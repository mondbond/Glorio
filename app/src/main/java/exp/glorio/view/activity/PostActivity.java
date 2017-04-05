package exp.glorio.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import exp.glorio.R;
import exp.glorio.common.BaseActivity;
import exp.glorio.common.IHasComponent;
import exp.glorio.di.AppComponent;
import exp.glorio.di.DaggerPostComponent;
import exp.glorio.di.PostComponent;
import exp.glorio.view.fragments.PostFragment;

public class PostActivity extends BaseActivity implements IHasComponent<PostComponent> {

    private final String RETAIN_POST = "postFragment";

    private PostComponent mComponent;
    private PostFragment mPostFragment;
    private View progressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        FragmentManager fm = getSupportFragmentManager();
        mPostFragment = (PostFragment) fm.findFragmentByTag(RETAIN_POST);

        if (mPostFragment == null) {
            mPostFragment = new PostFragment();
            Intent categoryIntent = getIntent();
            mPostFragment.setArguments(categoryIntent.getExtras());
            fm.beginTransaction().replace(R.id.postFragmentContainer, mPostFragment, RETAIN_POST).commit();
        }
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        mComponent = DaggerPostComponent.builder()
                .appComponent(appComponent)
                .build();
        mComponent.inject(this);
    }

    @Override
    public PostComponent getComponent() {
        return mComponent;
    }

    @Override
    public void showProgressAnimation() {
        findViewById(R.id.postFragmentContainer).setVisibility(View.INVISIBLE);
        progressCircle = findViewById(R.id.postProgress);
        progressCircle.setVisibility(View.VISIBLE);
        Animation progressAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_circle_rotation);
        progressCircle.setAnimation(progressAnimation);
    }

    @Override
    public void hideProgressAnimation() {
        findViewById(R.id.postFragmentContainer).setVisibility(View.VISIBLE);
        progressCircle.setAnimation(null);
        progressCircle.setVisibility(View.INVISIBLE);
    }
}
