package com.deificindia.indifun1.rest;

import android.content.Context;

import com.deificindia.indifun1.Model.agoramodel.GenerateToken;
import com.deificindia.indifun1.Model.agoramodel.RequestToken;
import com.deificindia.indifun1.Model.retro.AddProfilePhotos;
import com.deificindia.indifun1.Model.retro.GetPost;
import com.deificindia.indifun1.Model.retro.GetProfilePhotos;
import com.deificindia.indifun1.Model.retro.LanguageList;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.Model.retro.UserInterests;
import com.deificindia.indifun1.Model.retro.UserProfile;
import com.deificindia.indifun1.Model.retro.UserProfileUpdate;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Logger;
import com.deificindia.indifun1.Utility.MySharePref;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;
import static com.deificindia.indifun1.rest.RetroCallListener.*;

/*
RetroCalls.instance().callType(ONFOLLOWCLICK).listeners(obj -> {

}, (type, code, message) -> {

});
* */

public class RetroCalls {

    private static final String TAG = "RetroCalls";

    int call_type;
    String str_uid;
    String[] str_params;
    int[] int_params;

    static RetroCalls INST;
    RetroCallListener.OnSuccessListener onsuccess;
    RetroCallListener.OnFailListener onFail;

    public static synchronized RetroCalls instance(){
        if(INST==null)
            INST = new RetroCalls();
        return INST;
    }

    public RetroCalls callType(int ctype){
        this.call_type = ctype;
        return this;
    }

    public RetroCalls withUID(Context cnx){
        this.str_uid = IndifunApplication.getInstance().getCredential().getId();
        return this;
    }

    public RetroCalls withUID(){
        this.str_uid = IndifunApplication.getInstance().getCredential().getId();
        return this;
    }

    public RetroCalls stringParam(String... str){
        this.str_params = str;
        return this;
    }
    public RetroCalls intParam(int... ints){
        this.int_params = ints;
        return this;
    }

    public void listeners(RetroCallListener.OnSuccessListener onSuccess, RetroCallListener.OnFailListener onFail){
        this.onsuccess = onSuccess;
        this.onFail = onFail;
        this.callMethods();
    }

    void callMethods(){
        switch (call_type){
            case ONFOLLOWCLICK:
                live_follow_click();
                break;
            case FOLLOW_POST:
                follow_post();
                break;
            case USER_PROFILE:
                USER_PROFILE();
                break;
            case GET_PROFILE_PHOTOS:
                GET_PROFILE_PHOTOS();
                break;
            case ADD_PROFILE_PHOTOS:
                ADD_PROFILE_PHOTOS();
                break;
            case USER_PROFILE_UPDATE:
                USER_PROFILE_UPDATE();
                break;
            case USER_INTERESTS:
                USER_INTERESTS();
                break;
            case LANGUAGE_LIST:
                LANGUAGE_LIST();
                break;
            case GET_POST:
                GET_POST();
                break;
            case BROADCAST_BETWEEN:
                broadcast_between();
                break;
            case GENERATE_TOKEN:
                generate_token();
                break;
            case REQUEST_TOKEN:
                request_token();
                break;


        }
    }

    /*201023*/
    private void live_follow_click() {
        //Logger.loge(TAG, str_uid, str_params[0]);
        Call<PostModal> call = AppConfig.loadInterface().live_follow_click(this.str_uid, str_params[0]);
        call.enqueue(new Callback<PostModal>() {
            @Override
            public void onResponse(Call<PostModal> call, Response<PostModal> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<PostModal> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }


    //like comment
    private void follow_post() {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<PostModal> call = AppConfig.loadInterface().follow_post(this.str_uid, str_params[0], int_params[0], str_params[1]);
        call.enqueue(new Callback<PostModal>() {
            @Override
            public void onResponse(Call<PostModal> call, Response<PostModal> response) {

                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<PostModal> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }

    /*201024*/
    private void USER_PROFILE(/*String user_id*/) {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<UserProfile> call = AppConfig.loadInterface().user_profile(this.str_uid);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }
    private void GET_PROFILE_PHOTOS(/*String user_id,String image*/) {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<GetProfilePhotos> call = AppConfig.loadInterface().get_profile_photos(this.str_uid);
        call.enqueue(new Callback<GetProfilePhotos>() {
            @Override
            public void onResponse(Call<GetProfilePhotos> call, Response<GetProfilePhotos> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<GetProfilePhotos> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }
    private void ADD_PROFILE_PHOTOS(/*String user_id*/) {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<AddProfilePhotos> call = AppConfig.loadInterface().add_profile_photos(this.str_uid, str_params[0]);
        call.enqueue(new Callback<AddProfilePhotos>() {
            @Override
            public void onResponse(Call<AddProfilePhotos> call, Response<AddProfilePhotos> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<AddProfilePhotos> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }
    private void USER_PROFILE_UPDATE(/*String user_id,
                                     String full_name,
                                     String gender,
                                      String whatsup,
                                     String relationship,
                                     String country,
                                     String state,
                                     String city*/
    ) {

        Logger.loge(TAG, str_uid, str_params[0]);
        Call<UserProfileUpdate> call = AppConfig.loadInterface().user_profile_update(
                this.str_uid, str_params[0],  str_params[1],
                "","","","",""
        );
        call.enqueue(new Callback<UserProfileUpdate>() {
            @Override
            public void onResponse(Call<UserProfileUpdate> call, Response<UserProfileUpdate> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<UserProfileUpdate> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }
    private void USER_INTERESTS(/*String user_id*/) {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<UserInterests> call = AppConfig.loadInterface().user_interests(this.str_uid);
        call.enqueue(new Callback<UserInterests>() {
            @Override
            public void onResponse(Call<UserInterests> call, Response<UserInterests> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<UserInterests> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }
    private void LANGUAGE_LIST(/*String user_id*/) {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<LanguageList> call = AppConfig.loadInterface().language_list(this.str_uid);
        call.enqueue(new Callback<LanguageList>() {
            @Override
            public void onResponse(Call<LanguageList> call, Response<LanguageList> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<LanguageList> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }
    private void GET_POST(/*String user_id*/) {
        Logger.loge(TAG, str_uid, str_params[0]);
        Call<GetPost> call = AppConfig.loadInterface().get_post(this.str_uid);
        call.enqueue(new Callback<GetPost>() {
            @Override
            public void onResponse(Call<GetPost> call, Response<GetPost> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<GetPost> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }

    private void broadcast_between(/*String user_id*/) {
        Logger.loge(TAG, str_params[0], str_params[1]);
        Call<ResponseBody> call = AppConfig.loadInterface().broadcast_between(str_params[0], str_params[1]);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }

    private void generate_token() {
        Logger.loge(TAG,"generate_token", str_params[0], str_params[1], str_params[2]);
        Call<GenerateToken> call = AppConfig.loadInterface().generate_token(str_params[0], str_params[1], str_params[2]);
        call.enqueue(new Callback<GenerateToken>() {
            @Override
            public void onResponse(Call<GenerateToken> call, Response<GenerateToken> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<GenerateToken> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });
    }

    private void request_token() {
        loge(TAG, "request_token", str_params[0], ""+int_params[0]);
        Call<RequestToken> call = AppConfig.loadInterface().request_token(str_params[0], int_params[0]);
        call.enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(Call<RequestToken> call, Response<RequestToken> response) {
                if(onsuccess!=null){
                    onsuccess.onSuccessResult(call_type, response.body());
                }
            }

            @Override
            public void onFailure(Call<RequestToken> call, Throwable t) {
                if(onFail!=null){
                    onFail.onError(call_type, 1, t.getMessage());
                }
            }
        });

    }



}
