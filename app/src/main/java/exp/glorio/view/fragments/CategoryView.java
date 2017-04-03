package exp.glorio.view.fragments;

import java.util.ArrayList;

import exp.glorio.model.data.Category;

/**
 * Created by User on 06.03.2017.
 */

public interface CategoryView {

    void showAllCategories(ArrayList<Category> categories);
    void refreshRecycler();
}
