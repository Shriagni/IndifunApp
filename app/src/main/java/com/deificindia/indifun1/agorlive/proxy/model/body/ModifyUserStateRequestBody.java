package com.deificindia.indifun1.agorlive.proxy.model.body;

public class ModifyUserStateRequestBody {
    private int enableAudio;
    private int enableVideo;
    private int enableChat;

    public ModifyUserStateRequestBody(int enableAudio, int enableVideo, int enableChat) {
        this.enableAudio = enableAudio;
        this.enableVideo = enableVideo;
        this.enableChat = enableChat;
    }
}
