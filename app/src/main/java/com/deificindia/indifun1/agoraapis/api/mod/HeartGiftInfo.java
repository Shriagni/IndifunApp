package com.deificindia.indifun1.agoraapis.api.mod;


import com.deificindia.indifun1.agorlive.agora.rtm.RtmMessageManager;
import com.deificindia.indifun1.agorlive.agora.rtm.model.AbsRtmMessage;

public class HeartGiftInfo  extends AbsRtmMessage {
    public GiftMessageData data;

    public HeartGiftInfo(GiftMessageData data) {
        cmd = RtmMessageManager.CHANNEL_MSG_TYPE_GIFT3;
        this.data = data;
    }

    public HeartGiftInfo() { }

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
