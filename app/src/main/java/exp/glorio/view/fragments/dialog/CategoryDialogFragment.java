package exp.glorio.view.fragments.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import exp.glorio.R;
import exp.glorio.view.fragments.CategoryFragment;

public class CategoryDialogFragment extends DialogFragment {

    public final static String CATEGORY_NAME_CODE = "categoryName";

    private Button okBtn;
    private Button cencelBtn;
    private EditText categoryNameWrite;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Add new category !");
        View v = inflater.inflate(R.layout.category_dialog_fragment, null);
//        return super.onCreateView(inflater, container, savedInstanceState);

        categoryNameWrite = (EditText) v.findViewById(R.id.categoryDialogWrite);
        okBtn = (Button) v.findViewById(R.id.categoryDialogBtnYes);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!categoryNameWrite.getText().toString().equals("")) {
                    Intent intent = new Intent();
                    intent.putExtra(CATEGORY_NAME_CODE, categoryNameWrite.getText().toString());
                    getTargetFragment().onActivityResult(CategoryFragment.REQUEST_CREATE, Activity.RESULT_OK, intent);
                    Log.d("DIalog", "create");

                    dismiss();
                }
            }
        });
        cencelBtn = (Button) v.findViewById(R.id.categoryDialogBtnCencel);
        cencelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }
}
