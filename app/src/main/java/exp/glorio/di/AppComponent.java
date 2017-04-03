package exp.glorio.di;

import dagger.Component;
import exp.glorio.App;
import exp.glorio.model.DbRepository;
import exp.glorio.network.VkApiNetwork;

@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(App app);

    DbRepository getDbRepository();
    VkApiNetwork getVkApiUtil();
}
