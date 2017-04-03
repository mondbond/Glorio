package exp.glorio.model;

import com.vk.sdk.api.model.VKList;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import exp.glorio.util.VkUtil;

public class PublicStatistics {

    private String mPubName;
    private String mPubPhoto;
    private String mPubDescription;
    private float mMembersCount;
    private int mTotalPost;
    private float mTotalLikes;
    private float mPostCount;
    private float mSubscribers;
    private float mCountMembers;
    private float mManCount;
    private float mTotalAge;
    private float mAgeCount;

    public void setPublicInfo(VKList vkList) throws  JSONException{
        mPubName = vkList.get(0).fields.getString("name");
        mPubPhoto = vkList.get(0).fields.getString("photo_100");
        mPubDescription = vkList.get(0).fields.getString("description");
        mMembersCount = vkList.get(0).fields.getInt("members_count");
    }

    public void setPublicPosts(JSONObject jsonObject) throws JSONException {
        JSONObject response = jsonObject.getJSONObject("response");
        mTotalPost = response.getInt("count");
        JSONArray posts = response.getJSONArray("items");
        mPostCount = posts.length();
        mTotalLikes = VkUtil.getTotalLikes(posts);
    }

    public void setPublicMembers(JSONObject jsonObject) throws  JSONException {
        LocalDate currentDate = new LocalDate();
        mSubscribers = jsonObject.getInt("count");

        JSONObject member = new JSONObject();
        JSONArray members = jsonObject.getJSONArray("items");

        for(int i = 0; i != members.length(); i++) {
            member = members.getJSONObject(i);
            if(member.has("bdate")) {
                mCountMembers++;
                if (member.getInt("sex") == 2) {
                    mManCount++;
                }

                Integer age = getYears(member.getString("bdate"), currentDate);
                if(age != null) {
                    mTotalAge += age;
                    mAgeCount++;
                }
            }
        }
    }

    private Integer getYears(String bDate, LocalDate currentDate) {
        StringBuilder year = new StringBuilder();
        StringBuilder month = new StringBuilder();
        StringBuilder day = new StringBuilder();
        char[] birthday = bDate.toCharArray();
        int separator = 0;
        for(Character symbol: birthday) {
            if(!symbol.equals('.')) {
                if (separator == 0) {
                    day.append(symbol);
                } else if (separator ==1) {
                    month.append(symbol);
                }else if (separator == 2) {
                    year.append(symbol);
                }
            }else {
                separator++;
            }
        }

        if(!year.toString().equals("")) {
            LocalDate birthDate = new LocalDate(Integer.parseInt(year.toString()),
                    Integer.parseInt(month.toString()),Integer.parseInt(day.toString()));
            Period period = new Period(birthDate, currentDate, PeriodType.yearMonthDay());

            return period.getYears();
        }

        return null;
    }

    public float getManPersent(){
        return mManCount / mCountMembers * 100;
    }

    public String getPubName() {
        return mPubName;
    }

    public String getPubPhoto() {
        return mPubPhoto;
    }

    public String getPubDescription() {
        return mPubDescription;
    }

    public float getSubscribers() {
        return mSubscribers;
    }

    public float getMembersCount() {
        return mMembersCount;
    }

    public int getTotalPost() {
        return mTotalPost;
    }

    public float getLikesPerPost() {
        return mTotalLikes / mPostCount;
    }

    public float getAverageAge() {
        return mTotalAge / mAgeCount;
    }

    public float getSubPerAverageLikes() {
//        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMaximumFractionDigits(Integer.MAX_VALUE);
        return (float) (mSubscribers / Math.floor(getLikesPerPost()));
    }
}
