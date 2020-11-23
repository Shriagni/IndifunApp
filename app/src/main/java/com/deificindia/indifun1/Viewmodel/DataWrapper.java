package com.deificindia.indifun1.Viewmodel;

public class DataWrapper <T>{
    public T data;
    public String error;

    public DataWrapper(T data, String error) {
        this.data = data;
        this.error = error;
    }
}
