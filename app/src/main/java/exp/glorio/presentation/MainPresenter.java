package exp.glorio.presentation;

import javax.inject.Inject;
import exp.glorio.common.BasePresenter;
import exp.glorio.model.DbRepository;
import exp.glorio.view.fragments.MainView;

public class MainPresenter implements BasePresenter<MainView>{

    private MainView mView;

    private DbRepository mRepository;

    @Inject
    public MainPresenter(DbRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public void init(MainView view) {
        this.mView = view;
    }

    public void loadCategoryList() {
        mRepository.loadAllCategories().subscribe(categories -> mView.showCategoryList(categories));
    }
}
