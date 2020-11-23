package com.deificindia.indifun1.dialogs.giftsheet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.deificindia.indifun1.Utility.Partition;
//import com.deificindia.indifun1.agorlive.proxy.struts.model.GiftInfo;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agorlive.proxy.model.model.GiftInfo;

import java.util.List;

public class ChildFragmentPagerAdapter extends FragmentStateAdapter {

    int gift_category = 1;
    List<GiftInfo2> infoList;
    List<List<GiftInfo2>> listinlist =null;

    public ChildFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int gift_category, List<GiftInfo2> infoList) {
        super(fragmentManager, lifecycle);
        this.gift_category = gift_category;
        this.infoList = infoList;
        if(infoList.size()> 8) {
            this.listinlist = Partition.ofSize(infoList, 8);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(listinlist!=null){
            return ChildFragment.newInstance(gift_category, position, listinlist.get(position));
        }else {
            return ChildFragment.newInstance(gift_category, position, infoList);
        }

    }

    @Override
    public int getItemCount() {
        if(listinlist==null){
            return 1;
        }else {
            return listinlist.size();
        }
    }

}
