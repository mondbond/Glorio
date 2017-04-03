package exp.glorio.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import exp.glorio.R;
import exp.glorio.common.BaseActivity;
import exp.glorio.common.IHasComponent;
import exp.glorio.di.AppComponent;
import exp.glorio.di.DaggerDetailPublicComponent;
import exp.glorio.di.DetailPublicComponent;
import exp.glorio.view.fragments.CategoryFragment;
import exp.glorio.view.fragments.DetailPublicFragment;

public class DetailPublicActivity extends BaseActivity implements IHasComponent<DetailPublicComponent> {

    public final String RETAIN_DETAIL_PUBLIC = "detailPublicFragment";

    public static final String PUBLIC_NAME = "publicName";

    private DetailPublicComponent mComponent;
    private DetailPublicFragment mDetailPublicFragment;
    private View progressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_public);



        FragmentManager fm = getSupportFragmentManager();
        mDetailPublicFragment = (DetailPublicFragment) fm.findFragmentByTag(RETAIN_DETAIL_PUBLIC);

        if (mDetailPublicFragment == null) {
            mDetailPublicFragment = new DetailPublicFragment();
            Intent intent = getIntent();
            mDetailPublicFragment.setArguments(intent.getExtras());
            fm.beginTransaction().replace(R.id.detailPublicFragmentContainer, mDetailPublicFragment, RETAIN_DETAIL_PUBLIC).commit();
        }
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        mComponent = DaggerDetailPublicComponent.builder()
                .appComponent(appComponent)
                .build();

        mComponent.inject(this);
    }

    @Override
    public DetailPublicComponent getComponent() {
        return mComponent;
    }

    @Override
    public void showProgressAnimation() {
        findViewById(R.id.detailPublicFragmentContainer).setVisibility(View.INVISIBLE);
        progressCircle = findViewById(R.id.detailPublicProgress);
        progressCircle.setVisibility(View.VISIBLE);
        Animation progressAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_circle_rotation);
        progressCircle.setAnimation(progressAnimation);
    }

    @Override
    public void hideProgressAnimation() {
        findViewById(R.id.detailPublicFragmentContainer).setVisibility(View.VISIBLE);
        progressCircle.setAnimation(null);
        progressCircle.setVisibility(View.INVISIBLE);
    }
}
