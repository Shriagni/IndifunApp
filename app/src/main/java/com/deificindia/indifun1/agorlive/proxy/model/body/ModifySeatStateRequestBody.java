package com.deificindia.indifun1.agorlive.proxy.model.body;

public class ModifySeatStateRequestBody {
    private String userId;
    private int state;
    private int no;
    private String virtualAvatar;

    public ModifySeatStateRequestBody(int no, String userId, int state, String virtualAvatar) {
        this.no = no;
        this.userId = userId;
        this.state = state;
        this.virtualAvatar = virtualAvatar;
    }
}
