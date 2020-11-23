package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.deificindia.indifun1.Adapter.FollowAdapterRecomended;
import com.deificindia.indifun1.Model.GetFollow_Result;
import com.deificindia.indifun1.Model.retro.LiveResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.Viewmodel.DataWrapper;
import com.deificindia.indifun1.Viewmodel.LiveViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun1.Utility.EqualSpacingItemDecoration.GRID;
import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;

public class CountryWiseUser extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView_country;
    private SwipeRefreshLayout /*wipe_user_by_country,*/ swipe_country_by_user;
    private List<LiveResult> getUser_by_country = new ArrayList<>();

    String str_uid;

    LiveViewModel userViewModel;


    FollowAdapterRecomended countryAdapteruser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_wise_user);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);


        str_uid = MySharePref.getUserId();

        /*country_by_user*/
        recyclerView_country = (RecyclerView) findViewById(R.id.recycler_country_users);
        swipe_country_by_user = findViewById(R.id.user_by_country);
        swipe_country_by_user.setOnRefreshListener(this);
        swipe_country_by_user.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        recyclerView_country.setHasFixedSize(false);
        recyclerView_country.setLayoutManager(layoutManager);
        recyclerView_country.addItemDecoration(new EqualSpacingItemDecoration(5, GRID));


        /**setting up things**/

        userViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);
        userViewModel.init();

        callcountryuserAdapter();

        swipe_country_by_user.setRefreshing(true);
        swipe_country_by_user.post(this::get_country_user_recommended);


    }

    @Override
    public void onRefresh() {
        if (userViewModel != null) {
            get_country_user_recommended();
        }

    }

    /*coutry_user*/
    void get_country_user_recommended() {
        if (userViewModel == null) return;

        userViewModel.follow_homepage_recommended(str_uid).observe(this, new Observer<DataWrapper<GetFollow_Result>>() {
            @Override
            public void onChanged(DataWrapper<GetFollow_Result> result) {
                loge("LiveFollowFrag", toGson("LiveFollowFrag",result));
                if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
                    getUser_by_country.clear();
                    getUser_by_country.addAll(result.data.getResult());
                    countryAdapteruser.notifyDataSetChanged();
                }
                swipe_country_by_user.setRefreshing(false);
            }
        });
    }

    void callcountryuserAdapter() {
        if (countryAdapteruser == null) {
            countryAdapteruser = new FollowAdapterRecomended(this, getUser_by_country);
            recyclerView_country.setAdapter(countryAdapteruser);
        } else {
            countryAdapteruser.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        userViewModel = null;
        super.onDestroy();
    }
}