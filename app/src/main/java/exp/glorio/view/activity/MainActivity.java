    package exp.glorio.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.greenrobot.greendao.database.Database;
import org.json.JSONException;

import javax.inject.Inject;

import exp.glorio.R;
import exp.glorio.common.BaseActivity;
import exp.glorio.common.IHasComponent;
import exp.glorio.di.AppComponent;
import exp.glorio.di.DaggerMainComponent;
import exp.glorio.di.MainComponent;
import exp.glorio.model.data.CategoryDao;
import exp.glorio.model.data.DaoMaster;
import exp.glorio.model.data.DaoSession;
import exp.glorio.presentation.MainActivityPresenter;
import exp.glorio.view.fragments.MainFragment;

    public class MainActivity extends BaseActivity implements IHasComponent<MainComponent>, MainActivityView{

        public final String RETAIN_MAIN = "mainFragment";

        private MainComponent mComponent;

        @Inject
        MainActivityPresenter presenter;

        MainFragment mainFragment;

        private VKAccessToken mToken;
        private TextView userName;
        private TextView userStatus;
        private ImageView userAvatar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            userName = (TextView) findViewById(R.id.userName);
            userStatus = (TextView) findViewById(R.id.userStatus);
            userAvatar = (ImageView) findViewById(R.id.userAvatar);

//            VKSdk.login(this);
    //        getRequest();


            FragmentManager fm = getSupportFragmentManager();
            mainFragment = (MainFragment) fm.findFragmentByTag(RETAIN_MAIN);


            if (mainFragment == null) {
                mainFragment = new MainFragment();
                fm.beginTransaction().replace(R.id.mainFragmentContainer, mainFragment, RETAIN_MAIN).commit();
            }
    }

        @Override
        public void setupComponent(AppComponent appComponent) {
            mComponent = DaggerMainComponent.builder()
                    .appComponent(appComponent)
                    .build();

            mComponent.inject(this);
            presenter.init(this);
        }

        @Override
        protected void onResume() {
            super.onResume();
            presenter.getUserInfo();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if("Moderate categories".equals(item.getTitle().toString())) {
                Intent intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
                @Override
                public void onResult(VKAccessToken res) {
                    mToken = res;
                    // User passed Authorization
                }
                @Override
                public void onError(VKError error) {
                    // User didn't pass Authorization
                }
            })) {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        public void getRequest()
        {
            VKRequest request = VKApi.groups().getById(VKParameters.from(VKApiConst.GROUP_ID, "24946565"));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    userName.setText(response.json.toString());

                    VKList list = (VKList) response.parsedModel;

                    try{
                        System.out.println(list.get(0).fields.getString("name"));
                        Picasso.with(MainActivity.this).load(list.get(0).fields.getString("photo_200"))
                                .resize(120, 120).into(userAvatar);
                        if(userAvatar == null) {
                        }
                    }catch (JSONException e) {}

                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MainActivity.this, "glorio-db", null);
                    Database db = helper.getWritableDb();
                    DaoMaster daoMaster = new DaoMaster(db);

                    DaoSession daoSession = daoMaster.newSession();
                    CategoryDao categoryDao = daoSession.getCategoryDao();

                }

                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                    super.attemptFailed(request, attemptNumber, totalAttempts);
                }

                @Override
                public void onError(VKError error) {
                    super.onError(error);
                }
                @Override
                public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                    super.onProgress(progressType, bytesLoaded, bytesTotal);
                }
            });
        }

        @Override
        public MainComponent getComponent() {

            return mComponent;
        }

        @Override
        public void setPersonalInfo(String name, String status, String avatarUrl) {
            userName.setText(name);
            userStatus.setText(status);
            if(!avatarUrl.equals("")){
                Picasso.with(this).load(avatarUrl).into(userAvatar);
            }
        }
    }
