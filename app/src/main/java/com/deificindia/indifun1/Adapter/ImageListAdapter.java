package com.deificindia.indifun1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.indifun1.Activity.FullSizeImageActivity;
import com.deificindia.indifun1.Model.Hotpostmodel;
import com.deificindia.indifun1.Model.ImagesParsble;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.api;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun1.Utility.URLs.UserImageBaseUrl;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.HolderClass> {

    List<Hotpostmodel.MyResult.MyImages> images;
    List<String> images2;
    Context context;
    int typelist = 0;

    public ImageListAdapter(Context cnx, List<Hotpostmodel.MyResult.MyImages> images, int typelist) {
        this.context = cnx;
        this.images = images;
        this.typelist = typelist;
    }

    public ImageListAdapter(List<String> images2, Context context, int typelist) {
        this.images2 = images2;
        this.context = context;
        this.typelist = typelist;
    }

    @NonNull
    @Override
    public HolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HolderClass holder, int position) {
        if (typelist==0){
            holder.bind(images.get(position));
        }else {
            holder.bind(images2.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if(typelist==0){
            return images.size();
        }else {
            return images2.size();
        }

    }

    class HolderClass extends RecyclerView.ViewHolder{

        ImageView imageView;

        public HolderClass(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.myimage);

            itemView.setOnClickListener(v -> {
                if(context!=null) {
                    Intent intent = new Intent(context, FullSizeImageActivity.class);
                    ImagesParsble parsble = new ImagesParsble();
                    if (typelist == 0) {
                        List<String> arr = new ArrayList<>();
                        for (Hotpostmodel.MyResult.MyImages img : images) {
                            arr.add(img.getImage());
                        }
                        parsble.setImages(arr);
                    } else {
                        parsble.setImages(images2);
                    }

                    intent.putExtra("URL", parsble);
                    intent.putExtra("TAB", getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }

        void bind(Hotpostmodel.MyResult.MyImages model){
            if(model.getImage()!=null)
                Glide.with(context)
                        .load(UserImageBaseUrl+model.getImage())
                        .into(imageView);
        }

        void bind(String model){
            //if(model.getImage()!=null)
                Glide.with(context)
                        .load(model)
                        .into(imageView);
        }
    }
}
