package com.deificindia.indifun1.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.Model.retro.LiveResult;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.agoraapis.maths.CallActivity;
import com.deificindia.indifun1.rest.RestWork;
import com.deificindia.indifun1.rest.RetroCalls;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import static com.deificindia.indifun1.rest.RetroCallListener.ONFOLLOWCLICK;

public class FollowAdapterRecomended extends RecyclerView.Adapter<FollowAdapterRecomended.ViewHolder> {
    private Activity activity;
    private List<LiveResult> task;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;

    public FollowAdapterRecomended(Activity activity, List<LiveResult> task){
        this.activity = activity;
        this.task = task;
    }

    @Override
    public FollowAdapterRecomended.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_follow_recomended, parent, false);
        return new FollowAdapterRecomended.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FollowAdapterRecomended.ViewHolder holder, final int pos) {
//        if(type.equals("bfollow")){
        LiveResult data = task.get(pos);
        holder.follow_name.setText(data.getUser_name());

        if (data.getImage() != null && !data.getImage().isEmpty()) {

            Picasso.get().load(URLs.UserImageBaseUrl+data.getImage()).error(R.drawable.no_image)
                    .into(holder.follow_icon);
        } else {
            holder.follow_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_image));
        }

    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView follow_icon;
        TextView follow_name;
        AppCompatButton follow_button1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            follow_icon = itemView.findViewById(R.id.follow_icon);
            follow_name = itemView.findViewById(R.id.follow_name);
            follow_button1 = itemView.findViewById(R.id.btn_id);

            follow_icon.setOnClickListener(v -> {
                LiveResult itemobj = task.get(getAdapterPosition());
                if (itemobj.getAdd_broadcast_id() != null && itemobj.getAdd_broadcast_title() != null){
                    CallActivity.joinSingleLiveActivity(activity, itemobj);
            }else{
                    Toast.makeText(activity, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();
                }
            });

           follow_button1.setOnClickListener(v ->
                   RestWork.FollowApi(follow_button1, task.get(getAdapterPosition()).getIs_broadcasting())
           );

        }


    }
}
