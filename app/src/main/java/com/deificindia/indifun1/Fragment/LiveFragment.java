package com.deificindia.indifun1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun1.Activity.HomeActivity;
import com.deificindia.indifun1.Activity.LeaderBoardActivity;
import com.deificindia.indifun1.Model.ApiResponseModal;
import com.deificindia.indifun1.Model.ModalInviteUser;
import com.deificindia.indifun1.Model.retro.AddBroadcastingModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.agoraapis.maths.CallActivity;
import com.deificindia.indifun1.agorlive.proxy.interfaces.StartStopPkResponse;
import com.deificindia.indifun1.agorlive.proxy.model.request.Request;
import com.deificindia.indifun1.agorlive.proxy.model.response.ModifySeatStateResponse;
import com.deificindia.indifun1.agorlive.ui.live.LivePrepareActivity;
import com.deificindia.indifun1.agorlive.ui.main.fragments.AbstractFragment;
import com.deificindia.indifun1.agorlive.utils.Global;
import com.deificindia.indifun1.anim.PagerAnimations;
import com.deificindia.indifun1.dialogs.FullScreenDialog;
import com.deificindia.indifun1.ui.ImageArea;
import com.deificindia.indifun1.ui.IndiTab.IndiTabLayout;
import com.deificindia.indifun1.ui.IndiTab.IndiViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import static com.deificindia.indifun1.Utility.Logger.loge;


public class LiveFragment extends AbstractFragment {
    ViewPager2 viewPager;
    TabLayout tabLayout;
    ImageView call_board, startliveroom;

    public LiveFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        viewPager = v.findViewById(R.id.viewpager);
        tabLayout = v.findViewById(R.id.FragmentTab);
        call_board = v.findViewById(R.id.call_board);
        startliveroom = v.findViewById(R.id.startliveroom);

        setUpPager();

        call_board.setOnClickListener(v2->{
            Intent i = new Intent(getActivity(), LeaderBoardActivity.class);
            startActivity(i);
        });

        startliveroom.setOnClickListener(v3->{
            centerBottun();
        });
    }

    private void setUpPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Follow");
                            break;
                        case 1:
                            tab.setText("Universal");
                            break;
                    }
                }
        ).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return getFrag(position);
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        Fragment getFrag(int pos){
            if(pos==1){
                return new LiveHotFragment();
            }else {
                return new LiveFollowFragment();
            }
        }
    }

    void centerBottun(){
        new CallActivity(getContext(), getChildFragmentManager(), config())
                .start();
    }





}