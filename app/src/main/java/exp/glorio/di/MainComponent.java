package exp.glorio.di;

import dagger.Component;
import exp.glorio.view.activity.MainActivity;
import exp.glorio.view.fragments.MainFragment;

@Component(dependencies = {AppComponent.class})
public interface MainComponent {

    void inject(MainActivity mainActivity);
    void inject(MainFragment mainFragment);
}
