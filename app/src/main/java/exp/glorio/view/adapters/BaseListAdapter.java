package exp.glorio.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class BaseListAdapter extends RecyclerView.Adapter {

    public static final String USUAL_MODE = "USUAL";
    public static final String DELETE_MODE = "DELETE";
    public static final String PRESENTATION_MODE = "PRESENTATION";

    protected String mode;

    protected Context context;

    protected ArrayList<Long> deleteList;

    public BaseListAdapter(String mode, Context context) {
        this.mode = mode;
        this.context = context;
    }

    static protected class ViewHolder extends RecyclerView.ViewHolder {

        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @Override
    public  abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();

    protected boolean isInDeleteList(long id) {
//        if(!deleteList.isEmpty()) {
//            for (Long deleteItem : deleteList) {
//                if (deleteItem == id) {
//                    return true;
//                }
//            }
//        }
//
//        return false;

        if(!deleteList.isEmpty() && deleteList.contains(id)){
            Log.d("contain", "true");
            return true;
        }else {
            Log.d("contain", "false");
            return false;
        }
    }
}
