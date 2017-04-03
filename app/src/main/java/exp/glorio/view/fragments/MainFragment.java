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
import exp.glorio.common.BaseFragment;
import exp.glorio.common.IHasComponent;
import exp.glorio.di.MainComponent;
import exp.glorio.model.PostPOJO.Post;
import exp.glorio.model.data.Category;
import exp.glorio.presentation.MainPresenter;
import exp.glorio.view.adapters.CategoryAdapter;
import exp.glorio.view.adapters.PostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements MainView {

    @Inject
    MainPresenter presenter;

    private RecyclerView categoryListRecycler;
    private ArrayList<Category> categoryList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainFragment", "on CREAte");

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_main, container, false);
        categoryListRecycler = (RecyclerView) v.findViewById(R.id.categoryListRecycler);
        categoryListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(MainComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        if(categoryList == null) {
            presenter.loadCategoryList();
        }else {
            showCategoryList(categoryList);
        }
    }

    @Override
    public void showCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
        CategoryAdapter adapter = new CategoryAdapter(categoryList, CategoryAdapter.PRESENTATION_MODE,
                getActivity());
        categoryListRecycler.setAdapter(adapter);
    }
}
