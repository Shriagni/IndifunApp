package com.deificindia.indifun1.agorlive.proxy.model.response;

public class RefreshTokenResponse extends Response {
    public TokenData data;

    public class TokenData {
        public String rtcToken;
        public String rtmToken;
    }
}
