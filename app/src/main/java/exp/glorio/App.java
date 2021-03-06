package exp.glorio;

import android.app.Application;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import org.greenrobot.greendao.database.Database;
import exp.glorio.di.AppComponent;
import exp.glorio.di.AppModule;
import exp.glorio.di.DaggerAppComponent;
import exp.glorio.model.data.DaoMaster;
import exp.glorio.model.data.DaoSession;

public class App extends Application {

    private static AppComponent appComponent;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                // VKAccessToken is invalid
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);

        buildGraphAndInject();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "glorio-db", null);
        Database db = helper.getWritableDb();
        db.execSQL("PRAGMA foreign_keys = ON;");
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
    }

    public static AppComponent getAppComponent()
        {
            return appComponent;
        }

    public void buildGraphAndInject()
    {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
