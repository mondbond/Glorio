package exp.glorio.presentation;

import android.util.Log;

import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import javax.inject.Inject;

import exp.glorio.common.BasePresenter;
import exp.glorio.network.VkApiNetwork;
import exp.glorio.view.activity.MainActivityView;
import rx.Subscriber;


public class MainActivityPresenter implements BasePresenter<MainActivityView> {

    private MainActivityView mView;
    private VkApiNetwork mVkApi;

    @Inject
    public MainActivityPresenter(VkApiNetwork vk) {
        this.mVkApi = vk;
    }

    @Override
    public void init(MainActivityView view) {
        this.mView = view;
    }

    public void getUserInfo() {

        mVkApi.getUserPersonalInfo().subscribe(vkResponse -> showPersonalInfo(vkResponse));
    }

    private void showPersonalInfo(VKResponse response) {
        VKList list = (VKList) response.parsedModel;
        StringBuilder name = new StringBuilder();
        String status = "";
        String avatar = "";

        try {
            name.append(list.get(0).fields.getString("first_name"));
            name.append(" ");
            name.append(list.get(0).fields.get("last_name"));
            status = list.get(0).fields.getString("status");
            avatar = list.get(0).fields.getString("photo_100");
        }catch (JSONException e){}

        mView.setPersonalInfo(name.toString(), status, avatar);
    }
}
