package com.deificindia.indifun1.agorlive.agora.rtm.model;


import com.deificindia.indifun1.agorlive.proxy.model.response.EnterRoomResponse;

public class PKStateMessage extends AbsRtmMessage {
    public PKStateMessageBody data;

    public static class PKStateMessageBody {
        public int event;
        public int state;
        public long startTime;
        public long countDown;
        public int remoteRank;
        public int localRank;
        public int result;
        public EnterRoomResponse.RemoteRoom remoteRoom;
        public EnterRoomResponse.RelayConfig relayConfig;
    }
}