package com.deificindia.indifun1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.indifun1.Model.Likemodel_Result;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.URLs;

import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {

    private final List<Likemodel_Result> likemodelList;
    private Context context;

    public LikeAdapter(List<Likemodel_Result> likemodelList, Context context) {
        this.likemodelList = likemodelList;
        this.context = context;
    }


    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.like_adapter_layout, parent, false);
        return new LikeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {
        final Likemodel_Result nList = likemodelList.get(position);
        holder.likename.setText(nList.getFullName());
        holder.likeage.setText(nList.getAge());
        holder.liketime.setText(nList.getTime());
//        holder.recomtime.setText((Integer) nList.getLastActive());
        Glide.with(holder.wholikeimage.getContext()).load(URLs.UserImageBaseUrl+nList.getProfilePicture()).into(holder.wholikeimage);
        Glide.with(holder.whichimagelike.getContext()).load(URLs.UserImageBaseUrl+nList.getPostImage()).into(holder.whichimagelike);
        holder.itemView.setOnClickListener(v -> Toast.makeText(context, nList.getFullName() + "was clicked", Toast.LENGTH_LONG).show());

    }

    @Override
    public int getItemCount() {
        return likemodelList.size();
    }

    public static  class LikeViewHolder extends RecyclerView.ViewHolder {
        TextView likename, likeage, liketime;
        ImageView wholikeimage, whichimagelike;
        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);
            likename = itemView.findViewById(R.id.likename);
            likeage = itemView.findViewById(R.id.likeage);
            liketime = itemView.findViewById(R.id.liketime);
            wholikeimage = itemView.findViewById(R.id.wholikeimage);
            whichimagelike = itemView.findViewById(R.id.whichimagelike);
        }
    }
}
