package com.deificindia.indifun1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.Model.retro.ChatModel_Result;
import com.deificindia.indifun1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<ChatModel_Result> chatmodelList;
    private Context context;
    public String agotime;
    private Activity activity;

    public ChatAdapter(List<ChatModel_Result> chatmodelList, Context context) {
        this.chatmodelList = chatmodelList;
        this.context = context;
    }

//    public LikeAdapter(List<Likemodel_Result> result, Context context) {
////
////    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_adapter_layout, parent, false);
        return new ChatViewHolder(view);

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
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        final ChatModel_Result nList = chatmodelList.get(position);
        holder.senderName.setText(nList.getUsername1());

        holder.msgTiming.setText(setAgoTime(nList.getDate(), nList.getTime()));

        //Picasso.get().load(UserImageBaseUrl+nList.g)

//        if (nList.getProfilePicture() != null && !nList.getProfilePicture().isEmpty()) {
//
//            Picasso.with(holder.SenderDP.getContext()).load(Imageurl+nList.getProfilePicture()).error(R.drawable.no_image).into(holder.SenderDP, new Callback() {
//                @Override
//                public void onSuccess() {
//                    XLog.Log.d("TAG", "onSuccess");
//                }
//
//                @Override
//                public void onError() {
//                    //  Toast.makeText(DocumnetManagementActivity.this, "An error occurred in loading image", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            holder.SenderDP.setImageDrawable(holder.SenderDP.getContext().getResources().getDrawable(R.drawable.no_image));
//        }
    }
//        holder.itemView.setOnClickListener(v -> Toast.makeText(context, nList.getFullName() + "was clicked", Toast.LENGTH_LONG).show());

//    }

    @Override
    public int getItemCount() {
        return chatmodelList.size();
    }

    public static  class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView senderName, msg, msgTiming;
        ImageView SenderDP;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.senderName);
            msg = itemView.findViewById(R.id.msg);
            msgTiming = itemView.findViewById(R.id.msgTiming);
            SenderDP = itemView.findViewById(R.id.SenderDP);
        }
    }
}

