package com.deificindia.indifun1.agorlive.ui.main.fragments;

import com.deificindia.indifun1.agorlive.proxy.ClientProxy;
import com.deificindia.indifun1.agorlive.ui.live.HostPKLiveActivity;

public class PKHostInFragment extends AbsPageFragment {
    @Override
    protected int onGetRoomListType() {
        return ClientProxy.ROOM_TYPE_PK;
    }

    @Override
    protected Class<?> getLiveActivityClass() {
        return HostPKLiveActivity.class;
    }
}
