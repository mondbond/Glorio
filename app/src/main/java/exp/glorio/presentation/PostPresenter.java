package exp.glorio.presentation;

import javax.inject.Inject;
import exp.glorio.common.BasePresenter;
import exp.glorio.model.DbRepository;
import exp.glorio.network.VkApiNetwork;
import exp.glorio.util.VkUtil;
import exp.glorio.view.fragments.PostView;

public class PostPresenter implements BasePresenter<PostView> {

    private PostView mView;
    private VkApiNetwork mVkApiNetwork;
    private DbRepository mRepository;

    @Inject
    public PostPresenter(VkApiNetwork vkApiNetwork, DbRepository repository) {
        this.mVkApiNetwork = vkApiNetwork;
        this.mRepository = repository;
    }

    @Override
    public void init(PostView view) {
        this.mView = view;
    }

    public void getPosts(long categoryId) {
        mRepository.getPublicsByCategoryId(categoryId).subscribe(groups -> {
            mVkApiNetwork.getTopPost(groups).subscribe(arrayList -> {
                mView.setPostData(VkUtil.sortByDate(arrayList));
            });
        });
    }
}
