package com.deificindia.indifun1.dialogs.giftsheet;

//import com.deificindia.indifun1.agorlive.proxy.struts.model.GiftInfo;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agorlive.proxy.model.model.GiftInfo;

public class GiftItemListener {
    private static OnGiftItemSelectedListener _listenr;

    public static void setOnGiftItemSelectedListener(OnGiftItemSelectedListener listenr){
        _listenr = listenr;
    }

    public static void trigger(int pos, GiftInfo2 gift){
            if (_listenr!=null) _listenr.onItemSelected(pos, gift);
    }
}
