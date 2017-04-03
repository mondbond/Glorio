package exp.glorio.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import exp.glorio.model.PostPOJO.Attachments;
import exp.glorio.model.PostPOJO.Link;
import exp.glorio.model.PostPOJO.Photo;
import exp.glorio.model.PostPOJO.Post;
import exp.glorio.model.PublicStatistics;


public class VkUtil {

    public static final String PHOTO_ATTACHMENT = "photo";
    public static final String LINK_ATTACHMENT = "link";

    public static final int NO_FILTER = 0;
    public static final double GOOD_FILTER = 1;
    public static final double BEST_FILTER = 1.7;

    public static void appendPosts(JSONObject postJson, ArrayList<Post> postsList) {
        try {
            Log.d("ALL JSON", postJson.toString());

            JSONObject response = postJson.getJSONObject("response");
            JSONArray posts = response.getJSONArray("items");
            int averageL = getTotalLikes(posts)/posts.length();

//            Log.d("LIKE  !!!", " like = " + String.valueOf(averageL));

            JSONObject post;
            for(int i = 0; i != posts.length(); i++) {

//                Log.d("TTTTTT", "45 = " + String.valueOf(posts.length()));

                post = posts.getJSONObject(i);
//                Log.d("TTTTTT", "46");

                if(post.getInt("marked_as_ads") == 0
                        && post.getJSONObject("likes").getInt("count") >= averageL*GOOD_FILTER) {
//                    Log.d("TTTTTT", "47");

                    postsList.add(parsePost(response, post, averageL));
//                    Log.d("TTTTTT", "2");
                }

//                Log.d("TTTTTT", "1");
            }
//            Log.d("TTTTTT", "3");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static Post parsePost(JSONObject response, JSONObject jsonPost, float avarageLikes) throws JSONException {
//        Log.d("TTTTTT", "11");

        Post post = new Post();
        post.setId(jsonPost.getInt("id"));
        post.setAd(jsonPost.getInt("marked_as_ads"));
        post.setType(jsonPost.getString("post_type"));
        post.setText(jsonPost.getString("text"));

        post.setLikes(jsonPost.getJSONObject("likes").getInt("count"));
        post.setReposts(jsonPost.getJSONObject("reposts").getInt("count"));

        post.setIndex(getFormatted(post.getLikes()/avarageLikes));

        post.setGroupId(response.getJSONArray("groups").getJSONObject(0).getInt("id"));
        post.setGroupName(response.getJSONArray("groups").getJSONObject(0).getString("name"));
        post.setPhoto100Url(response.getJSONArray("groups").getJSONObject(0).getString("photo_100"));

        post.setDate(jsonPost.getLong("date"));


        if(jsonPost.has("attachments")) {
            JSONArray jsonAttachments = jsonPost.getJSONArray("attachments");
            ArrayList<Attachments> attachmentses = new ArrayList<Attachments>();
            for(int i = 0; i != jsonAttachments.length(); i++){
                attachmentses.add(parseAttachments(jsonAttachments.getJSONObject(i)));
            }
            post.setAttachmentses(attachmentses);
        }
        return post;
    }

    private static Attachments parseAttachments(JSONObject jsonAttachment) throws JSONException {
        switch (jsonAttachment.getString("type")) {
            case PHOTO_ATTACHMENT:
                JSONObject jsonPhoto = jsonAttachment.getJSONObject("photo");
                Photo photo = new Photo(PHOTO_ATTACHMENT, jsonPhoto.getInt("id"), jsonPhoto.getString("photo_604"),
                        jsonPhoto.getInt("width"), jsonPhoto.getInt("height"),
                        jsonPhoto.getString("text"));

                return photo;

            case LINK_ATTACHMENT:
                JSONObject jsonLink = jsonAttachment.getJSONObject("link");
                Link link;
                if(jsonLink.has("image_big"))
                {
                    link = new Link(LINK_ATTACHMENT, jsonLink.getString("url"), jsonLink.getString("title"),
                            jsonLink.getString("description"), jsonLink.getString("image_big"));
                }else if(jsonLink.has("image_src")) {
                    link = new Link(LINK_ATTACHMENT, jsonLink.getString("url"), jsonLink.getString("title"),
                             jsonLink.getString("description"), jsonLink.getString("image_src"));
                }else {
                    link = new Link(LINK_ATTACHMENT, jsonLink.getString("url"), jsonLink.getString("title"),
                             jsonLink.getString("description"), null);
                }

                return link;

            default:

                return null;
        }
    }

    public static int getTotalLikes(JSONArray postList) throws JSONException {
        JSONObject post;
        JSONObject likes;
        int totalLikes = 0;
        for (int i = 0; i != postList.length(); i++) {
            post = postList.getJSONObject(i);
            if(post.getInt("marked_as_ads") == 0) {
                likes = post.getJSONObject("likes");
                totalLikes += likes.getInt("count");
            }
        }

        return totalLikes;
    }

    public static ArrayList<Post> sortByDate(ArrayList<Post> postList) {
        Collections.sort(postList, new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                return t1.getDate().compareTo(post.getDate());
            }
        });

        return  postList;
    }

    public static String getFormatted(float number) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern("#.##");

        return df.format(number);
    }
}
