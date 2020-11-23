package com.deificindia.indifun1.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.deificindia.indifun1.Model.agoramodel.GenerateToken;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.agorlive.Config;
//import com.deificindia.indifun1.agorlive.utils.AgoraSettings;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.rtm.ChatManager;

import java.io.IOException;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun1.Utility.Logger.loge;

public class IndifunService extends Service {

    public static final String TAG = "IndifunService";
    public IndifunService() {}
///
    private RtmClient mRtmClient;
    private boolean mIsInChat = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        starEngines();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    void starEngines(){
        new Thread(()->{
            if(IndifunApplication.getInstance().getCredential() != null){
                //IndifunApplication.getInstance().initRtm();
                initRtmEngine();

                //rtc
               /* AgoraSettings.instance()
                        .registerListener()
                        .with(getApplicationContext(), IndifunApplication.getInstance().getSharedPreferences(),
                                IndifunApplication.getInstance().config(),
                                IndifunApplication.getInstance().rtmClient())
                        .login()

                        .getGiftList();*/

            }
        }).start();

    }

    void initRtmEngine(){
        //ChatManager mChatManager = IndifunApplication.getInstance().getChatManager();
        ///mRtmClient = mChatManager.getRtmClient();

        if(IndifunApplication.getInstance().getCredential()!=null){
            getRtcToken();
        }
    }

    @Override
    public void onDestroy() {

        if(IndifunApplication.getInstance().rtmClient()!=null){
            IndifunApplication.getInstance().rtmClient().logout(null);
            IndifunApplication.getInstance().rtmClient().release();
        }

        if(IndifunApplication.getInstance().rtcEngine()!=null){
            IndifunApplication.getInstance().rtcEngine().destroy();
        }
        super.onDestroy();
    }

    public void getRtcToken(){
        String uid = IndifunApplication.getInstance().getCredential().getId();
        Call<ResponseBody> call = AppConfig.loadInterface().generate_rtc_token(uid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String token = response.body().string();
                    String mUserId = IndifunApplication.getInstance().getCredential().getId();
                    loge("INDIFUNSERVICE", mUserId);
                    doLogin(token, mUserId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void doLogin(String token, String mUserId) {
        loge(TAG, "doLogin",token, mUserId);
        mIsInChat = true;
        String profile = IndifunApplication.getInstance().config().getUserProfile().getRtcToken();
        mRtmClient.login(profile, mUserId, new ResultCallback<Void>() {

            @Override
            public void onSuccess(Void responseInfo) {
                Log.e(TAG, "login success");

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.e(TAG, "login failed: " + errorInfo.getErrorCode());
                mIsInChat = false;
            }
        });
    }

}