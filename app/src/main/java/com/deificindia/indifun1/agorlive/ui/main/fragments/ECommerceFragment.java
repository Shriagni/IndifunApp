package com.deificindia.indifun1.agorlive.ui.main.fragments;

import com.deificindia.indifun1.agorlive.proxy.ClientProxy;
import com.deificindia.indifun1.agorlive.ui.live.ECommerceLiveActivity;

public class ECommerceFragment extends AbsPageFragment {
    @Override
    protected int onGetRoomListType() {
        return ClientProxy.ROOM_TYPE_ECOMMERCE;
    }

    @Override
    protected Class<?> getLiveActivityClass() {
        return ECommerceLiveActivity.class;
    }
}
