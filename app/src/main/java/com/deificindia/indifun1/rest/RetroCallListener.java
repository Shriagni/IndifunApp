package com.deificindia.indifun1.rest;

public class RetroCallListener {

    public interface OnSuccessListener{
        void onSuccessResult(int type_result, Object obj);
    }

    public interface OnFailListener{
        void onError(int type_result, int code, String message);
    }

    public static final int ONFOLLOWCLICK = 1;
    public static final int FOLLOW_POST = 2;
    public static final int USER_PROFILE = 3;
    public static final int GET_PROFILE_PHOTOS = 4;
    public static final int ADD_PROFILE_PHOTOS = 5;
    public static final int USER_PROFILE_UPDATE = 6;
    public static final int USER_INTERESTS = 7;
    public static final int LANGUAGE_LIST = 8;
    public static final int GET_POST = 9;
    public static final int BROADCAST_BETWEEN = 10;
    public static final int GENERATE_TOKEN = 11;
    public static final int REQUEST_TOKEN = 12;


}
