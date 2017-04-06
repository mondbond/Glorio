package exp.glorio.di;

import android.content.Context;

import dagger.Component;
import exp.glorio.App;
import exp.glorio.model.DbRepository;
import exp.glorio.network.VkApiNetwork;

@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(App app);

    Context getContext();
    DbRepository getDbRepository();
    VkApiNetwork getVkApiUtil();
}
