package exp.glorio.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import exp.glorio.R;
import exp.glorio.model.PostPOJO.Attachments;
import exp.glorio.model.PostPOJO.Link;
import exp.glorio.model.PostPOJO.Photo;
import exp.glorio.model.PostPOJO.Post;
import exp.glorio.presentation.PostPresenter;
import exp.glorio.util.VkUtil;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private ArrayList<Post> postArray;
    private Context context;

    private Date date;
    private SimpleDateFormat format;
    private PostPresenter mPresenter;
    private HashMap<Integer, Boolean> likeArray = new HashMap<>();

    public PostAdapter(ArrayList<Post> postArray, Context context, PostPresenter postPresenter) {
        this.postArray = postArray;
        this.context = context;
        format = new SimpleDateFormat("mm.HH dd.MM.yyyy", Locale.ROOT);
        mPresenter = postPresenter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;

        MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        View view = holder.itemView;

        ImageView groupLogo = (ImageView) view.findViewById(R.id.postGroupLogo);
        TextView groupName = (TextView) view.findViewById(R.id.postGroupName);

        TextView postText = (TextView) view.findViewById(R.id.postText);
        TextView postLikeIndex = (TextView) view.findViewById(R.id.postLikeIndex);
        TextView dateText = (TextView) view.findViewById(R.id.postDate);

        LinearLayout container = (LinearLayout) view.findViewById(R.id.postAttachmentsContainer);
        ImageView like = (ImageView) view.findViewById(R.id.postLike);

        setLikeStatus(postArray.get(position), like);

        Picasso.with(context).load(postArray.get(position).getGroupLogo100Url()).into(groupLogo);
        groupName.setText(postArray.get(position).getGroupName());
        postText.setText(postArray.get(position).getText());
        postLikeIndex.setText(postArray.get(position).getIndex());
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.putOrDeleteLike(postArray.get(position), postArray.get(position)
                        .getGroupId(), like, PostAdapter.this);
            }
        });
        date = new Date(postArray.get(position).getDate()*1000);
        dateText.setText(format.format(date));

        if(container.getChildCount() != 0 ) {
            container.removeAllViews();
        }

        ArrayList<Photo> photoList = new ArrayList<>();

        if(postArray.get(position).getAttachmentses() != null) {
            for (int i = 0; i != postArray.get(position).getAttachmentses().size(); i++) {
                if (postArray.get(position).getAttachmentses().get(i) == null) {
                }
                if (postArray.get(position).getAttachmentses().get(i) != null && postArray.get(position)
                        .getAttachmentses().get(i).getType().equals(VkUtil.PHOTO_ATTACHMENT)) {
                    photoList.add((Photo) postArray.get(position).getAttachmentses().get(i));
                }
            }

            if (!photoList.isEmpty() && photoList.size() > 1) {
                TextView photoCountText = (TextView) view.findViewById(R.id.postImageCount);
                photoCountText.setVisibility(View.VISIBLE);
                photoCountText.setText(String.valueOf(1));

                TextView photoCountTextAll = (TextView) view.findViewById(R.id.postImageCountAll);
                photoCountTextAll.setVisibility(View.VISIBLE);
                photoCountTextAll.setText("/" + String.valueOf(photoList.size()));

                Photo photo = photoList.get(0);
                ImageView image = new ImageView(context);
                image.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                container.addView(image);
                Picasso.with(context).load(photo.getPhoto604()).into(image);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Integer.parseInt(photoCountText.getText().toString()) < photoList.size()) {
                            photoCountText.setText(String.valueOf(Integer.parseInt(photoCountText.getText().toString()) + 1));
                        } else {
                            photoCountText.setText("1");
                        }
                        Picasso.with(context).load(photoList.get(Integer.parseInt(photoCountText
                                .getText().toString()) - 1).getPhoto604()).into(image);
                    }
                });
            } else if (photoList.size() == 1) {

                Photo photo = photoList.get(0);
                ImageView image = new ImageView(context);
                image.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                Picasso.with(context).load(photo.getPhoto604()).into(image);
                container.addView(image);
            }
        }

        if (postArray.get(position).getAttachmentses() != null) {
            for (int i = 0; i != postArray.get(position).getAttachmentses().size(); i++) {
                Attachments attachment = postArray.get(position).getAttachmentses().get(i);
                if(attachment != null && attachment.getType() != null) {
                    switch (attachment.getType()) {
                        case VkUtil.PHOTO_ATTACHMENT:
                            break;

                        case VkUtil.LINK_ATTACHMENT:
                            Link link = (Link) attachment;
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            View linkView = inflater.inflate(R.layout.attachment_link, null);
                            linkView.setBackgroundColor(context.getResources().getColor(R.color.linkBackground));
                            linkView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getUrl()));
                                    context.startActivity(browseIntent);
                                }
                            });

                            TextView linkTitle = (TextView) linkView.findViewById(R.id.attachmentLinkText);
                            ImageView linkImage = (ImageView) linkView.findViewById(R.id.attachmentLinkPhoto);

                            linkTitle.setText(link.getTitle());

                            if(link.getImageSrc() != null) {
                                Picasso.with(context).load(link.getImageSrc()).into(linkImage);
                            }
                            container.addView(linkView);
                    }
                }
            }
        }
    }

    public void setLikeStatus(Post post, ImageView like) {
        if(!getLikeArray().containsKey(post.getId())) {
            if(post.getUserLiked()){
                like.setImageDrawable(context.getResources().getDrawable(R.drawable.like_pressed));
            }else {
                like.setImageDrawable(context.getResources().getDrawable(R.drawable.like_unpressed));
            }
        }else if(getLikeArray().get(post.getId())) {
            like.setImageDrawable(context.getResources().getDrawable(R.drawable.like_pressed));
        } else if(!getLikeArray().get(post.getId())){
            like.setImageDrawable(context.getResources().getDrawable(R.drawable.like_unpressed));
        }

    }

    public HashMap<Integer, Boolean> getLikeArray() {
        return likeArray;
    }

    @Override
    public int getItemCount() {
        return postArray.size();
    }
}
