package exp.glorio.view.activity;

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
import exp.glorio.di.CategoryComponent;
import exp.glorio.di.DaggerCategoryComponent;
import exp.glorio.view.adapters.CategoryAdapter;
import exp.glorio.view.fragments.CategoryFragment;
import exp.glorio.view.fragments.dialog.CategoryDialogFragment;
import exp.glorio.view.fragments.dialog.ConfirmDialogFragment;

public class CategoryActivity extends BaseActivity
        implements IHasComponent<CategoryComponent> {

    private final String RETAIN_CATEGORY = "categoryFragment";
    private final String DELETE_MODE = "deleteMode";

    private CategoryComponent mComponent;
    private CategoryFragment mCategoryFragment;

    boolean deleteMode;

    private CategoryDialogFragment mCategoryDialogFragment;
    private ConfirmDialogFragment mConfirmDialogFragment;

    FloatingActionButton fab;

    MenuItem trash;
    MenuItem delete;
    MenuItem back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCategoryDialogFragment.setTargetFragment(mCategoryFragment, 0);
                mCategoryDialogFragment.show(getSupportFragmentManager(), "new");
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        mCategoryFragment = (CategoryFragment) fm.findFragmentByTag(RETAIN_CATEGORY);

        if (mCategoryFragment == null) {
            mCategoryFragment = new CategoryFragment();
            fm.beginTransaction().replace(R.id.categoryFragmentContainer, mCategoryFragment, RETAIN_CATEGORY).commit();
        }

        mCategoryDialogFragment = new CategoryDialogFragment();

        if(savedInstanceState != null) {
            deleteMode = savedInstanceState.getBoolean(DELETE_MODE);
        }
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
                mCategoryFragment.getAdapter().cleanCheckedItems();
                changeMode();
                break;
            case R.id.actionDelete:
                deleteMode = false;
                mConfirmDialogFragment = new ConfirmDialogFragment();
                mConfirmDialogFragment.setTargetFragment(mCategoryFragment, 0);
                mConfirmDialogFragment.show(getSupportFragmentManager(), "confirm");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        trash = menu.findItem(R.id.actionTrash);
        back = menu.findItem(R.id.actionBack);
        delete = menu.findItem(R.id.actionDelete);
        changeMode();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        mComponent = DaggerCategoryComponent.builder()
                .appComponent(appComponent)
                .build();

        mComponent.inject(this);
    }

    @Override
    public CategoryComponent getComponent() {
        return mComponent;
    }

    private void changeMode() {
        if(mCategoryFragment.getAdapter() != null) {
            if (deleteMode) {
                trash.setVisible(false);
                delete.setVisible(true);
                back.setVisible(true);
                fab.setVisibility(View.INVISIBLE);
                mCategoryFragment.getAdapter().changeMode(CategoryAdapter.DELETE_MODE);
                mCategoryFragment.getAdapter().notifyDataSetChanged();
            } else {
                trash.setVisible(true);
                delete.setVisible(false);
                back.setVisible(false);
                fab.setVisibility(View.VISIBLE);
                mCategoryFragment.getAdapter().changeMode(CategoryAdapter.USUAL_MODE);
                mCategoryFragment.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DELETE_MODE, deleteMode);
    }
}
