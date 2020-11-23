package com.deificindia.indifun1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.indifun1.Model.Recommendadmodel;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.rest.RetroCalls;
import com.deificindia.indifun1.ui.TagView;
import com.deificindia.indifun1.ui.imagelistview.ImageListView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.deificindia.indifun1.Utility.ActivityUtils.openUserDetailsActivity;
import static com.deificindia.indifun1.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun1.Utility.UiUtils.setGenderTag;
import static com.deificindia.indifun1.rest.RetroCallListener.ONFOLLOWCLICK;

/*
*
* */
public class RecommendadAdapter extends RecyclerView.Adapter<RecommendadAdapter.RecommendadViewHolder> {

    private final List<Recommendadmodel> recommendadmodelList;
    private Context context;

    public RecommendadAdapter(List<Recommendadmodel> recommendadmodelList, Context activity) {
        this.recommendadmodelList = recommendadmodelList;
        this.context = activity;
    }

    @NonNull
    @Override
    public RecommendadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendad_adapter_layout, parent, false);
        return new RecommendadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendadViewHolder holder, int position) {
        final Recommendadmodel mList = recommendadmodelList.get(position);
        holder.recomname.setText(mList.getFullName());

        if(mList.getAge()!=null)
            holder.tagGender.getTagText().setText(mList.getAge());

        if(mList.getGender()!=null){
            setGenderTag(holder.tagGender, mList.getGender());
        }
        Picasso.get().load(UserImageBaseUrl+mList.getProfilePicture())
                .into(holder.userimage);

        if(mList.getWhatsup()!=null && !mList.getWhatsup().isEmpty()){
            holder.tvWhatsup.setVisibility(View.VISIBLE);
            holder.tvWhatsup.setText(mList.getWhatsup());
        }else holder.tvWhatsup.setVisibility(View.GONE);

        if(mList.getIsBroadcasting().equalsIgnoreCase("1")){
            holder.liveLayout.setVisibility(View.VISIBLE);
            holder.imageListView.setVisibility(View.GONE);
            Picasso.get().load(UserImageBaseUrl+mList.getProfilePicture()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.liveLayout.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

            Picasso.get().load(UserImageBaseUrl+mList.getProfilePicture())
                    .into(holder.imgLive);
        }else {
            holder.imageListView.setVisibility(View.GONE);
            holder.liveLayout.setVisibility(View.GONE);
        }

        holder.userimage.setOnClickListener(v ->{
            openUserDetailsActivity(context, mList.getId(), mList.getFullName());
        });

    }

    @Override
    public int getItemCount() {
        return recommendadmodelList.size();
    }

    public static class RecommendadViewHolder extends RecyclerView.ViewHolder {
        ImageView userimage, img1like;
        CircleImageView imgLive;
        TextView recomname, tvWhatsup;
        TagView tagGender;
        View tagsLayout, liveLayout;
        ImageListView imageListView;

        public RecommendadViewHolder(@NonNull View itemView) {
            super(itemView);
            userimage = itemView.findViewById(R.id.recomimage);
            recomname = itemView.findViewById(R.id.username);
            tvWhatsup = itemView.findViewById(R.id.tvWhatsup);

            tagGender = itemView.findViewById(R.id.gendertag);

            img1like = itemView.findViewById(R.id.imageView6);
            tagsLayout = itemView.findViewById(R.id.tagsLayout);
            liveLayout = itemView.findViewById(R.id.liveLayout);
            imgLive = itemView.findViewById(R.id.imgLive);
            imageListView = itemView.findViewById(R.id.imgMomentImages);
        }

    }

    void like(){
       /* RetroCalls.instance().callType(ONFOLLOWCLICK)
                //.withUID(context)
                .stringParam(task.get(getAdapterPosition()).getUser_id())
                .listeners(obj -> {
                    if(obj!=null){
                        PostModal ob = (PostModal) obj;

                    }
                }, (type, code, message) -> {

                });*/

    }

    void heart(){
        /*RetroCalls.instance().callType(ONFOLLOWCLICK)
                //.withUID(context)
                .stringParam(task.get(getAdapterPosition()).getUser_id())
                .listeners(obj -> {
                    if(obj!=null){
                        PostModal ob = (PostModal) obj;

                    }
                }, (type, code, message) -> {

                });*/

    }
}
