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
//            Log.d("PUBINFO", vkStatistics.toString());
//            Log.d("PUBINFO", vkStatistics.getPubName());
//            Log.d("PUBINFO", vkStatistics.getPubPhoto());
//            Log.d("PUBINFO_ POST", String.valueOf(vkStatistics.getTotalPost()));
//            Log.d("PUBINFO_ AVLIKE", String.valueOf(vkStatistics.getLikesPerPost()));
//            Log.d("PUBINFO_ AGE", vkStatistics.getFormatted(vkStatistics.getAverageAge()));
//            Log.d("PUBINFO_ S", String.valueOf(vkStatistics.getSubscribers()));
//            Log.d("PUBINFO_ L - S", String.valueOf(vkStatistics.getSubPerAverageLikes()));
//            Log.d("PUBINFO_ SEX", String.valueOf(vkStatistics.getManPersent()));
//
            view.setDetailPublicAnaliticResult(vkStatistics);
        });
    }
}
