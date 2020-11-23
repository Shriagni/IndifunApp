package com.deificindia.indifun1.agorlive.ui.main.fragments;

import com.deificindia.indifun1.agorlive.proxy.ClientProxy;
import com.deificindia.indifun1.agorlive.ui.live.SingleHostLiveActivity;

public class SingleHostFragment extends AbsPageFragment {
    @Override
    protected int onGetRoomListType() {
        return ClientProxy.ROOM_TYPE_SINGLE;
    }

    @Override
    protected Class<?> getLiveActivityClass() {
        return SingleHostLiveActivity.class;
    }
}
