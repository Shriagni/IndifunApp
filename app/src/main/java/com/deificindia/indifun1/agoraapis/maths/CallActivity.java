package com.deificindia.indifun1.agoraapis.maths;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.deificindia.indifun1.Model.ApiResponseModal;
import com.deificindia.indifun1.Model.retro.LiveResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.agoraapis.api.AbstractApiCallbacks1;
import com.deificindia.indifun1.agoraapis.api.ApiCall1;
import com.deificindia.indifun1.agoraapis.api.ApiCallbacks1;
import com.deificindia.indifun1.agorlive.Config;
import com.deificindia.indifun1.agorlive.proxy.model.model.RoomInfo;
import com.deificindia.indifun1.agorlive.ui.live.HostPKLiveActivity;
import com.deificindia.indifun1.agorlive.ui.live.LivePrepareActivity;
import com.deificindia.indifun1.agorlive.utils.Global;
import com.deificindia.indifun1.dialogs.FullScreenDialog;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;
import static com.deificindia.indifun1.Utility.MySharePref.getUserId;

public class CallActivity extends AbstractApiCallbacks1 {

    Context context;
    FragmentManager fragmentManager;
    Config config;

    public CallActivity(Context context, FragmentManager fragmentManager, Config config) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.config = config;
    }

    public CallActivity() { }

    public void start(){
        FullScreenDialog dialog = new FullScreenDialog();
        dialog.setOnOkClickListener(new FullScreenDialog.OnOkClickListener() {
            @Override
            public void onOkClick() {
                is_user_broadcasting();
            }
        });
        dialog.show(fragmentManager, "FullScreenDialog");
    }

    void is_user_broadcasting() {
        String uid = MySharePref.getUserId();
        ApiCall1.instance().client().listener(new ApiCallbacks1() {
            @Override
            public void onResponseError(int callNo, String msg) {
                Toast.makeText(context,"Error in joining broadcast, check your internet connection", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseResult(int callNo, Object... msg) {
                if(callNo==1){
                    boolean b = (boolean) msg[0];
                    if(b){
                        if(msg[1] instanceof ApiResponseModal.IsBroadcasting_result){
                            ApiResponseModal.IsBroadcasting_result result = (ApiResponseModal.IsBroadcasting_result) msg[1];
                            startsPreviousStream(result.getTitle(), result.getId());
                        }

                    }else {
                        startnewBroadcast();
                    }
                }
            }

        }).is_broadcasting(uid);

    }


    void startnewBroadcast(){
        if (config.appIdObtained()) {
            Intent intent = new Intent(context, LivePrepareActivity.class);
            intent.putExtra(Global.Constants.TAB_KEY, 3);
            intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, true);
            intent.putExtra(Global.Constants.KEY_CREATE_ROOM, true);
            intent.putExtra(Global.Constants.KEY_ROOM_OWNER_ID, config.getUserProfile().getUserId());

            intent.putExtra(Global.Constants.BROADCASTERID, IndifunApplication.getInstance().getCredential().getId());
            intent.putExtra(Global.Constants.USER_PROFILE, IndifunApplication.getInstance().getCredential().getProfilePicture());

            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.agora_app_id_failed, Toast.LENGTH_SHORT).show();
        }
    }

    public void startsPreviousStream(String roomName, String broadcastid){
        if (config.appIdObtained()) {
            Intent intent = new Intent(context, HostPKLiveActivity.class);

            intent.putExtra(Global.Constants.TAB_KEY, 3);
            intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, true);
            intent.putExtra(Global.Constants.KEY_CREATE_ROOM, true);
            intent.putExtra(Global.Constants.KEY_ROOM_NAME, roomName);

            intent.putExtra(Global.Constants.KEY_ROOM_OWNER_ID, getUserId());
            intent.putExtra(Global.Constants.BROADCASTERID, getUserId());
            intent.putExtra(Global.Constants.BROADCAST_ID, broadcastid);

            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.agora_app_id_failed, Toast.LENGTH_SHORT).show();
        }
    }

    void goLiveRoom(RoomInfo info) {
        if (config.appIdObtained()) {
            Intent intent = new Intent(context, HostPKLiveActivity.class);
            intent.putExtra(Global.Constants.TAB_KEY, 3);
            intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, false);
            intent.putExtra(Global.Constants.KEY_ROOM_NAME, info.roomName);
            intent.putExtra(Global.Constants.KEY_ROOM_ID, info.roomId);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.agora_app_id_failed, Toast.LENGTH_SHORT).show();
            //checkUpdate();
        }
    }

    /*join broadcast*/
    /*previously started stream will be resumed*/
    public static void joinSingleLiveActivity(Context cnx, LiveResult item){


        loge("ACTIVITYUTILS", toGson(item) );

        Intent intent = new Intent(cnx, HostPKLiveActivity.class);

        intent.putExtra(Global.Constants.TAB_KEY, 3);
        intent.putExtra(Global.Constants.KEY_IS_ROOM_OWNER, false);
        intent.putExtra(Global.Constants.KEY_CREATE_ROOM, false);

        intent.putExtra(Global.Constants.KEY_ROOM_NAME, item.getAdd_broadcast_title());
        intent.putExtra(Global.Constants.KEY_ROOM_ID, item.getRoom_id());

        intent.putExtra(Global.Constants.KEY_ROOM_OWNER_ID, item.getUser_id());
        intent.putExtra(Global.Constants.USER_PROFILE, item.getImage());
        intent.putExtra(Global.Constants.USERNAME, item.getUser_name());
        intent.putExtra(Global.Constants.TAB_KEY, Integer.parseInt(item.getBroadcasting_type()));
        intent.putExtra(Global.Constants.BROADCAST_ID, item.getAdd_broadcast_id());
        intent.putExtra(Global.Constants.BROADCASTERID, item.getUser_id());

        intent.putExtra(Global.Constants.USER_GENDER, item.getGender());
        intent.putExtra(Global.Constants.USER_AGE, item.getAge());

        putUserLevel(intent, item.getDiamond(), item.getLevel(), item.getHeart(), item.getMy_xp());

        intent.putExtra(Global.Constants.COUNTDOWN, item.getCount_down_in_sec());

        cnx.startActivity(intent);
    }

    static void putUserLevel(Intent intent, String diam, String level, String heart, String xp){
        intent.putExtra(Global.Constants.USER_DIAMOND, diam);
        intent.putExtra(Global.Constants.USER_LEVEL, level);
        intent.putExtra(Global.Constants.USER_HEART, heart);
        intent.putExtra(Global.Constants.USER_XP, xp);
    }

    public static void updateRoomId(String broadcasteid, String room_id, int broadcastetype){
        ApiCall1.instance().client().broadcasting_room_id(broadcasteid, room_id, broadcastetype);
    }


    public static void endSession(String userid, String bid){
        ApiCall1.instance().client().UPDATE_BROADCASTING(userid, bid);
    }

}
