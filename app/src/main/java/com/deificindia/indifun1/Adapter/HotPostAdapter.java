package com.deificindia.indifun1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.deificindia.indifun.Model.Hotmodel;
import com.deificindia.indifun1.Activity.ProfileActivity;
import com.deificindia.indifun1.Model.Hotpostmodel;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.interfaces.OnCommentUserClickListener;
import com.deificindia.indifun1.rest.RestWork;
import com.deificindia.indifun1.rest.RetroCalls;
import com.deificindia.indifun1.ui.LikeCommentPanel;
import com.deificindia.indifun1.ui.TagView;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.deificindia.indifun1.Utility.ActivityUtils.openUserDetailsActivity;
import static com.deificindia.indifun1.Utility.UiUtils.setGenderTag;
import static com.deificindia.indifun1.rest.RetroCallListener.FOLLOW_POST;

public class HotPostAdapter extends RecyclerView.Adapter<HotPostAdapter.HotPostViewHolder>{
    private final List<Hotpostmodel.MyResult> hotpostmodelList;

    WeakReference<Context> _context;
    Context context(){
        return this._context.get();
    }

    OnCommentUserClickListener _listener;

    public void setOnCommentUserClickListener(OnCommentUserClickListener listener){
        this._listener = listener;
    }

    public HotPostAdapter(List<Hotpostmodel.MyResult> hotpostmodelList, Context context) {
        this.hotpostmodelList = hotpostmodelList;
        this._context = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public HotPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hopost_adapter_layout, parent, false);
        return new HotPostViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//    }

    @Override
    public void onBindViewHolder(final HotPostViewHolder holder, int position) {
        final Hotpostmodel.MyResult item = hotpostmodelList.get(position);
        holder.hotpostnam.setText(item.getUser_name());

        if(item.getAge()!=null) holder.genderTag.getTagText().setText(""+item.getAge());
        if(item.getGender()!=null)
            setGenderTag(holder.genderTag, item.getGender());

        holder.hotposttime.setText(item.getTime());
//        holder.hotpostdistance.setText(mList.getFriends());

        if(item.getImage()!=null){
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+item.getImage())
                    .error(R.drawable.no_image)
                    .into(holder.hotpostdp);
        }

        holder.itemView.setOnClickListener(v -> {
            //Toast.makeText((android.content.Context) Context, mList.getUserName() + "was clicked", Toast.LENGTH_LONG).show()

        });

        holder.hotpostdp.setOnClickListener(v->{
            openUserDetailsActivity(context(), item.getPost_by(), item.getUser_name());
        });

        if(item.getImage()!=null  && !item.getImage().isEmpty()) holder.bind(item.getImage());

        if(item.getIs_likes()==1){
            holder.likeCommentPanel.likeButton.setLiked(true);
            holder.likeCommentPanel.likeButton.setEnabled(false);
            holder.likeCommentPanel.likeEnable(false);
        }

        holder.likeCommentPanel.setLikeCount(String.valueOf(item.getTotal_likes()));
        holder.likeCommentPanel.setComment(String.valueOf(item.getTotal_comments()));
        holder.likeCommentPanel.setOnUserClickListener(()->{
            if(_listener!=null){
                _listener.onComment(position,
                        item.getId(),
                        item.getPost_by()
                );
            }
        }, ()->{

        });
    }

    @Override
    public int getItemCount() {
        return hotpostmodelList.size();
    }

    public class HotPostViewHolder extends RecyclerView.ViewHolder {
        ImageView hotpostdp;
        TextView hotpostnam, hotposttime, hotpostdistance;

        TagView genderTag;
        AppCompatButton follow_button;

        RecyclerView recyclerView;

        LikeCommentPanel likeCommentPanel;

        public HotPostViewHolder(@NonNull View itemView) {
            super(itemView);
            hotpostdp = itemView.findViewById(R.id.hotpostdp);
            hotpostnam = itemView.findViewById(R.id.hotpostname);
            genderTag = itemView.findViewById(R.id.genderTag);

            hotposttime = itemView.findViewById(R.id.hotposttime);
            hotpostdistance = itemView.findViewById(R.id.hotpostdistance);

            recyclerView = itemView.findViewById(R.id.imagelayout);
            follow_button = itemView.findViewById(R.id.button);

            likeCommentPanel = itemView.findViewById(R.id.like_commentPanel);

            follow_button.setOnClickListener(v->{
                RestWork.FollowApi(follow_button, hotpostmodelList.get(getAdapterPosition()).getPost_by());
            });

          /*  comment_layout.setOnClickListener(v->{
                if(_listener!=null){
                    _listener.onComment(getAdapterPosition(),
                            hotpostmodelList.get(getAdapterPosition()).getId(),
                            hotpostmodelList.get(getAdapterPosition()).getPost_by(),
                            comment_layout
                    );
                }
            });*/

           /* likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    likeButton.setEnabled(false);
                    //likepost(holder,position,task.get(position).getId());
                    on_like(getAdapterPosition());
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                }
            });*/

            hotpostdp.setOnClickListener(v->{
                Intent int1 = new Intent(context(), ProfileActivity.class);
                int1.putExtra("UID", hotpostmodelList.get(getAdapterPosition()).getId());
                int1.putExtra("NAME", hotpostmodelList.get(getAdapterPosition()).getUser_name());
                //int1.putExtra("GENDER", hotpostmodelList.get(getAdapterPosition()).get());
                context().startActivity(int1);
            });

            //likeButton.setIconSizeDp(8);

        }

        void bind(List<Hotpostmodel.MyResult.MyImages> images){
            ImageListAdapter adapter = new ImageListAdapter(context(), images, 0);
            recyclerView.setLayoutManager(new GridLayoutManager(context(), 4));
            //recyclerView.addItemDecoration(new SpacesItemDecoration(5));
            recyclerView.setAdapter(adapter);
        }


        void on_like(int pos){
            RetroCalls.instance().callType(FOLLOW_POST)
                    .withUID(context())
                    .stringParam(hotpostmodelList.get(pos).getPost_by(), "")
                    .intParam(1)
                    .listeners((call_type,  obj) -> {
                        if(obj!=null){
                            PostModal ob = (PostModal) obj;
                            if(ob.getStatus()==1){
                                hotpostmodelList.get(pos).updateTotal_Likes();
                                notifyItemChanged(pos);
                            }else {

                            }
                        }

                    }, (type, code, message) -> {

                    });
        }

    }

    public void onPostComment(int pos){
        hotpostmodelList.get(pos).updateTotal_comments();
        notifyItemChanged(pos);


    }




}
