package com.deificindia.indifun1.dialogs.giftsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agoraapis.api.mod.GiftListResponse2;
import com.deificindia.indifun1.agorlive.proxy.model.model.GiftInfo;
import com.deificindia.indifun1.agorlive.proxy.model.response.GiftListResponse;
import com.deificindia.indifun1.rest.AppConfig;
//import com.deificindia.indifun1.agorlive.proxy.struts.model.GiftInfo;
//import com.deificindia.indifun1.agorlive.proxy.struts.response.GiftListResponse;
import com.deificindia.indifun1.Utility.Logger;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage=1;

    ChildFragmentPagerAdapter adapter;
    ViewPager2 pager;
    TabLayout tab1;

    public static ParentFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ParentFragment fragment = new ParentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift_page, container, false);
        tab1 =  view.findViewById(R.id.tab_layout);
        pager =  view.findViewById(R.id.photos_viewpager);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //changeTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        callApi(mPage+1);

        return view;
    }

    void pagerInit(int gift_category, List<GiftInfo2> infoList){
        adapter = new ChildFragmentPagerAdapter(getChildFragmentManager(), getLifecycle(), gift_category, infoList);

        pager.setAdapter(adapter);
        new TabLayoutMediator(tab1, pager, (tab, position) -> {

        }).attach();

    }

    void callApi(int page){
        Logger.loge("ParentFrag", ""+page);
        Call<GiftListResponse2> chatmodelList = AppConfig.loadInterface().getGiftList(page);
        chatmodelList.enqueue(new Callback<GiftListResponse2>() {
            @Override
            public void onResponse(Call<GiftListResponse2> call, Response<GiftListResponse2> response) {
                GiftListResponse2 data = response.body();
                if(data!=null && data.getStatus()==1 && data.getResult()!=null){
                    //chatlist.setAdapter(new ChatAdapter(list.getResult(), getContext()));
                    List<GiftInfo2> infoList1 = data.getResult();
                    pagerInit(page, infoList1);
                }
            }

            @Override
            public void onFailure(Call<GiftListResponse2> call, Throwable t) {
                Toast.makeText(getContext(), "error occcured", Toast.LENGTH_LONG).show();

            }
        });
    }
}
