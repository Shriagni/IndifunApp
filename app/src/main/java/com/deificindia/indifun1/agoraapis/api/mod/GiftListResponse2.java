package com.deificindia.indifun1.agoraapis.api.mod;

import java.util.List;

public class GiftListResponse2 {
    public List<GiftInfo2> result;
    String message;
    int status;

    public List<GiftInfo2> getResult() {
        return result;
    }

    public void setResult(List<GiftInfo2> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
