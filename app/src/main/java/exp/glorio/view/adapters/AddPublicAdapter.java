package exp.glorio.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

import exp.glorio.R;

public class AddPublicAdapter extends RecyclerView.Adapter<AddPublicAdapter.DialogViewHolder> {

    private VKList list;
    private Context context;

    private Integer selectedPosition = null;

    public AddPublicAdapter(VKList list, Context context) {
        this.list = list;
        this.context = context;
    }

    static class DialogViewHolder extends RecyclerView.ViewHolder {
        public View view;
        ImageView publicLogo;
        TextView publicName;
        TextView publicSubscribersCount;

        DialogViewHolder(final View itemView) {
            super(itemView);
            this.view = itemView;

            publicLogo = (ImageView) view.findViewById(R.id.addPublicLogo);
            publicName = (TextView) view.findViewById(R.id.addPublicName);
            publicSubscribersCount = (TextView) view.findViewById(R.id.addPublicSubscribers);
        }
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_public_dialog_list_item, parent, false);

        return new DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DialogViewHolder holder, int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
//                view.setBackgroundColor();
            }
        });

        if(selectedPosition != null && position == selectedPosition) {
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
        }else {
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.backgroundWhite));
        }

        try{
            Picasso.with(context).load(list.get(position).fields.getString("photo_50")).into(holder.publicLogo);
            holder.publicName.setText(list.get(position).fields.getString("name"));
            holder.publicSubscribersCount.setText(list.get(position).fields.getString("members_count"));
        }catch (JSONException e){}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getSelectedItem() {
        return selectedPosition;
    }
}
