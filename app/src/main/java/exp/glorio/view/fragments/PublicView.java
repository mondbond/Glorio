package exp.glorio.view.fragments;

import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

import exp.glorio.model.data.Public;

public interface PublicView {
    void showAllPublics();
    void showAllPublics(VKList vkList);
    void refresh();

    void refreshRecycler();
}
