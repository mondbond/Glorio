package exp.glorio.common;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import exp.glorio.App;
import exp.glorio.R;
import exp.glorio.di.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(App.getAppComponent());
    }

    public abstract void setupComponent(AppComponent appComponent);

    protected void setupFragment(View container, BaseFragment fragment) {

    }

    public void showProgressAnimation() {
    }

    public void hideProgressAnimation() {
    }


}
