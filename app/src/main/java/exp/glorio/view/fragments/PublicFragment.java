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

import com.vk.sdk.api.model.VKList;

import javax.inject.Inject;

import exp.glorio.R;
import exp.glorio.common.BaseFragment;
import exp.glorio.di.PublicComponent;
import exp.glorio.presentation.PublicPresenter;
import exp.glorio.view.activity.PublicActivity;
import exp.glorio.view.adapters.PublicAdapter;
import exp.glorio.view.fragments.dialog.AddPublicDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicFragment extends BaseFragment implements PublicView {



    public final static int REQUEST_CREATE = 0;
    public final static int REQUEST_DELETE = 1;

    private RecyclerView publicRecycle;

    private PublicAdapter adapter;

    private Long categoryId;

    @Inject
    PublicPresenter presenter;

    public PublicFragment() {
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
        View v = inflater.inflate(R.layout.fragment_public, container, false);

        publicRecycle = (RecyclerView) v.findViewById(R.id.publicRecycler);
        publicRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        Bundle arguments = getArguments();
        categoryId = arguments.getLong(PublicActivity.CATEGORY_NAME);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(PublicComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        refresh();

//        LocalDate birthdate = new LocalDate (1961, 1, 20);          //Birth date
//        LocalDate now = new LocalDate();                    //Today's date
//        Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
//        Log.d("JODA", Long.toString(period.getYears()));
    }


    @Override
    public void showAllPublics() {
        presenter.getAllPublicsByCategoryId(categoryId);
    }

    @Override
    public void showAllPublics(VKList list) {

        if(adapter == null){
        adapter = new PublicAdapter(list,
                PublicAdapter.USUAL_MODE, getActivity());
        publicRecycle.setAdapter(adapter);
        }else {
            adapter.setVkList(list);
        }
        publicRecycle.setAdapter(adapter);
    }

    @Override
    public void refreshRecycler() {
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public PublicAdapter getAdapter() {
        return adapter;
    }

    public void deleteSelectedItems() {
        presenter.deleteSelectedItems(adapter.getCheckedItems());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CREATE:
                    Log.d("2", "///");

                    Bundle bundle =  data.getExtras();
                    VKList vkList = (VKList) bundle.getParcelable(AddPublicDialogFragment.PUBlIC_LIST);
                    int selectedItem = bundle.getInt(AddPublicDialogFragment.SELECTED_POSITION);
                    Log.d("3", "..." + vkList.toString() + Integer.toString(selectedItem));

                    presenter.savePublic(vkList, selectedItem, categoryId);
                    break;

                case REQUEST_DELETE:
                    deleteSelectedItems();
                    break;
            }
        }
    }

    @Override
    public void refresh() {
        presenter.getAllPublicsByCategoryId(categoryId);
    }

}
