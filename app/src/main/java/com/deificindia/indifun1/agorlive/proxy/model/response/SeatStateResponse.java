package com.deificindia.indifun1.agorlive.proxy.model.response;

import java.util.List;

public class SeatStateResponse extends Response {
    public List<SeatInfo> data;

    public class SeatInfo {
        public int no;
        public String userId;
        public String userName;
        public int uid;
        public int state;
    }
}
