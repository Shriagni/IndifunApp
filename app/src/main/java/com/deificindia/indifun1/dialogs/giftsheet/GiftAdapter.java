package com.deificindia.indifun1.dialogs.giftsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.R;
//import com.deificindia.indifun1.agorlive.proxy.struts.model.GiftInfo;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agorlive.proxy.model.model.GiftInfo;

import com.deificindia.indifun1.Utility.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter {
    List<GiftInfo2> giftlist = new ArrayList<>();
    int mSelected = -1;

    int tabindex =1;
    int pageindex =1;
    Context context;

    String mValueFormat;

    public GiftAdapter(List<GiftInfo2> giftlist, int tabindex, int pageindex, Context cnx) {
        mValueFormat = cnx.getResources().getString(R.string.live_room_gift_action_sheet_value_format_2);
        this.giftlist = giftlist;
        this.tabindex = tabindex;
        this.pageindex = pageindex;
        this.context = cnx;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GiftViewHolder(LayoutInflater.from(context).
                inflate(R.layout.action_gift_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GiftInfo2 info = giftlist.get(position);
        GiftViewHolder giftViewHolder = (GiftViewHolder) holder;
        //giftViewHolder.name.setText(info.getName());
        giftViewHolder.value.setText(String.format(mValueFormat, info.getPoint()));
        giftViewHolder.setPosition(position);
        giftViewHolder.itemView.setActivated(mSelected == position);

       /* Picasso.get().load(URLs.GifgBaseUrl+info.getCover())
                .error(context.getResources().getDrawable(R.drawable.empty_gift))
                .into(giftViewHolder.icon);*/
    }

    @Override
    public int getItemCount() {
        return giftlist.size();
    }

    private class GiftViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView icon;
        AppCompatTextView name;
        AppCompatTextView value;
        int position;

        GiftViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.live_room_action_sheet_gift_item_icon);
            name = itemView.findViewById(R.id.live_room_action_sheet_gift_item_name);
            value = itemView.findViewById(R.id.live_room_action_sheet_gift_item_value);

            itemView.setOnClickListener(view -> {
                mSelected = position;
                notifyDataSetChanged();
                GiftItemListener.trigger(position, giftlist.get(position));
            });
        }

        void setPosition(int position) {
            this.position = position;
        }
    }



}
