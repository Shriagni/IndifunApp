package com.deificindia.indifun1.agorlive.ui.main.fragments;

import com.deificindia.indifun1.agorlive.proxy.ClientProxy;
import com.deificindia.indifun1.agorlive.ui.live.MultiHostLiveActivity;

public class HostInFragment extends AbsPageFragment {
    @Override
    protected int onGetRoomListType() {
        return ClientProxy.ROOM_TYPE_HOST_IN;
    }

    @Override
    protected Class<?> getLiveActivityClass() {
        return MultiHostLiveActivity.class;
    }
}
