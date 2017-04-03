package exp.glorio.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import exp.glorio.App;
import exp.glorio.model.DbRepository;
import exp.glorio.network.VkApiNetwork;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    Context providesContext() {

        return app.getApplicationContext();
    }

    @Provides
    DbRepository providesDbRepository() {

        return new DbRepository(app.getApplicationContext());
    }

    @Provides
    VkApiNetwork providesVkApiUtil() {

        return new VkApiNetwork();
    }
}
