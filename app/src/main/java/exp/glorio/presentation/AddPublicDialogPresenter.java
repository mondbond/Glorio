package exp.glorio.presentation;

import com.vk.sdk.api.model.VKList;
import javax.inject.Inject;
import exp.glorio.common.BasePresenter;
import exp.glorio.network.VkApiNetwork;
import exp.glorio.view.fragments.dialog.AddPublicDialogView;

public class AddPublicDialogPresenter implements BasePresenter<AddPublicDialogView> {

    private AddPublicDialogView mView;
    private VkApiNetwork mVkApi;

    VKList vkList;

    @Inject
    public AddPublicDialogPresenter(VkApiNetwork vkApi) {
        this.mVkApi = vkApi;
    }

    @Override
    public void init(AddPublicDialogView view) {
        this.mView = view;
    }

    public void searchPublicsByName(String publicName) {
        mVkApi.getPublicsByName(publicName).subscribe(vkResponse -> {
            vkList = (VKList) vkResponse.parsedModel;
            mView.setAdapter(vkList);
        });
    }
}
