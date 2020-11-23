package com.deificindia.indifun1.agoraapis.api;

import com.deificindia.indifun1.Model.ApiResponseModal;

public interface ApiCallbacks1 {

    void onResponseError(int callNo, String msg);
    //void onBroadcasteResult();
    void onResponseResult(int callNo, Object... msg);
}
