package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun1.Model.retro.UserProfile;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.bindingmodals.otheruserprofile.Language;
import com.deificindia.indifun1.bindingmodals.otheruserprofile.OtherUserProfile;
import com.deificindia.indifun1.bindingmodals.otheruserprofile.OtherUserProfileResult;
import com.deificindia.indifun1.databinding.ActivityUserDetailsActivityBinding;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.ui.imagelistview.ImageListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;

public class UserDetailsActivityActivity extends AppCompatActivity {

   /* TextView tv_profile_name ,tv_whatsup,
            Moments_count, gift_count,medal_count,
            level_text,activity_text,join_date_text;
    Button btn_follow;
    ImageView backbtn, level_image;
    View statustags, moment, gift, topfan, level, medal, other, like;

    ImageListView moment_recycler,gifts_recycler,topfans_recycler,
            medals_recycler;*/

    ActivityUserDetailsActivityBinding binding;
    String other_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_details_activity);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details_activity);

        //binding.setUdm();

       /* statustags = findViewById(R.id.linear);
        moment = findViewById(R.id.moment_count);
        gift = findViewById(R.id.gifts_linear);
        topfan = findViewById(R.id.topfans_linear);
        level = findViewById(R.id.level_count);
        medal = findViewById(R.id.medals_linear);
        other = findViewById(R.id.ls_linear);
        like = findViewById(R.id.like);

        //////////////////////////////////////////////////
        tv_profile_name = findViewById(R.id.profile_name);
        tv_whatsup = findViewById(R.id.largetext);
        tv_profile_name = findViewById(R.id.profile_name);
        Moments_count = findViewById(R.id.Moments);
        gift_count = findViewById(R.id.gift_no);
        medal_count = findViewById(R.id.medal_no);
        level_text = findViewById(R.id.level_text);
        activity_text = findViewById(R.id.activity_text);
        join_date_text = findViewById(R.id.date_text);
        join_date_text = findViewById(R.id.date_text);


        backbtn = findViewById(R.id.mBack);
        level_image = findViewById(R.id.level_image);
        btn_follow = findViewById(R.id.follow_btn1);


        moment_recycler = findViewById(R.id.moment_recycler);
        gifts_recycler = findViewById(R.id.gifts_recycler);
        topfans_recycler = findViewById(R.id.topfans_recycler);
        medals_recycler = findViewById(R.id.medals_recycler);

*/
        init_data();
        listeners();
        getUserProfile();
    }

    void init_data(){
        String name = getIntent().getStringExtra("NAME");
        other_user_id = getIntent().getStringExtra("UID");

        binding.profileName.setText(name);
    }

    void listeners(){
        binding.mback.setOnClickListener(v->finish());
        binding.followBtn1.setOnClickListener(v->{});
        binding.like.setOnClickListener(v->{});
    }

    void getUserProfile(){
        String selfId= IndifunApplication.getInstance().getCredential().getId();
        loge("UserDetails", "profile", other_user_id, selfId);
        AppConfig.loadInterface().other_user_profile(selfId, other_user_id)
        .enqueue(new Callback<OtherUserProfile>() {
            @Override
            public void onResponse(Call<OtherUserProfile> call, Response<OtherUserProfile> response) {
                //loge("UserDetails", "profile", toGson(response));
                if(response.isSuccessful()){
                    OtherUserProfile userProfile = response.body();
                    if(userProfile!=null && userProfile.getStatus()==1 && userProfile.getResult()!=null){

                        //binding.setUdm(userProfile.getResult());

                        setData(userProfile.getResult());
                       // loge("UserDetails", "profile", toGson(userProfile.getResult()));
                    }
                }
            }

            @Override
            public void onFailure(Call<OtherUserProfile> call, Throwable t) {
                    loge("USERDETAIL", "getUserProfile", ""+t.getMessage());
            }
        });
    }

    void setData(OtherUserProfileResult d){
        if(d.getMomentPic()!=null && !d.getMomentPic().isEmpty()) {
            binding.momentcount.setVisibility(View.VISIBLE);
            binding.moments.setText(d.momentpiccount());
        }

        binding.relationText.setText(d.getRelationship());
        if(d.getLanguage()!=null && !d.getLanguage().isEmpty()) {
            List<String> langs = new ArrayList<>();
            for(Language l : d.getLanguage()){
                langs.add(l.getLanguageName());
            }
           binding.langtags.setTags(langs);
        }
        //binding.cityText.setText(d.city);
        if(d.getGift()!=null && !d.getGift().isEmpty()){
            binding.giftLinear.setVisibility(View.VISIBLE);
            binding.giftno.setText(d.giftcount());

            //binding.giftsRecycler.updateData(d.getGift());
        }

        if(d.getGroup()!=null){
            binding.groups.setVisibility(View.VISIBLE);
            binding.groupscount.setText(d.groupcount());
        }

        binding.levelText.setText(d.getLevel());

        if(d.getMedals()!=null && !d.getMedals().isEmpty()){
            binding.medalslinear.setVisibility(View.VISIBLE);
            binding.medalno.setText(d.medalscount());
        }

        binding.lsLinear.setVisibility(View.GONE);











    }


}