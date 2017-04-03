package exp.glorio.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import exp.glorio.R;
import exp.glorio.model.data.Category;
import exp.glorio.view.activity.PostActivity;
import exp.glorio.view.activity.PublicActivity;

public class  CategoryAdapter extends BaseListAdapter {

    private ArrayList<Category> categoryList;

    public CategoryAdapter(ArrayList<Category> list, String mode, Context context) {
        super(mode, context);
        this.categoryList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;

        TextView categoryName = (TextView) view.findViewById(R.id.categoryName);
        categoryName.setText(categoryList.get(position).getCategoryName());

        if(mode.equals(DELETE_MODE)) {
            if(deleteList == null) {
                deleteList = new ArrayList<Long>();
            }

            final ImageView checkBox = (ImageView) view.findViewById(R.id.categoryCheckBox);
            if(isInDeleteList(categoryList.get(position).getId())){
                checkBox.setImageResource(R.drawable.checkbox_true_icon);
            }else {
                checkBox.setImageResource(R.drawable.checkbox_false_icon);
            }

            checkBox.setVisibility(View.VISIBLE);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(isInDeleteList(categoryList.get(position).getId())) {
                        checkBox.setImageResource(R.drawable.checkbox_false_icon);
                        deleteList.remove(categoryList.get(position).getId());
                    }else {
                        checkBox.setImageResource(R.drawable.checkbox_true_icon);
                        deleteList.add(categoryList.get(position).getId());
                    }
                }
            });
        } else {
            ImageView checkBox = (ImageView) view.findViewById(R.id.categoryCheckBox);
            checkBox = (ImageView) view.findViewById(R.id.categoryCheckBox);
            checkBox.setVisibility(View.INVISIBLE);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mode.equals(PRESENTATION_MODE)){
                        Intent intent = new Intent(context, PostActivity.class) ;
                        intent.putExtra(PublicActivity.CATEGORY_NAME, categoryList.get(position).getId());
                        context.startActivity(intent);
                    } else if(mode.equals(DELETE_MODE)){

                    }else {
                        Intent intent = new Intent(context, PublicActivity.class);
                        intent.putExtra(PublicActivity.CATEGORY_NAME, categoryList.get(position).getId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public ArrayList<Long> getCheckedItems() {
        return deleteList;
    }
    public void cleanCheckedItems() {
        this.deleteList.clear();
        notifyDataSetChanged();
    }
    public void changeMode(String newMode) {
        mode = newMode;
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
