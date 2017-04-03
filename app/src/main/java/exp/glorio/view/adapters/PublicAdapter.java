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

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import java.util.ArrayList;

import exp.glorio.R;
import exp.glorio.view.activity.DetailPublicActivity;
import exp.glorio.view.activity.PublicActivity;

public class PublicAdapter  extends BaseListAdapter {

    private VKList vkList;

    ArrayList<Long> deleteList = new ArrayList<Long>();

    public PublicAdapter(VKList list, String mode, Context context) {
        super(mode, context);
        this.vkList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.public_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        View view = holder.itemView;

        ImageView publicLogo = (ImageView) view.findViewById(R.id.publicLogo);
        TextView publicName = (TextView) view.findViewById(R.id.publicName);
        final ImageView checkBox = (ImageView) view.findViewById(R.id.publicCheckBox);




        try {

            if (isInDeleteList(vkList.get(position).fields.getInt("id"))) {
                checkBox.setImageResource(R.drawable.checkbox_true_icon);
            }else {
                checkBox.setImageResource(R.drawable.checkbox_false_icon);
            }

            Log.d("7", vkList.get(position).fields.getString("photo_50"));
            Picasso.with(context).load(vkList.get(position).fields.getString("photo_50")).into(publicLogo);
            publicName.setText(vkList.get(position).fields.getString("name"));
        } catch (JSONException e) {}

        if (mode.equals(DELETE_MODE)) {

            checkBox.setVisibility(View.VISIBLE);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (isInDeleteList(vkList.get(position).fields.getInt("id"))) {
                            checkBox.setImageResource(R.drawable.checkbox_false_icon);
                            deleteList.remove(Long.valueOf(vkList.get(position).fields.getInt("id")));
                        } else {
                            checkBox.setImageResource(R.drawable.checkbox_true_icon);
                            deleteList.add(Long.valueOf(vkList.get(position).fields.getInt("id")));
                            Log.d("Dlist", deleteList.toString());
                        }
                    } catch (JSONException e) {
                    }
                }
            });
        } else {
//            ImageView checkBox = (ImageView) view.findViewById(R.id.publicCheckBox);
            checkBox.setVisibility(View.INVISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mode.equals(DELETE_MODE)){

                    }else {
                        Intent intent = new Intent(context, DetailPublicActivity.class);
                        try {
                            intent.putExtra(DetailPublicActivity.PUBLIC_NAME, vkList.get(position)
                                    .fields.getInt("id"));
                        } catch (JSONException e) {
                        }
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return vkList.size();
    }

    protected boolean isInDeleteList(long id) {
        if (!deleteList.isEmpty()) {
            for (Long deleteItem : deleteList) {
                if (deleteItem == id) {
                    return true;
                }
            }
        }

        return false;
    }

    public ArrayList<Long> getCheckedItems() {
        return deleteList;
    }

    public void cleanCheckedItems() {
        this.deleteList.clear();
    }
    public void changeMode(String newMode) {
        mode = newMode;
    }

    public VKList getVkList() {
        return vkList;
    }

    public void setVkList(VKList vkList) {
        this.vkList = vkList;
    }
}
