package com.deificindia.indifun1.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.deificindia.indifun1.Fragment.ExploreFragment;
import com.deificindia.indifun1.Fragment.PostFragment;
import com.deificindia.indifun1.Fragment.LiveFragment;
import com.deificindia.indifun1.Fragment.ProfileFragment;
import com.deificindia.indifun1.Model.ApiResponseModal;
import com.deificindia.indifun1.Utility.ActivityUtils;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agoraapis.maths.CallActivity;
import com.deificindia.indifun1.agorlive.Config;
import com.deificindia.indifun1.agorlive.proxy.model.model.GiftInfo;
import com.deificindia.indifun1.agorlive.proxy.model.request.Request;
import com.deificindia.indifun1.agorlive.proxy.model.request.UserRequest;
import com.deificindia.indifun1.agorlive.proxy.model.response.AppVersionResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.CreateUserResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.GiftListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.LoginResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.MusicListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.Response;
import com.deificindia.indifun1.agorlive.ui.BaseActivity;
import com.deificindia.indifun1.agorlive.ui.live.LivePrepareActivity;
import com.deificindia.indifun1.agorlive.utils.Global;
import com.deificindia.indifun1.dialogs.FullScreenDialog;
import com.elvishew.xlog.XLog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.deificindia.indifun1.R;

import io.agora.rtm.ErrorInfo;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.logpk;
import static com.deificindia.indifun1.Utility.Logger.toGson;
import static com.deificindia.indifun1.fcm.Config.subscribetopic;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private static final int NETWORK_CHECK_INTERVAL = 10000;
    private static final int MAX_PERIODIC_APP_ID_TRY_COUNT = 5;

    public static Boolean streamopen = false;

    BottomNavigationView bottomNavigationView;

    private int mAppIdTryCount;

   /* @Override*/
    protected void onSendGift(int position, GiftInfo info) { }

    @Override
    protected void onSendGift(int position, GiftInfo2 info) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //startService(new Intent(HomeActivity.this, IndifunService.class));
        initAsync();
        subscribetopic();

        initNavigation();
        openFrag(0);
        locationpermission();


    }

    ///////////////////////////////////////////////////////////////////////
    private void initNavigation() {

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int selectedId = item.getItemId();
                if (selectedId == R.id.i_empty) {
                    centerBottun();
                    return false;
                } else if (selectedId == R.id.navigation_home) {
                   // fm.beginTransaction().hide(active).show(fragment1LiveFragment).commit();
                   /// active = fragment1LiveFragment;
                    openFrag(0);
                } else if (selectedId == R.id.navigation_ex) {
                   // fm.beginTransaction().hide(active).show(fragment2ExploreFragment).commit();
                   // active = fragment2ExploreFragment;
                    openFrag(1);
                } else if (selectedId == R.id.navigation_post) {
                    //fm.beginTransaction().hide(active).show(fragment3PostFragment).commit();
                    //active = fragment3PostFragment;
                    openFrag(2);
                } else if (selectedId == R.id.navigation_pro) {
                    //fm.beginTransaction().hide(active).show(fragment4ProfileFragment).commit();
                    //active = fragment4ProfileFragment;
                    openFrag(3);
                }

                return true;
            }

        });

    }

    //===================================================================
    int openedFrag = -1;
    void openFrag(int ind){
        switch (ind){
            case 0:
                if(openedFrag!=0)
                    replaceFragment(new LiveFragment(), "LiveFragment");
                break;
            case 1:
                if(openedFrag!=1)
                    replaceFragment(new ExploreFragment(), "ExploreFragment");
                break;
            case 2:
                if(openedFrag!=2)
                    replaceFragment(new PostFragment(), "PostFragment");
                break;
            case 3:
                if(openedFrag!=3)
                    replaceFragment(new ProfileFragment(), "ProfileFragment");
                break;
        }
        openedFrag = ind;
        hideKeyboard(HomeActivity.this);
    }

      private void replaceFragment(Fragment fragment, String tag) {
         removeFrag();
         getSupportFragmentManager().beginTransaction()
         .replace(R.id.container, fragment, tag)
         .addToBackStack(null)
         .commit();
     }

     private void removeFrag(){
         if(getSupportFragmentManager().findFragmentById(R.id.container)!=null){
             getSupportFragmentManager()
                     .beginTransaction()
                     .remove(getSupportFragmentManager().findFragmentById(R.id.container))
                     .commit();
         }
     }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //bottomNav.onSaveInstanceState(outState);
    }

    void centerBottun1(){
       // ActivityUtils.joinSingleLiveActivity(this, new LiveResult());
        startActivity(new Intent(HomeActivity.this, Test1Activity.class));
    }

    void centerBottun(){
        hideKeyboard(HomeActivity.this);
        startLive();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        //if(mRtmClient!=null) mRtmClient.logout(null);
        //stopService(new Intent(HomeActivity.this, IndifunService.class));
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    void locationpermission(){
         GoogleApiClient googleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(HomeActivity.this, 1);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    ///////////////////////Agora-Engine////////////////////////////////////////////////////

    private void initAsync() {
        new Thread(() -> {
            //checkUpdate();
            prelogin();
            getGiftList();
            getMusicList();
        }).start();
    }

    private void checkUpdate() {
        if (!config().hasCheckedVersion()) {
            sendRequest(Request.APP_VERSION, getAppVersion());
        }
    }

    @Override
    public void onAppVersionResponse(AppVersionResponse response) {
        config().setVersionInfo(response.data);
        config().setAppId(response.data.config.appId);
        application().initEngine(response.data.config.appId);
        Log.e("Main", "Appid ::" +response.data.config.appId);
        mAppIdTryCount = 0;
        login();
    }

    void prelogin(){
        //config().setVersionInfo("1.0.0");
        config().setAppId("df0a7c6c984646e28e74d37e550e01e1");
        application().initEngine("df0a7c6c984646e28e74d37e550e01e1");
        Log.e("Main", "Appid ::" );
        mAppIdTryCount = 0;
        login();
    }

    private void login() {
        Config.UserProfile profile = config().getUserProfile();
        initUserFromStorage(profile);
        if (!profile.isValid()) {
            createUser();
        } else {
            loginToServer();
        }
    }

    private void initUserFromStorage(Config.UserProfile profile) {
        profile.setUserId(preferences().getString(Global.Constants.KEY_PROFILE_UID, null));
        profile.setUserName(preferences().getString(Global.Constants.KEY_USER_NAME, null));
        profile.setImageUrl(preferences().getString(Global.Constants.KEY_IMAGE_URL, null));
        profile.setToken(preferences().getString(Global.Constants.KEY_TOKEN, null));
    }

    private void createUser() {
        String userName = IndifunApplication.getInstance().getCredential().getFullName();//RandomUtil.randomUserName(this);

        config().getUserProfile().setUserName(userName);
        preferences().edit().putString(Global.Constants.KEY_USER_NAME, userName).apply();
        sendRequest(Request.CREATE_USER, new UserRequest(userName));
    }

    @Override
    public void onCreateUserResponse(CreateUserResponse response) {
        logpk(TAG, "onCreateUserResponse", toGson(response));
        createUserFromResponse(response);
        loginToServer();
    }

    private void createUserFromResponse(CreateUserResponse response) {
        Config.UserProfile profile = config().getUserProfile();
        profile.setUserId(response.data.userId);
        preferences().edit().putString(Global.Constants.KEY_PROFILE_UID, profile.getUserId()).apply();
    }

    private void loginToServer() {
        sendRequest(Request.USER_LOGIN, config().getUserProfile().getUserId());
    }

    @Override
    public void onLoginResponse(LoginResponse response) {
        logpk(TAG, "onLoginResponse", toGson(response));
        if (response != null && response.code == Response.SUCCESS) {
            Config.UserProfile profile = config().getUserProfile();
            profile.setToken(response.data.userToken);
            profile.setRtmToken(response.data.rtmToken);
            profile.setAgoraUid(response.data.uid);
            preferences().edit().putString(Global.Constants.KEY_TOKEN, response.data.userToken).apply();
            joinRtmServer();

        }
    }

    private void joinRtmServer() {
        Config.UserProfile profile = config().getUserProfile();
        rtmClient().login(profile.getRtmToken(), String.valueOf(profile.getAgoraUid()), new io.agora.rtm.ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                XLog.d("rtm client login success:" + config().getUserProfile().getRtmToken());
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {

            }
        });
    }

    private void getGiftList() {
        sendRequest(Request.GIFT_LIST, null);
    }

    @Override
    public void onGiftListResponse(GiftListResponse response) {
        config().initGiftList(this);
    }

    private void getMusicList() {
        sendRequest(Request.MUSIC_LIST, null);
    }

    @Override
    public void onMusicLisResponse(MusicListResponse response) {
        config().setMusicList(response.data);
    }

    @Override
    public void onResponseError(int requestType, int error, String message) {
        XLog.e("request:" + requestType + " error:" + error + " msg:" + message);

        switch (requestType) {
            case Request.APP_VERSION:
                if (mAppIdTryCount <= MAX_PERIODIC_APP_ID_TRY_COUNT) {
                    new Handler(getMainLooper()).postDelayed(
                            HomeActivity.this::checkUpdate, NETWORK_CHECK_INTERVAL);
                    mAppIdTryCount++;
                }
                break;
            default: runOnUiThread(() -> showLongToast("Request type: "+
                    Request.getRequestString(requestType) + " " + message));
        }
    }

    private void startLive() {
        new CallActivity(HomeActivity.this, getSupportFragmentManager(), config())
                .start();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////



}