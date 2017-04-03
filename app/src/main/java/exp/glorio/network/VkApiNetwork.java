package exp.glorio.network;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import exp.glorio.model.PostPOJO.Post;
import exp.glorio.model.PublicStatistics;
import exp.glorio.model.data.Public;
import exp.glorio.util.VkUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VkApiNetwork {

    private VKResponse mPersonalInfoResponse;
    private VKResponse mSearchPublicsResponse;
    private VKResponse mPublicsInfo;
    private VKList mPublicInfo;
    private JSONObject mPublicsMembers;

    public Observable<VKResponse> getUserPersonalInfo() {
        return  Observable.create(new Observable.OnSubscribe<VKResponse>()
        {
            @Override
            public void call(Subscriber<? super VKResponse> subscriber) {
                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                        "photo_200,status"));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        VkApiNetwork.this.mPersonalInfoResponse = response;
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }
                });
                subscriber.onNext(mPersonalInfoResponse);
            }}).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VKResponse> getPublicsByName(String publicName) {
        return Observable.create(new Observable.OnSubscribe<VKResponse>() {
            @Override
            public void call(Subscriber<? super VKResponse> subscriber) {
                VKRequest request = VKApi.groups().search(VKParameters.from(VKApiConst.Q, publicName,
                        VKApiConst.SORT, 0, VKApiConst.COUNT, 100, VKApiConst.FIELDS, "members_count"));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        VkApiNetwork.this.mSearchPublicsResponse = response;
                    }
                });
                subscriber.onNext(mSearchPublicsResponse);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VKResponse> getPublicsInfoByIds(String ids) {
        return Observable.create(new Observable.OnSubscribe<VKResponse>() {
            @Override
            public void call(Subscriber<? super VKResponse> subscriber) {
                VKRequest request = VKApi.groups().getById(VKParameters.from("group_ids", ids));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        mPublicsInfo = response;
                    }
                });
                subscriber.onNext(mPublicsInfo);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VKList> getPubInfo(int pubId) {
        return Observable.create(new Observable.OnSubscribe<VKList>() {
            @Override
            public void call(Subscriber<? super VKList> subscriber) {
                VKRequest request = VKApi.groups().getById(VKParameters.from(VKApiConst.GROUP_ID, pubId,
                        VKApiConst.FIELDS, "members_count,status,description"));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        mPublicInfo = (VKList) response.parsedModel;
                        subscriber.onNext(mPublicInfo);
                    }
                });
            }
        });
    }

    public Observable<JSONObject> getPubPosts(Integer pubId) {
        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                StringBuilder id = new StringBuilder();
                id.append("-").append(pubId.toString());
                VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID,
                        Integer.parseInt(id.toString()),
                        VKApiConst.COUNT, 100, VKApiConst.EXTENDED, 1, VKApiConst.FILTERS, "owner"));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        subscriber.onNext(response.json);
                    }
                });
            }
        });
    }

    public Observable<JSONObject> getPubMembers(Integer pubId) {
        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                for (int i = 1; i != 10; i++) {
                    VKRequest request = VKApi.groups().getMembers(VKParameters.from(VKApiConst.GROUP_ID, pubId,
                    VKApiConst.FIELDS, "sex,bdate", VKApiConst.COUNT, 500, VKApiConst.OFFSET,
                    i*1000, VKApiConst.SORT));
                    request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    if(mPublicsMembers == null) {
                        try {
                            mPublicsMembers = response.json.getJSONObject("response");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }}else {
                        try {
                            JSONArray members = response.json.getJSONObject("response")
                                    .getJSONArray("items");
                            JSONArray count = mPublicsMembers.getJSONArray("items");
                            for(int i = 0; i != members.length(); i++) {
                                count.put(members.get(i));
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                        }
                    });
                }
                subscriber.onNext(mPublicsMembers);
            }
        });
    }

    public Observable<PublicStatistics> getDetailPublicInfo(Integer pubId) {
        PublicStatistics statistics = new PublicStatistics();
        return Observable.create(new Observable.OnSubscribe<PublicStatistics>() {
            @Override
            public void call(Subscriber<? super PublicStatistics> subscriber) {
                getPubPosts(pubId).subscribe(vkList -> {
                    try {
                        statistics.setPublicPosts(vkList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                getPubInfo(pubId).subscribe(vkList -> {
                    try {
                        statistics.setPublicInfo(vkList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                getPubMembers(pubId).subscribe(vkList -> {
                    try {
                        statistics.setPublicMembers(vkList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                subscriber.onNext(statistics);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ArrayList<Post>>  getTopPost(ArrayList<Public> categoriesId) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Post>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Post>> subscriber) {
                ArrayList<Post> array = new ArrayList<Post>();
                for(Public group : categoriesId){
                    getPubPosts(group.getPublicId()).subscribe(jsonObject -> {
                        VkUtil.appendPosts(jsonObject, array);
                    });
                }
                subscriber.onNext(array);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
