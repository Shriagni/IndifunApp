package com.deificindia.indifun1.agoraapis.api;

import com.deificindia.indifun1.Model.ApiResponseModal;
import com.deificindia.indifun1.Model.retro.AddBroadcastingModal;
import com.deificindia.indifun1.agorlive.proxy.ClientProxyListener;
import com.deificindia.indifun1.bindingmodals.userleve.UserLevel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.URLs.BaseUrl;

public class ApiCall1 {
    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 30;
    private static final int MAX_RESPONSE_THREAD = 10;

    public static final String TAG = "ApiCall";

    public LocalApis1 mLocalServices;

    ApiCallbacks1 callbacks;

    static ApiCall1 INSTANCE;

    public static ApiCall1 instance(){
        if(INSTANCE==null)
            INSTANCE = new ApiCall1();

        return INSTANCE;
    }


    public ApiCall1 client(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder2 = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .callbackExecutor(Executors.newFixedThreadPool(MAX_RESPONSE_THREAD))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit1 = builder2.build();

        mLocalServices = retrofit1.create(LocalApis1.class);

        return this;
    }

    public ApiCall1 listener(ApiCallbacks1 listener){
        callbacks = listener;
        return this;
    }

    void errorTrigger(int n, String s){
        if(callbacks!=null) callbacks.onResponseError(n ,s);
    }

    public void is_broadcasting(String userid){
        loge("is_broadcasting", userid);
        mLocalServices.is_broadcasting(userid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody resp = response.body();
                        loge("is broadcasting", String.valueOf(resp));
                        if (resp != null) {
                            try {
                                String resdata = resp.string();
                                loge(TAG, resdata);
                                JSONObject object = new JSONObject(resdata);
                                int status = object.getInt("status");
                                String data = object.getString("result");
                                if(status==1){
                                    ApiResponseModal.IsBroadcasting_result result = new Gson().fromJson(data, ApiResponseModal.IsBroadcasting_result.class);
                                    if(callbacks!=null) callbacks.onResponseResult(1, true, result);

                                }else {
                                    if(callbacks!=null) callbacks.onResponseResult(1, false, null);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            if(callbacks!=null) callbacks.onResponseResult(1, false, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        errorTrigger(1,  t.getMessage());
                    }
                });
    }



    public void go_live_video_mode(String uid, String title){
        loge(TAG, uid, title);

        mLocalServices.go_live_video_mode(uid,title)
                .enqueue(new Callback<AddBroadcastingModal>() {
                    @Override
                    public void onResponse(Call<AddBroadcastingModal> call, Response<AddBroadcastingModal> response) {
                        AddBroadcastingModal resp = response.body();
                        if (resp != null && resp.getStatus()==1 && resp.getResult()!=null) {
                            if(callbacks!=null) callbacks.onResponseResult(2, true, resp.getResult());
                        }else {
                            if(callbacks!=null) callbacks.onResponseResult(2, true, resp.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<AddBroadcastingModal> call, Throwable t) {
                        errorTrigger(2,  t.getMessage());
                    }
                });
    }

    //update room id after room join success
    public void broadcasting_room_id(String broadcastid, String roomid, int btype){
        loge("broadcasting_room_id", broadcastid, roomid, ""+btype);
        mLocalServices.broadcasting_room_id(broadcastid, roomid, btype)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody resp = response.body();
                        loge("is broadcasting", String.valueOf(resp));
                        if (resp != null) {
                            /*try {
                                String resdata = resp.string();
                                loge(TAG, resdata);
                                JSONObject object = new JSONObject(resdata);
                                int status = object.getInt("status");
                                String data = object.getString("result");
                                loge("broadcasting_room_id", ""+status, data);
                                if(status==1){
                                    //ApiResponseModal.IsBroadcasting_result result = new Gson().fromJson(data, ApiResponseModal.IsBroadcasting_result.class);
                                    *//*for (ClientProxyListener listener : mProxyListeners) {
                                        listener.isUerBroadcasting(true, result);
                                    }*//*
                                }else {
                                    *//*for (ClientProxyListener listener : mProxyListeners) {
                                        listener.isUerBroadcasting(false, null);
                                    }*//*
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }else{
                            /*for (ClientProxyListener listener : mProxyListeners) {
                                listener.isUerBroadcasting(false, null);
                            }*/
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        errorTrigger(3,  t.getMessage());
                    }
                });
    }

    public void UPDATE_BROADCASTING(String userId, String braodcastid) {
        loge(TAG, "change_online_status", braodcastid);
        mLocalServices.update_broadcasting(userId, braodcastid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody resp = response.body();
                        if (resp != null) {
                            try {
                                String resdata = resp.string();
                                loge(TAG, resdata);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            errorTrigger(4, "null response");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        errorTrigger(4,  t.getMessage());
                    }
                });
    }

    /* user info apis...*/
    public void get_user_level(String userId) {
        mLocalServices.get_user_level(userId)
                .enqueue(new Callback<UserLevel>() {
                    @Override
                    public void onResponse(Call<UserLevel> call, Response<UserLevel> response) {
                        UserLevel resp = response.body();
                        if (resp != null && resp.status==1 && resp.result!=null) {
                            if(callbacks!=null) callbacks.onResponseResult(5,  resp.result);
                        }else{
                            errorTrigger(5, "null response");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLevel> call, Throwable t) {
                        errorTrigger(5,  t.getMessage());
                    }
                });
    }

    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////


}
