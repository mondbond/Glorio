package exp.glorio.di;

import dagger.Component;
import exp.glorio.view.fragments.DetailPublicFragment;
import exp.glorio.view.activity.DetailPublicActivity;

/**
 * Created by User on 13.03.2017.
 */
@Component(dependencies = AppComponent.class)
public interface DetailPublicComponent {

    void inject(DetailPublicActivity detailPublicActivity);
    void inject(DetailPublicFragment detailPublicFragment);
}
