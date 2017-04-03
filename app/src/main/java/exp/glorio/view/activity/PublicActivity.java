package exp.glorio.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import exp.glorio.R;
import exp.glorio.common.BaseActivity;
import exp.glorio.common.IHasComponent;
import exp.glorio.di.AppComponent;
import exp.glorio.di.DaggerPublicComponent;
import exp.glorio.di.PublicComponent;
import exp.glorio.view.adapters.CategoryAdapter;
import exp.glorio.view.fragments.MainFragment;
import exp.glorio.view.fragments.PublicFragment;
import exp.glorio.view.fragments.dialog.AddPublicDialogFragment;
import exp.glorio.view.fragments.dialog.ConfirmDialogFragment;

public class PublicActivity extends BaseActivity implements IHasComponent<PublicComponent> {

    private final String DELETE_MODE = "deleteMode";
    private final String RETAIN_PUBLIC = "publicFragment";

    public static final String CATEGORY_NAME = "categoryNAme";

    private PublicComponent mPublicComponent;

    private PublicFragment mPublicFragment;

    private ConfirmDialogFragment mConfirmDialogFragment;

    FloatingActionButton fab;

    boolean deleteMode;

    MenuItem trash;
    MenuItem delete;
    MenuItem back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        Toolbar toolbar = (Toolbar) findViewById(R.id.publicToolbar);
        setSupportActionBar(toolbar);

//        mPublicFragment = new PublicFragment();

        fab = (FloatingActionButton) findViewById(R.id.publicFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPublicDialogFragment addPublicDialogFragment = new AddPublicDialogFragment();
                addPublicDialogFragment.setTargetFragment(mPublicFragment, 3);
                addPublicDialogFragment.show(getSupportFragmentManager(), "add");
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        mPublicFragment = (PublicFragment) fm.findFragmentByTag(RETAIN_PUBLIC);


        if (mPublicFragment == null) {
            mPublicFragment = new PublicFragment();
            Intent categoryIntent = getIntent();
            mPublicFragment.setArguments(categoryIntent.getExtras());
            fm.beginTransaction().replace(R.id.publicFragmentContainer, mPublicFragment, RETAIN_PUBLIC).commit();
        }
//        categoryDialogFragment = new CategoryDialogFragment();

        if(savedInstanceState != null) {
            deleteMode = savedInstanceState.getBoolean(DELETE_MODE);
//            changeMode();
        }
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        mPublicComponent = DaggerPublicComponent.builder()
                .appComponent(appComponent)
                .build();

        mPublicComponent.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionTrash:
                deleteMode = true;
                changeMode();
                break;
            case R.id.actionBack:
                deleteMode = false;
                mPublicFragment.getAdapter().cleanCheckedItems();
                changeMode();
                break;
            case R.id.actionDelete:
                deleteMode = false;
                mConfirmDialogFragment = new ConfirmDialogFragment();
                mConfirmDialogFragment.setTargetFragment(mPublicFragment, 0);
                mConfirmDialogFragment.show(getSupportFragmentManager(), "confirm");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        Log.d("8", "menu" );


        trash = menu.findItem(R.id.actionTrash);
        back = menu.findItem(R.id.actionBack);
        delete = menu.findItem(R.id.actionDelete);
        changeMode();


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public PublicComponent getComponent() {
        return mPublicComponent;
    }

    private void changeMode() {
        if(mPublicFragment.getAdapter() != null) {
            if (deleteMode) {
                trash.setVisible(false);
                delete.setVisible(true);
                back.setVisible(true);
                fab.setVisibility(View.INVISIBLE);
                mPublicFragment.getAdapter().changeMode(CategoryAdapter.DELETE_MODE);
                mPublicFragment.getAdapter().notifyDataSetChanged();
            } else {
                trash.setVisible(true);
                delete.setVisible(false);
                back.setVisible(false);
                fab.setVisibility(View.VISIBLE);

                mPublicFragment.getAdapter().changeMode(CategoryAdapter.USUAL_MODE);
                mPublicFragment.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DELETE_MODE, deleteMode);
    }
}
