package com.deificindia.indifun1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.Activity.CountryWiseUser;
import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.Model.retro.NewstarModal;
import com.deificindia.indifun1.Model.retro.TrendingModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.UiUtils;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.Utility.api;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import static com.deificindia.indifun1.Utility.ActivityUtils.openUserDetailsActivity;

public class GlobalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int NEWSTAR = 0;
    public static final int COUNTRY = 1;
    public static final int TRENDING = 2;

    private LayoutInflater layoutInflater;

    int view_type = 0;
    public int user_rank = 0;
    Context context;

    List<NewstarModal.MyResult> list_newstar;
    List<CountryNamesResult.MyCountry> list_country;
    List<TrendingModal.MyResult> list_trending;

    public GlobalAdapter(int view_type, Context context, List<NewstarModal.MyResult> list) {
        this.view_type = view_type;
        this.context = context;
        this.list_newstar = list;
    }

    public GlobalAdapter(int view_type, List<CountryNamesResult.MyCountry> list_country, Context context) {
        this.view_type = view_type;
        this.context = context;
        this.list_country = list_country;
    }

    public GlobalAdapter(Context context, int view_type, List<TrendingModal.MyResult> list_trending) {
        this.view_type = view_type;
        this.context = context;
        this.list_trending = list_trending;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (layoutInflater==null){
            layoutInflater=LayoutInflater.from(parent.getContext());
        }

       /* NewstarModal.MyResult myListBinding= DataBindingUtil.inflate(layoutInflater,R.layout.item_live_follow_recomended, parent,false);
        return new ViewHolder(myListBinding);
*/

        switch (viewType) {
            case NEWSTAR:
                view = LayoutInflater.from(context).inflate(R.layout.item_live_follow_recomended, parent, false);
                return new NewstarHoler(view);
            case COUNTRY:
                view = LayoutInflater.from(context).inflate(R.layout.item_country, parent, false);
                return new CountryHolder(view);
            case TRENDING:
                view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false);
                return new TrendingHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case NEWSTAR:
                ((NewstarHoler) holder).bind(list_newstar.get(position));
                break;
            case COUNTRY:
                ((CountryHolder) holder).bind(list_country.get(position).getFlag(), list_country.get(position).getCountry());
                break;
            case TRENDING:
                ((TrendingHolder)holder).bind(list_trending.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return view_type;
    }

    @Override
    public int getItemCount() {
        switch (view_type) {
            case NEWSTAR:
                return list_newstar.size();
            case COUNTRY:
                return list_country.size();
            case TRENDING:
                return list_trending.size();
        }
        return 0;
    }

    /*----view holder classes-----*/
    class NewstarHoler extends RecyclerView.ViewHolder{

        TextView tvname;
        ImageView avtar;
        CircularProgressButton follow;

        public NewstarHoler(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.follow_name);
            avtar = itemView.findViewById(R.id.follow_icon);
            follow = itemView.findViewById(R.id.btn_id);

            follow.setOnClickListener(v->{
               // openUserDetailsActivity(context, usr.getId(), usr.getFullName());
            });

            avtar.setOnClickListener(v->{
                openUserDetailsActivity(context, list_newstar.get(getAdapterPosition()).getId(),
                        list_newstar.get(getAdapterPosition()).getFull_name());
            });
        }

        public void bind(NewstarModal.MyResult list){
            tvname.setText(list.getFull_name());
            Picasso.get().load(api.IMAGE_URL+list.getProfile_picture())
                    .error(ContextCompat.getDrawable(context, R.drawable.img_user_default))
                    .into(avtar);
        }
    }

    class CountryHolder extends RecyclerView.ViewHolder{

        ImageView imgFlag;
        TextView placeholder;

        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.imgFlag);
            placeholder = itemView.findViewById(R.id.tvPlaceholder);

            itemView.setOnClickListener(v->{
                context.startActivity(new Intent(context, CountryWiseUser.class));

                //
            });

        }

        public void bind(String imgurl, String country){
            //Picasso.get().load(CountryFlagImages+imgurl).error(ContextCompat.getDrawable(context, R.drawable.no_image)).into(imgFlag);
           /* new SVGLoader(imgurl, new SVGLoader.OnResultListener() {
                @Override
                public void onsuccessResult(Drawable drawable) {
                    if(drawable!=null) {
                        imgFlag.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        imgFlag.setImageDrawable(drawable);
                        placeholder.setVisibility(View.GONE);
                    }else {
                        loge("GlobalAdapter", "null drawable");
                        imgFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_image));
                        placeholder.setVisibility(View.VISIBLE);
                        placeholder.setText(country);
                    }
                }

                @Override
                public void onFaileListener(String msg) {
                    loge("GlobalAdapter", ""+msg);
                    imgFlag.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_image));
                    placeholder.setVisibility(View.VISIBLE);
                    placeholder.setText(country);
                }
            }).execute();*/
            UiUtils.fetchSvg(context, URLs.CountryFlagImages + imgurl, imgFlag, new UiUtils.onLoadData() {
                @Override
                public void onSuccess() {
                    placeholder.setVisibility(View.GONE);
                }

                @Override
                public void onFail() {
                    placeholder.setText(country);
                }
            });
        }
    }

    class TrendingHolder extends RecyclerView.ViewHolder{

        ImageView avtar;
        TextView tvName, tvMessage;
        View tagLay;

        public TrendingHolder(@NonNull View itemView) {
            super(itemView);

//            tvRank = itemView.findViewById(R.id.tv);
            avtar = itemView.findViewById(R.id.imgAvtar);
            tvName = itemView.findViewById(R.id.tvName);
            tagLay = itemView.findViewById(R.id.tagLay);
            tvMessage = itemView.findViewById(R.id.tvMessage);

            avtar.setOnClickListener(v->{
                openUserDetailsActivity(context, list_trending.get(getAdapterPosition()).getId(),
                        list_trending.get(getAdapterPosition()).getFullName());
            });

        }

        public void bind(TrendingModal.MyResult list_trending){
            tvName.setText(list_trending.getFullName());
            Picasso.get().load(api.IMAGE_URL+list_trending.getProfilePicture()).error(ContextCompat.getDrawable(context, R.drawable.img_user_default))
                    .into(avtar);
            user_rank++;
            //tvRank.setText(String.valueOf(user_rank));
            tvMessage.setText("");

        }
    }


}
