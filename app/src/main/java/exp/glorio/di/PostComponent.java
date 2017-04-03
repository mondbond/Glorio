package exp.glorio.di;

import dagger.Component;
import exp.glorio.view.activity.PostActivity;
import exp.glorio.view.fragments.PostFragment;

@Component(dependencies = AppComponent.class)
public interface PostComponent {
    void inject(PostActivity postActivity);
    void inject(PostFragment postFragment);
}
