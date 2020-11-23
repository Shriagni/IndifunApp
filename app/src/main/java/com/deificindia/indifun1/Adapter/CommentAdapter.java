package com.deificindia.indifun1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.Model.retro.Commentmodel_Result;
import com.deificindia.indifun1.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final List<Commentmodel_Result> commentmodelList;
    private Context context;
    public String agotime;
    private Activity activity;

    public CommentAdapter(List<Commentmodel_Result> commentmodelList, Context context) {
        this.commentmodelList = commentmodelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_adapter_layout, parent, false);
        return new CommentViewHolder(view);
    }

    public String setAgoTime(String date, String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date past = format.parse(date+'T'+time);
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes= TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours= TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60){
                agotime = (seconds + " seconds");
            }
            else if(minutes<60){
                agotime = (minutes + " minutes");
            }
            else if(hours<24){
                agotime = (hours + " hours");
            }
            else {
                agotime = (days + " days");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("time",e.getMessage());
        }
        return agotime;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        String Imageurl = "https://deificindia.com/indifun/assets/images/user/";
        String Imageurl2 = "https://deificindia.com/indifun/assets/images/post/";
        final Commentmodel_Result nList = commentmodelList.get(position);
        holder.Commentname.setText(nList.getFullName());
        holder.commentage.setText(nList.getAge());
        holder.maincomment.setText(nList.getComments());

        holder.commenttime.setText(setAgoTime(nList.getEntryDate(), nList.getTime()));
//        holder.recomtime.setText((Integer) nList.getLastActive());

        if (nList.getProfilePicture() != null && !nList.getProfilePicture().isEmpty()) {

            Picasso.get().load(Imageurl+nList.getProfilePicture())
                    .error(R.drawable.no_image)
                    .into(holder.whocommentimage);
        } else {
            holder.whocommentimage.setImageDrawable(holder.whocommentimage.getContext().getResources().getDrawable(R.drawable.no_image));
        }

        if (nList.getPostImage() != null && !nList.getPostImage().isEmpty()) {

            Picasso.get().load(Imageurl2+nList.getPostImage())
                    .error(R.drawable.no_image).into(holder.whichimagecomment);
        } else {
            holder.whichimagecomment.setImageDrawable(holder.whichimagecomment.getContext().getResources().getDrawable(R.drawable.no_image));
        }
        holder.itemView.setOnClickListener(v -> Toast.makeText(context, nList.getFullName() + "was clicked", Toast.LENGTH_LONG).show());

    }

    @Override
    public int getItemCount() {
        return commentmodelList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView Commentname, commentage, commenttime, maincomment;
        ImageView whocommentimage, whichimagecomment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            Commentname = itemView.findViewById(R.id.commentname);
            commentage = itemView.findViewById(R.id.commentage);
            commenttime = itemView.findViewById(R.id.comenttime);
            maincomment = itemView.findViewById(R.id.maincomment);
            whocommentimage = itemView.findViewById(R.id.whocommentimage);
            whichimagecomment = itemView.findViewById(R.id.whichimagecomment);
        }
    }
}
