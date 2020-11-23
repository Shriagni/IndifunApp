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
import com.deificindia.indifun1.Model.retro.LiveResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.agoraapis.maths.CallActivity;

import java.util.List;

import static com.deificindia.indifun1.Utility.URLs.UserImageBaseUrl;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.HotViewHolder> {

    private final List<LiveResult> hotmodelList;
    private Context context;
//    private Hotmodel[] data;
//    public HotAdapter(List<Hotmodel> hotmodelList, LiveHotFragment context, Hotmodel[] data){
//        this.hotmodelList = hotmodelList;
//        this.context = context;
//        this.data = data;
//
//    }

    public HotAdapter(List<LiveResult> hotmodelList, Context context) {
        this.hotmodelList = hotmodelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hot_adapter_layout, parent, false);
        return new HotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HotViewHolder holder, int position) {
        final LiveResult mList = hotmodelList.get(position);
//        Hotmodel hotmodel = data[position];
        holder.hotusername.setText(mList.getUser_name());
        holder.hotfriends.setText(mList.getFriends());
        Glide.with(holder.imgicon.getContext()).load(UserImageBaseUrl+mList.getImage()).into(holder.imgicon);

    }

    @Override
    public int getItemCount() {
        return hotmodelList.size();
    }

    public class HotViewHolder extends RecyclerView.ViewHolder{
        ImageView imgicon;
        TextView hotusername, hotfriends;

        public HotViewHolder(@NonNull View itemView) {
            super(itemView);
            imgicon = itemView.findViewById(R.id.hotimage);
            hotusername = itemView.findViewById((R.id.hotusername));
            hotfriends = itemView.findViewById((R.id.hotfriends));

            itemView.setOnClickListener(v -> {
                //Toast.makeText(context, hotmodelList.get(getAdapterPosition()).getUser_name() + "was clicked", Toast.LENGTH_LONG).show();
                LiveResult itemobj = hotmodelList.get(getAdapterPosition());
                if(itemobj.getAdd_broadcast_id() !=null && itemobj.getAdd_broadcast_title() !=null)
                    CallActivity.joinSingleLiveActivity(context, itemobj);
                else
                    Toast.makeText(context, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();
            });

        }
    }
}
