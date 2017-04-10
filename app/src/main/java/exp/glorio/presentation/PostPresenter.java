package exp.glorio.presentation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import javax.inject.Inject;

import exp.glorio.R;
import exp.glorio.common.BasePresenter;
import exp.glorio.model.DbRepository;
import exp.glorio.model.PostPOJO.Post;
import exp.glorio.network.VkApiNetwork;
import exp.glorio.util.VkUtil;
import exp.glorio.view.adapters.PostAdapter;
import exp.glorio.view.fragments.PostView;

public class PostPresenter implements BasePresenter<PostView> {

    @Inject
    Context context;

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

    public void putOrDeleteLike(Post post, int groupId, ImageView like, PostAdapter postAdapter) {
        if(like.getDrawable().getConstantState().equals(context.getResources()
                .getDrawable(R.drawable.like_unpressed).getConstantState())) {
            mVkApiNetwork.putLike(groupId, post.getId()).subscribe(result -> {
                if (result) {
                    postAdapter.getLikeArray().put(post.getId(), true);
                    postAdapter.setLikeStatus(post, like);
                }
            });
        }else {
            mVkApiNetwork.deleteLike(groupId, post.getId()).subscribe(result -> {
                if (result) {
                    if(postAdapter.getLikeArray().containsKey(post.getId())) {
                        postAdapter.getLikeArray().remove(post.getId());
                    }else {
                        postAdapter.getLikeArray().put(post.getId(), false);
                    }
                    postAdapter.setLikeStatus(post, like);
                }
            });
        }
    }
}
