package com.deificindia.indifun1.screenutils;

public class LiveLikeEntity {
    public int local_id;
    public String remote_id;

    public LiveLikeEntity(int i2, String str) {
        this.local_id = i2;
        this.remote_id = str;
    }

    public String toString() {
        return "LiveLikeEntity{, local_id=" + this.local_id + ", remote_id='" + this.remote_id + '\'' + '}';
    }

    public LiveLikeEntity() {
    }
}
