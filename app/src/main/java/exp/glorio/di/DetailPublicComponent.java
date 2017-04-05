package exp.glorio.di;

import dagger.Component;
import exp.glorio.view.fragments.DetailPublicFragment;
import exp.glorio.view.activity.DetailPublicActivity;

@Component(dependencies = AppComponent.class)
public interface DetailPublicComponent {

    void inject(DetailPublicActivity detailPublicActivity);
    void inject(DetailPublicFragment detailPublicFragment);
}
