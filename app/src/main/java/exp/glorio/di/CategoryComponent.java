package exp.glorio.di;

import dagger.Component;
import exp.glorio.App;
import exp.glorio.view.activity.CategoryActivity;
import exp.glorio.view.fragments.CategoryFragment;

/**
 * Created by User on 06.03.2017.
 */
@Component(dependencies = {AppComponent.class})
public interface CategoryComponent {
    void inject(CategoryActivity categoryActivity);
    void inject(CategoryFragment categoryFragment);
}
