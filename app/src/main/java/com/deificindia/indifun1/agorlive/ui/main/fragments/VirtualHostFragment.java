package com.deificindia.indifun1.agorlive.ui.main.fragments;

import com.deificindia.indifun1.agorlive.proxy.ClientProxy;
import com.deificindia.indifun1.agorlive.ui.live.VirtualHostLiveActivity;

public class VirtualHostFragment extends AbsPageFragment {
    @Override
    protected int onGetRoomListType() {
        return ClientProxy.ROOM_TYPE_VIRTUAL_HOST;
    }

    @Override
    protected Class<?> getLiveActivityClass() {
        return VirtualHostLiveActivity.class;
    }
}
