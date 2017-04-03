package exp.glorio.view.fragments;


import android.app.Activity;
import android.content.Intent;
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
import exp.glorio.di.CategoryComponent;
import exp.glorio.model.data.Category;
import exp.glorio.presentation.CategoryPresenter;
import exp.glorio.view.adapters.CategoryAdapter;
import exp.glorio.view.adapters.PostAdapter;
import exp.glorio.view.fragments.dialog.CategoryDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment implements CategoryView {

    public final static int REQUEST_CREATE = 0;
    public final static int REQUEST_DELETE = 1;

    @Inject
    CategoryPresenter presenter;
    private RecyclerView categoryRecycle;

    private CategoryAdapter adapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        setHasOptionsMenu(true);

        categoryRecycle = (RecyclerView) v.findViewById (R.id.categoryEditRecycler);
        categoryRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(CategoryComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        presenter.getAllCategories();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE:
                    presenter.createCategory(
                            data.getStringExtra(CategoryDialogFragment.CATEGORY_NAME_CODE));
                    break;
                case REQUEST_DELETE:
                    Log.d("1", "LOG");
                    deleteSelectedItems();
                    break;
            }
        }
    }

    @Override
    public void showAllCategories(ArrayList<Category> categories) {

        if(adapter == null){
            adapter = new CategoryAdapter(categories,
                    CategoryAdapter.USUAL_MODE, getActivity());
            Log.d("3", "adapter  = " + adapter.toString());

        }else {
            adapter.setCategoryList(categories);
            Log.d("3", "adapter  = " + adapter.toString());
        }
        categoryRecycle.setAdapter(adapter);
    }

    @Override
    public void refreshRecycler() {
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public CategoryAdapter getAdapter() {
        return adapter;
    }

    public void deleteSelectedItems() {
        presenter.deleteSelectedItems(adapter.getCheckedItems());
        Log.d("2", "LOG");
    }
}
