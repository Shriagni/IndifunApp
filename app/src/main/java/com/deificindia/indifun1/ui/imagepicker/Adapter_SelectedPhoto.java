package com.deificindia.indifun1.ui.imagepicker;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.ui.imagepicker.custom.adapter.BaseRecyclerViewAdapter;

public class Adapter_SelectedPhoto extends BaseRecyclerViewAdapter<Uri, Adapter_SelectedPhoto.SelectedPhotoHolder> {



    int closeImageRes;

    ImagePickerActivity imagePickerActivity;

    public Adapter_SelectedPhoto(ImagePickerActivity imagePickerActivity, int closeImageRes) {
        super(imagePickerActivity);
        this.imagePickerActivity = imagePickerActivity;
        this.closeImageRes = closeImageRes;


    }

    @Override
    public void onBindView(SelectedPhotoHolder holder, int position) {

        Uri uri = getItem(position);

        Glide.with(imagePickerActivity)
                .load(uri.toString())
             //   .override(selected_bottom_size, selected_bottom_size)
                .dontAnimate()
                .centerCrop()
                .error(R.drawable.no_image)
                .into(holder.selected_photo);




        holder.iv_close.setTag(uri);



    }

    @Override
    public SelectedPhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(R.layout.ip_picker_list_item_selected_thumbnail, parent, false);
        return new SelectedPhotoHolder(view);
    }





    class SelectedPhotoHolder extends RecyclerView.ViewHolder {


        ImageView selected_photo;
        ImageView iv_close;


        public SelectedPhotoHolder(View itemView) {
            super(itemView);
            selected_photo = (ImageView) itemView.findViewById(R.id.selected_photo);
            iv_close = (ImageView) itemView.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = (Uri) view.getTag();
                    imagePickerActivity.removeImage(uri);
                }
            });

        }





    }
}
