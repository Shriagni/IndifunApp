package com.deificindia.indifun1.ui.imagelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageListView extends RecyclerView {

    ImageViewAdapter adapter;

    int viewType = 1;

    public void init(){
        this.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
        this.setItemAnimator(new DefaultItemAnimator());
        this.addItemDecoration(new EqualSpacingItemDecoration(3, HORIZONTAL));
         this.adapter = new ImageViewAdapter();
        this.setAdapter(this.adapter);
    }

    public void updateData(List<MyImageModal> images){
        this.adapter.updateImages(images);
    }

    public ImageListView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ImageListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs){
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageListView);

            if (typedArray.hasValue(R.styleable.ImageListView_viewType)) {
                viewType = typedArray.getInt(R.styleable.ImageListView_viewType, 0);
            }

            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    class ImageViewAdapter extends Adapter<ViewHolder>{

        List<MyImageModal> images = new ArrayList<>();

        public ImageViewAdapter() { }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType){
                case 1:
                    return new ImageViewHolderCircular(
                            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circular_image, parent, false)
                    );
                case 2:
                    return new ImageViewHolderRect(
                            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circular_image, parent, false)
                    );
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MyImageModal modal = images.get(position);
            switch (modal.type){
                case 1:
                    ((ImageViewHolderCircular)holder).bind(modal);
                    break;
                case 2:
                    ((ImageViewHolderRect)holder).bind(modal);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        @Override
        public int getItemViewType(int position) {
            return images.get(position).type;
        }

        public void updateImages(List<MyImageModal> images){
            this.images.clear();
            this.images.addAll(images);
            notifyDataSetChanged();
        }
    }

    class ImageViewHolderCircular extends ViewHolder{

        CircleImageView circleImageView;
        public ImageViewHolderCircular(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.img1);
        }
        void bind(MyImageModal img){
            Picasso.get().load(img.image_link)
                    .into(circleImageView);
        }


    }
    class ImageViewHolderRect extends ViewHolder{

        ImageView imageView;
        public ImageViewHolderRect(@NonNull View itemView) {
            super(itemView);
            imageView = findViewById(R.id.img2);
        }

        void bind(MyImageModal img){
            Picasso.get().load(img.image_link)
                    .into(imageView);
        }


    }




}
