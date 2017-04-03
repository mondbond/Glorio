package exp.glorio.presentation;

import java.util.ArrayList;

import javax.inject.Inject;

import exp.glorio.common.BasePresenter;
import exp.glorio.model.DbRepository;
import exp.glorio.view.fragments.CategoryView;

public class CategoryPresenter implements BasePresenter<CategoryView> {

    CategoryView mView;
    DbRepository mRepository;

    @Inject
    public CategoryPresenter(DbRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public void init(CategoryView view) {
        this.mView = view;
    }

    public void getAllCategories() {
        mRepository.loadAllCategories().subscribe(res -> mView.showAllCategories(res));
    }

    public void deleteSelectedItems(ArrayList<Long> checkedItems) {
       mRepository.deleteSelectedCategory(checkedItems).subscribe(res -> {
           if(res) {
               getAllCategories();
           }
       });
    }

    public void createCategory(String categoryName) {
        mRepository.createNewCategory(categoryName)
                .subscribe(res -> {
                    if(res) {
                        getAllCategories();
                    }
                });
    }
}
