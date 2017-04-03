package exp.glorio.view.fragments.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vk.sdk.api.model.VKList;

import javax.inject.Inject;

import exp.glorio.R;
import exp.glorio.common.IHasComponent;
import exp.glorio.di.PublicComponent;
import exp.glorio.presentation.AddPublicDialogPresenter;
import exp.glorio.view.adapters.AddPublicAdapter;
import exp.glorio.view.fragments.PublicFragment;


public class AddPublicDialogFragment extends DialogFragment implements AddPublicDialogView{

    public static  final  String PUBlIC_LIST = "list";
    public static  final String SELECTED_POSITION = "selectedPosition";
    public final static String CATEGORY_NAME_CODE = "Add";

    @Inject
    AddPublicDialogPresenter presenter;

    VKList vkList;

    private Button saveBtn;
    private Button backBtn;
    private Button searchBtn;
    private EditText publicNameWrite;

    AddPublicAdapter adapter;
    private RecyclerView recyclerView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Add new public !");
        View v = inflater.inflate(R.layout.add_public_dialog_fragment, null);
//        return super.onCreateView(inflater, container, savedInstanceState);

        getComponent().inject(this);
        recyclerView = (RecyclerView) v.findViewById(R.id.addPublicRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        publicNameWrite = (EditText) v.findViewById(R.id.publicDialogWrite);
        searchBtn = (Button) v.findViewById(R.id.publicSearchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!publicNameWrite.getText().toString().equals("")){
                    presenter.searchPublicsByName(publicNameWrite.getText().toString());
                    }
                }
            });

        backBtn = (Button) v.findViewById(R.id.publicDialogBtnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        saveBtn = (Button) v.findViewById(R.id.publicDialogBtnAdd);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra(PUBlIC_LIST, vkList);
                intent.putExtra(SELECTED_POSITION, adapter.getSelectedItem());
                Log.d("1", Integer.toString(adapter.getSelectedItem()));

                getTargetFragment().onActivityResult(PublicFragment.REQUEST_CREATE,
                        Activity.RESULT_OK, intent);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init((AddPublicDialogView) this);
    }

    private PublicComponent getComponent() {
        return PublicComponent.class.cast(((IHasComponent<PublicComponent>)
                getActivity()).getComponent());
    }

    @Override
    public void setAdapter(VKList list) {
        adapter = new AddPublicAdapter(list, getActivity());
        vkList = list;
        recyclerView.setAdapter(adapter);
    }
}
