package exp.glorio.presentation;

import android.util.Log;

import javax.inject.Inject;

import exp.glorio.common.BasePresenter;
import exp.glorio.network.VkApiNetwork;

public class DetailPublicPresenter implements BasePresenter<exp.glorio.view.fragments.DetailPublicView> {

    private exp.glorio.view.fragments.DetailPublicView view;

    private VkApiNetwork mVkApi;

    @Inject
    public DetailPublicPresenter(VkApiNetwork vkApiNetwork) {
        this.mVkApi = vkApiNetwork;
    }

    @Override
    public void init(exp.glorio.view.fragments.DetailPublicView view) {
        this.view = view;
    }

    public void getAllPublicInfo(int publicId) {
        mVkApi.getDetailPublicInfo(publicId).subscribe(vkStatistics -> {
            view.setDetailPublicAnaliticResult(vkStatistics);
        });
    }
}
