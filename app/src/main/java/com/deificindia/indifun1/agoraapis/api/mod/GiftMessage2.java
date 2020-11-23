package com.deificindia.indifun1.agoraapis.api.mod;

import com.deificindia.indifun1.agorlive.agora.rtm.RtmMessageManager;
import com.deificindia.indifun1.agorlive.agora.rtm.model.AbsRtmMessage;

public class GiftMessage2  extends AbsRtmMessage {
    public GiftMessageData data;

    public GiftMessage2() {}

    public GiftMessage2(GiftMessageData data) {
        cmd = RtmMessageManager.CHANNEL_MSG_TYPE_GIFT2;
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
