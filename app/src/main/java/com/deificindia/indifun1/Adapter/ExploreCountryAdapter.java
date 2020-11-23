package com.deificindia.indifun1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun1.Activity.CountryWiseUser;
import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.Model.CountryUserResult;
import com.deificindia.indifun1.Model.CountryUsers;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.Utility.UiUtils;
import com.deificindia.indifun1.Utility.api;
import com.deificindia.indifun1.ui.TagView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun1.Utility.URLs.CountryFlagImages;
import static com.deificindia.indifun1.Utility.UiUtils.setGenderTag;


public class ExploreCountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<CountryNamesResult.MyCountry> list_country = new ArrayList<>();
    List<CountryUserResult> list_country_user = new ArrayList<>();

    Context context;

    public static final int COUNTRY = 1;
    public static final int USERs = 2;

    int view_type = 1;
    int selectedCountry = 0;

    public ExploreCountryAdapter(Context context, int view_type) {
        this.context = context;
        this.view_type = view_type;
    }

    public ExploreCountryAdapter(List<CountryUserResult> list_country_user, Context context, int view_type) {
        this.list_country_user = list_country_user;
        this.context = context;
        this.view_type = view_type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = null;
        switch (viewType) {
            case COUNTRY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
                return new ExploreHolder(view);
            case USERs:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_user, parent, false);
                return new ExploreHolderUser(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case COUNTRY:
                ((ExploreHolder) holder).bind(list_country.get(position));
                break;
            case USERs:
                ((ExploreHolderUser)holder).bind(list_country_user.get(position));
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
            case COUNTRY:
                return list_country.size();
            case USERs:
                return list_country_user.size();
        }
        return 0;
    }

    class ExploreHolder extends RecyclerView.ViewHolder{
        View parent;
        SimpleDraweeView imgFlag;
        TextView placeholder;
        public ExploreHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.imgFlag);
            placeholder = itemView.findViewById(R.id.tvPlaceholder);
            parent = itemView.findViewById(R.id.parentlay);

            itemView.setOnClickListener(v->{
                //context.startActivity(new Intent(context, CountryWiseUser.class));
                selectedCountry = getAdapterPosition();
                if(_listener!=null) _listener.onCountryClick(getAdapterPosition(), list_country.get(getAdapterPosition()));

                notifyDataSetChanged();
            });
        }

        void bind(CountryNamesResult.MyCountry data) {
            new AsyncTask<String ,Void, String>(){

                @Override
                protected String doInBackground(String... voids) {
                    String extension = null;
                    String fileName = voids[0];
                    int i = fileName.lastIndexOf('.');
                    if (i > 0) {
                        extension = fileName.substring(i+1);
                    }

                    if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")){
                        return fileName;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if(s!=null){
                        //Picasso.get().load(s).into(imgFlag);
                        Uri uri = Uri.parse(CountryFlagImages+s);
                        imgFlag.setImageURI(uri);
                    }else {
                        placeholder.setVisibility(View.VISIBLE);
                        placeholder.setText(data.getCountry());
                    }
                }
            }.execute(data.getFlag());


            if(getAdapterPosition()==selectedCountry){
                parent.setBackgroundColor(context.getResources().getColor(R.color.blue_dialog));
            }else {
                parent.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

        }

    }

    OnCountryClickListener _listener;

    public interface OnCountryClickListener{
        void onCountryClick(int pos, CountryNamesResult.MyCountry country);
        //void onCountryLoad(CountryNamesResult.MyCountry country);
    }

    public void setOnCountryClickListener(OnCountryClickListener listener){
        this._listener = listener;
    }

    OnCountryClickListenerUser _listenerUser;

    public interface OnCountryClickListenerUser{
        void onCountryUserClick(CountryUserResult user);
    }

    public void setOnCountryUserClickListener(OnCountryClickListenerUser listenerUser){
        this._listenerUser = listenerUser;
    }

    class ExploreHolderUser extends RecyclerView.ViewHolder{
        ImageView imgAvtar;
        TextView tv1;
        TagView tag;
        public ExploreHolderUser(@NonNull View itemView) {
            super(itemView);
            imgAvtar = itemView.findViewById(R.id.imgAvtar);
            tv1 = itemView.findViewById(R.id.tvUsername);
            tag = itemView.findViewById(R.id.tag1);

            imgAvtar.setOnClickListener(v->{
                if(_listenerUser!=null) _listenerUser.onCountryUserClick(list_country_user.get(getAdapterPosition()));
            });
        }

        void bind(CountryUserResult data){
            tv1.setText(data.getFullName());
            Picasso.get().load(api.IMAGE_URL+data.getProfilePicture())
                    .error(ContextCompat.getDrawable(context, R.drawable.img_user_default))
                    .into(imgAvtar);
            //tag.changeBg(R.drawable.bg_tag_fill_stroke_1);
            tag.getTagText().setText(data.getAge());
            setGenderTag(tag, data.getGender());
        }

    }

    public void updateUsers(List<CountryUserResult> list){
        list_country_user.clear();
        list_country_user.addAll(list);
        notifyDataSetChanged();
    }

    public void updateCountry(List<CountryNamesResult.MyCountry> list){
        list_country.clear();
        list_country.addAll(list);
        notifyDataSetChanged();
    }
}
