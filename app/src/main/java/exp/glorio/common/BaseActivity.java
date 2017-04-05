package exp.glorio.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import exp.glorio.App;
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
