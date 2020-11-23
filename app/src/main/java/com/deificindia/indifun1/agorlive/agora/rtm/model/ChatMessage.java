package com.deificindia.indifun1.agorlive.agora.rtm.model;


import com.deificindia.indifun1.agorlive.agora.rtm.RtmMessageManager;

public class ChatMessage extends AbsRtmMessage {
    public ChatMessageData data;

    public ChatMessage(String fromUserId, String fromUserName, String message) {
        cmd = RtmMessageManager.CHANNEL_MSG_TYPE_CHAT;
        data = new ChatMessageData();
        data.fromUserId = fromUserId;
        data.fromUserName = fromUserName;
        data.message = message;
    }

    public static class ChatMessageData {
        public String fromUserId;
        public String fromUserName;
        public String message;
    }
}
