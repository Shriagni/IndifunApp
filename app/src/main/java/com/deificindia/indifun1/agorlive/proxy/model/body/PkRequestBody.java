package com.deificindia.indifun1.agorlive.proxy.model.body;

public class PkRequestBody {
    public String roomId;
    public int type;

    public PkRequestBody(String roomId, int type) {
        this.roomId = roomId;
        this.type = type;
    }
}
