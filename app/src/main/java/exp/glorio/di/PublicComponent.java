package exp.glorio.di;

import dagger.Component;
import exp.glorio.view.activity.PublicActivity;
import exp.glorio.view.fragments.PublicFragment;
import exp.glorio.view.fragments.dialog.AddPublicDialogFragment;

@Component(dependencies = AppComponent.class)
public interface PublicComponent {
    void inject(PublicActivity publicActivity);
    void inject(PublicFragment publicFragment);
    void inject(AddPublicDialogFragment addPublicDialogFragment);
}
