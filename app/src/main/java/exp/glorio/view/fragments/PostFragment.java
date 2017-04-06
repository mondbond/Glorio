package exp.glorio.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import exp.glorio.R;
import exp.glorio.common.BaseActivity;
import exp.glorio.common.BaseFragment;
import exp.glorio.di.CategoryComponent;
import exp.glorio.di.PostComponent;
import exp.glorio.model.PostPOJO.Post;
import exp.glorio.presentation.PostPresenter;
import exp.glorio.view.activity.PublicActivity;
import exp.glorio.view.adapters.BaseListAdapter;
import exp.glorio.view.adapters.PostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends BaseFragment implements PostView {

    @Inject
    PostPresenter presenter;

    private RecyclerView recyclerView;
    private long categoryId;
    private ArrayList<Post> postsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.postFragmentRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Bundle arguments = getArguments();
        categoryId = arguments.getLong(PublicActivity.CATEGORY_NAME);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);

        if(postsList == null) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.showProgressAnimation();
            presenter.getPosts(categoryId);
        }else {
            setPostData(postsList);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(PostComponent.class).inject(this);
    }

    @Override
    public void setPostData(ArrayList<Post> postsList) {

        if(this.postsList == null){
            BaseActivity activity = (BaseActivity) getActivity();
            activity.hideProgressAnimation();
        }

        this.postsList = postsList;

        PostAdapter adapter = new PostAdapter(postsList, getActivity(), presenter);
        recyclerView.setAdapter(adapter);
    }
}
