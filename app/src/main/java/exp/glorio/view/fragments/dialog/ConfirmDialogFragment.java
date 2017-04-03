package exp.glorio.view.fragments.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import exp.glorio.R;
import exp.glorio.view.fragments.CategoryFragment;

public class ConfirmDialogFragment extends android.support.v4.app.DialogFragment {

    public final static String CATEGORY_CONFIRM_CODE = "confirmThis";

    TextView confirmText;
    Button cancelBtn;
    Button confirmBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.confirm_dialog_fragment, null);

        confirmText = (TextView) v.findViewById(R.id.confirmDialogWrite);
        cancelBtn = (Button) v.findViewById(R.id.confirmDialogBtnCencel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        confirmBtn = (Button) v.findViewById(R.id.confirmDialogBtnYes);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Log.d("0", "LOG");
                getTargetFragment().onActivityResult(CategoryFragment.REQUEST_DELETE,
                        Activity.RESULT_OK, intent);
                dismiss();
            }
        });

        return v;
    }
}
