package exp.glorio.view.fragments;

import java.util.ArrayList;

import exp.glorio.model.data.Category;

public interface CategoryView {

    void showAllCategories(ArrayList<Category> categories);
    void refreshRecycler();
}
