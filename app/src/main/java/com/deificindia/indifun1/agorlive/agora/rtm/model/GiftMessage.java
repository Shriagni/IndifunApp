package com.deificindia.indifun1.agorlive.agora.rtm.model;

public class GiftMessage {
    public GiftMessageData data;

    public GiftMessage(GiftMessageData data) {
        this.data = data;
    }

    public static class GiftMessageData {
         public String fromUserId;
         public String fromUserName;
         public String fromUserAvtar;
         public String fromUserLevel;
         public String toUserId;
         public String toUserName;
         public int giftId;
        public int gift_category;
        public String json_animation;
    }
}
