package com.deificindia.indifun1.agorlive.proxy.model.response;

public class OssPolicyResponse extends Response {
    public OssPolicyInfo data;

    public class OssPolicyInfo {
        public String accessKey;
        public String host;
        public String policy;
        public String signature;
        public long expire;
        public String callback;
        public String dir;
    }
}
