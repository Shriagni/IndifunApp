package com.deificindia.indifun1.agorlive.ui.live;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.agoraapis.animui.AnimUtils;
import com.deificindia.indifun1.agoraapis.animui.GiftAnimView;
import com.deificindia.indifun1.agoraapis.animui.GiftHeartAnimView;
import com.deificindia.indifun1.agoraapis.animui.RoomEnterAnima;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agoraapis.api.mod.GiftMessage2;
import com.deificindia.indifun1.agoraapis.api.mod.HeartGiftInfo;
import com.deificindia.indifun1.agoraapis.maths.AgoraUiUtils;
import com.deificindia.indifun1.agoraapis.uis.ItemLayout;
import com.deificindia.indifun1.agorlive.agora.rtm.model.GiftMessage;
import com.deificindia.indifun1.agorlive.proxy.model.model.GiftInfo;
import com.deificindia.indifun1.agorlive.proxy.model.response.ProductListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.SendGiftResponse;
import com.deificindia.indifun1.dialogs.ProfilePreviewBottomSheetDialog;
import com.deificindia.indifun1.ui.tag.ChipView;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;

import io.agora.rtc.Constants;
import io.agora.rtc.video.ChannelMediaInfo;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import com.deificindia.indifun1.agorlive.Config;
import com.deificindia.indifun1.agorlive.agora.rtm.model.PKStateMessage;
import com.deificindia.indifun1.agorlive.proxy.ClientProxy;
import com.deificindia.indifun1.agorlive.proxy.manager.PKServiceManager;
import com.deificindia.indifun1.agorlive.proxy.model.model.RoomInfo;
import com.deificindia.indifun1.agorlive.proxy.model.model.SeatInfo;
import com.deificindia.indifun1.agorlive.proxy.model.request.Request;
import com.deificindia.indifun1.agorlive.proxy.model.request.RoomRequest;
import com.deificindia.indifun1.agorlive.proxy.model.response.EnterRoomResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.Response;
import com.deificindia.indifun1.agorlive.proxy.model.response.RoomListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.types.PKConstant;
import com.deificindia.indifun1.agorlive.ui.actionsheets.toolactionsheet.LiveRoomToolActionSheet;
import com.deificindia.indifun1.agorlive.ui.actionsheets.PkRoomListActionSheet;
import com.deificindia.indifun1.agorlive.ui.components.CameraTextureView;
import com.deificindia.indifun1.agorlive.ui.components.bottomLayout.LiveBottomButtonLayout;
import com.deificindia.indifun1.agorlive.ui.components.LiveHostNameLayout;
import com.deificindia.indifun1.agorlive.ui.components.LiveMessageEditLayout;
import com.deificindia.indifun1.agorlive.ui.components.PkLayout;
import com.deificindia.indifun1.agorlive.utils.UserUtil;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.logpk;
import static com.deificindia.indifun1.Utility.Logger.toGson;
import static com.deificindia.indifun1.Utility.URLs.GifgBaseUrl;
import static com.deificindia.indifun1.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun1.anim.TranslateView.getScreenWidth;

public class HostPKLiveActivity extends LiveRoomActivity
        implements View.OnClickListener, PkRoomListActionSheet.OnPkRoomSelectedListener, LiveHostNameLayout.OnAddClickListener {
    private static final String TAG = HostPKLiveActivity.class.getSimpleName();

    private static final int PK_RESULT_DISPLAY_LAST = 2000;

    private RelativeLayout mLayout;
    private FrameLayout mVideoNormalLayout;
    private LiveHostNameLayout mNamePad;
    private PkRoomListActionSheet mPkRoomListActionSheet;
    private AppCompatImageView mStartPkButton;
    private PkLayout mPkLayout;
    private boolean mTopLayoutCalculated;

    private String mPKRoomId;
    private String mPKRoomUserName;
    private boolean mPkStarted;
    private boolean mBroadcastStarted;

    private Map<String, ChipView> chipMap = new HashMap<>();

    private PKServiceManager mPKManager;

    ////////////////////////////////
    private LinearLayout itemLayout,
            layout_room_enter_animation,
            layout_gift_animation,
            layout_heart_animation_area,
            layout_gift_2_animation;

    private LottieAnimationView lottieAnimationView;

    // When the owner returns to his room and the room
    // is in pk mode before he left, the owner needs to
    // start pk mode. But he also needs to join rtc channel
    // first. This pending request records the case.
    private boolean mPendingStartPkRequest;

    private EnterRoomResponse.RelayConfig mPendingPkConfig;

    private int mMessageListHeightInNormalMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(false);
    }

    @Override
    protected void onPermissionGranted() {
        logpk(TAG, "onPermissionGranted");
        mPKManager = new PKServiceManager(application());
        initUI();
        super.onPermissionGranted();
    }

    private void initUI() {
        mMessageListHeightInNormalMode = getResources().
                getDimensionPixelOffset(R.dimen.live_message_list_height);

        setContentView(R.layout.activity_pk_host_in);

        mLayout = findViewById(R.id.live_room_pk_room_layout);
        mVideoNormalLayout = findViewById(R.id.live_pk_video_normal_layout);
        mNamePad = findViewById(R.id.pk_host_in_name_pad);
        mNamePad.init();
        mNamePad.setListener(this);

        participants = findViewById(R.id.pk_host_in_participant);
        participants.init();
        participants.setUserLayoutListener(this);

        itemLayout = findViewById(R.id.itemLayouts);
        layout_room_enter_animation = findViewById(R.id.layout_room_enter_animation);
        layout_gift_animation = findViewById(R.id.layout_gift_animation);
        layout_heart_animation_area = findViewById(R.id.layout_heart_animation_area);

        messageList = findViewById(R.id.message_list);
        messageList.init();

        bottomButtons = findViewById(R.id.pk_host_in_bottom_layout);
        bottomButtons.init();
        bottomButtons.setLiveBottomButtonListener(this);
        bottomButtons.setRole(isOwner ? LiveBottomButtonLayout.ROLE_OWNER :
                isHost ? LiveBottomButtonLayout.ROLE_HOST :
                        LiveBottomButtonLayout.ROLE_AUDIENCE);

        findViewById(R.id.live_bottom_btn_close).setOnClickListener(this);
        findViewById(R.id.live_bottom_btn_more).setOnClickListener(this);
        findViewById(R.id.live_bottom_btn_fun1).setOnClickListener(this);
        findViewById(R.id.live_bottom_btn_fun2).setOnClickListener(this);

        mStartPkButton = findViewById(R.id.start_pk_button);
        mStartPkButton.setOnClickListener(this);

        messageEditLayout = findViewById(R.id.message_edit_layout);
        messageEditText = messageEditLayout.findViewById(LiveMessageEditLayout.EDIT_TEXT_ID);

        mPkLayout = findViewById(R.id.pk_host_layout);
        layout_gift_2_animation = mPkLayout.findViewById(R.id.layout_gift_2_animation);
        // At the initialization phase, the room is considered to
        // be in single-broadcast mode.
        // Whether the room is already in PK mode or not depends
        // on the information returned in the "enter room" response.
        setupUIMode(false, isOwner);
        setupSingleBroadcastBehavior(isOwner, !isOwner, !isOwner);

        // If I am the room owner, I will start single broadcasting
        // right now and do not need to start in "enter room" response
        if (isOwner) mBroadcastStarted = true;

        rtcStatsView = findViewById(R.id.host_pk_rtc_stats);
        rtcStatsView.setCloseListener(view -> rtcStatsView.setVisibility(View.GONE));
    }

    @Override
    protected void onGlobalLayoutCompleted() {
        View topLayout = findViewById(R.id.pk_host_in_top_participant_layout);
        if (topLayout != null && !mTopLayoutCalculated) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) topLayout.getLayoutParams();
            params.topMargin += systemBarHeight;
            topLayout.setLayoutParams(params);
            mTopLayoutCalculated = true;
        }
    }

    @Override
    public void onEnterRoomResponse(EnterRoomResponse response) {
        loge(TAG, "onEnterRoomResponse", toGson(response));
        if (response.code == Response.SUCCESS) {
            Config.UserProfile profile = config().getUserProfile();
            profile.setRtcToken(response.data.user.rtcToken);
            profile.setAgoraUid(response.data.user.uid);

            rtcChannelName = response.data.room.channelName;
            roomId = response.data.room.roomId;
            roomName = response.data.room.roomName;

            ownerId = response.data.room.owner.userId;
            ownerRtcUid = response.data.room.owner.uid;

            // Determine if I am the owner of a host here because
            // I may leave the room unexpectedly and come once more.
            String myId = config().getUserProfile().getUserId();
            if (!isOwner && myId.equals(response.data.room.owner.userId)) {
                isOwner = true;
            }

            // Result from server if the channel is in PK mode
            mPkStarted = response.data.room.pk.state == PKConstant.PK_STATE_PK;
            if (mPkStarted) mPKRoomId = response.data.room.pk.remoteRoom.roomId;

            runOnUiThread(() -> {
                mNamePad.setName(response.data.room.owner.userName, broadcaster_id, isOwner);

                if(user_profile_image==null){
                    mNamePad.setIcon(UserUtil.getUserRoundIcon(getResources(), response.data.room.owner.userId));
                }else {
                    mNamePad.setAvtarByLink(user_profile_image);
                }

                participants.reset(response.data.room.currentUsers,
                        response.data.room.rankUsers);

                setLevelTags();
               /* if(isOwner){
                   itemLayout.callapi(broadcaster_id);
                }else {
                    itemLayout.setData(user_level, user_diamond, user_heart, user_xp);
                }*/

                if (!mPkStarted) {
                    boolean audioMuted = config().isAudioMuted();
                    boolean videoMuted = config().isVideoMuted();

                    if (isOwner && !mBroadcastStarted) {
                        // I created this room and I left this room unexpectedly
                        // not long ago.
                        // This time I came from room list as an audience at first,
                        // but from the server response, I know that this is my room.
                        // I can start my broadcasting right now if not muted.
                        audioMuted = response.data.room.owner.enableAudio !=
                                SeatInfo.User.USER_AUDIO_ENABLE;
                        videoMuted = response.data.room.owner.enableVideo !=
                                SeatInfo.User.USER_VIDEO_ENABLE;
                    }

                    setupUIMode(false, isOwner);
                    setupSingleBroadcastBehavior(isOwner, audioMuted, videoMuted);
                    mBroadcastStarted = true;
                } else {
                    mBroadcastStarted = false;
                    mPendingStartPkRequest = true;
                    mPendingPkConfig = response.data.room.pk.relayConfig;
                    setupUIMode(true, isOwner);
                    setupPkBehavior(isOwner, response.data.room.pk.countDown,
                            response.data.room.pk.remoteRoom.owner.userName,
                            response.data.room.pk.relayConfig);
                    updatePkGiftRank(response.data.room.pk.localRank,
                            response.data.room.pk.remoteRank);
                }

                joinRtcChannel();
                joinRtmChannel();
            });
        }
    }



    @Override
    public void onSendGiftResponse(SendGiftResponse response) {

    }

    @Override
    public void onGetProductListResponse(ProductListResponse response) {

    }

    @Override
    public void onProductStateChangedResponse(String productId, int state, boolean success) {

    }

    @Override
    public void onProductPurchasedResponse(boolean success) {

    }

    @Override
    public void onSeatInteractionResponse(long processId, String userId, int seatNo, int type) {

    }

    private void setupUIMode(boolean isPkMode, boolean isOwner) {
        if (isPkMode) {
            mLayout.setBackgroundResource(R.drawable.dark_background);
            mStartPkButton.setVisibility(View.GONE);
            mVideoNormalLayout.setVisibility(View.GONE);
            mPkLayout.removeResult();
            mPkLayout.setVisibility(View.VISIBLE);
            mPkLayout.setHost(isOwner);
        } else {
            mLayout.setBackground(null);
            mStartPkButton.setVisibility(isOwner ? View.VISIBLE : View.GONE);
            mPkLayout.removeResult();
            mPkLayout.getLeftVideoLayout().removeAllViews();
            mPkLayout.getRightVideoLayout().removeAllViews();
            mPkLayout.setVisibility(View.GONE);
            mVideoNormalLayout.setVisibility(View.VISIBLE);
        }

        setupMessageListLayout(isPkMode);
        bottomButtons.setRole(isOwner ? LiveBottomButtonLayout.ROLE_OWNER
                : LiveBottomButtonLayout.ROLE_AUDIENCE);
        bottomButtons.setBeautyEnabled(config().isBeautyEnabled());
    }

    /**
     * Must be called after the desirable UI mode is already set up
     */
    private void setupPkBehavior(boolean isOwner, long remaining,
                                 String remoteName, EnterRoomResponse.RelayConfig config) {

        Log.e("HotPk", "gson "+ remoteName);
        Log.e("HotPk", "gson "+ new Gson().toJson(config));
        myRtcRole = isOwner ? Constants.CLIENT_ROLE_BROADCASTER : Constants.CLIENT_ROLE_AUDIENCE;
        rtcEngine().setClientRole(myRtcRole);

        mPkLayout.setHost(isOwner);
        mPkLayout.setPKHostName(remoteName);
        mPkLayout.startCountDownTimer(remaining);
        if (!isOwner) {
            mPkLayout.setOnClickGotoPeerChannelListener(view -> enterAnotherPkRoom(mPKRoomId));
        }

        if (isOwner) {
            startCameraCapture();
            CameraTextureView cameraTextureView = new CameraTextureView(this);
            mPkLayout.getLeftVideoLayout().removeAllViews();
            mPkLayout.getLeftVideoLayout().addView(cameraTextureView);
            SurfaceView remoteSurfaceView = setupRemoteVideo(config.remote.uid);
            mPkLayout.getRightVideoLayout().removeAllViews();
            mPkLayout.getRightVideoLayout().addView(remoteSurfaceView);
            rtcEngine().muteLocalAudioStream(false);
            rtcEngine().muteLocalVideoStream(false);
            config().setAudioMuted(false);
            config().setVideoMuted(false);
        } else {
            SurfaceView surfaceView = setupRemoteVideo(ownerRtcUid);
            mPkLayout.getLeftVideoLayout().removeAllViews();
            mPkLayout.getLeftVideoLayout().addView(surfaceView);
            surfaceView.setZOrderMediaOverlay(true);
            SurfaceView remoteSurfaceView = setupRemoteVideo(config.remote.uid);
            mPkLayout.getRightVideoLayout().removeAllViews();
            mPkLayout.getRightVideoLayout().addView(remoteSurfaceView);
            remoteSurfaceView.setZOrderMediaOverlay(true);
        }
    }

    /**
     * Must be called after the desirable UI mode is already set up
     */
    private void setupSingleBroadcastBehavior(boolean isOwner, boolean audioMuted, boolean videoMuted) {
        myRtcRole = isOwner ? Constants.CLIENT_ROLE_BROADCASTER
                : Constants.CLIENT_ROLE_AUDIENCE;
        rtcEngine().setClientRole(myRtcRole);

        if (isOwner) {
            startCameraCapture();
            CameraTextureView cameraTextureView = new CameraTextureView(this);
            mVideoNormalLayout.addView(cameraTextureView);
        } else {
            SurfaceView surfaceView = setupRemoteVideo(ownerRtcUid);
            mVideoNormalLayout.removeAllViews();
            mVideoNormalLayout.addView(surfaceView);
        }

        config().setAudioMuted(audioMuted);
        config().setVideoMuted(videoMuted);
        rtcEngine().muteLocalAudioStream(audioMuted);
        rtcEngine().muteLocalVideoStream(videoMuted);
        bottomButtons.setRole(isOwner ? LiveBottomButtonLayout.ROLE_OWNER : LiveBottomButtonLayout.ROLE_AUDIENCE);
    }

    private void startMediaRelay(EnterRoomResponse.RelayConfig config) {
        ChannelMediaRelayConfiguration relayConfig = new ChannelMediaRelayConfiguration();
        relayConfig.setSrcChannelInfo(toChannelMediaInfo(config.local));
        relayConfig.setDestChannelInfo(config.proxy.channelName, toChannelMediaInfo(config.proxy));
        rtcEngine().startChannelMediaRelay(relayConfig);
    }

    private ChannelMediaInfo toChannelMediaInfo(EnterRoomResponse.RelayInfo proxy) {
        return new ChannelMediaInfo(proxy.channelName, proxy.token, proxy.uid);
    }

    @Override
    public void onRtcJoinChannelSuccess(String channel, int uid, int elapsed) {
        if (isOwner && mPendingStartPkRequest && mPendingPkConfig != null) {
            startMediaRelay(mPendingPkConfig);
            mPendingStartPkRequest = false;
        }
    }

    private void setupMessageListLayout(boolean isPkMode) {
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) messageList.getLayoutParams();
        if (isPkMode) {
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            params.addRule(RelativeLayout.BELOW, R.id.pk_host_layout);
        } else {
            params.height = mMessageListHeightInNormalMode;
            params.removeRule(RelativeLayout.BELOW);
        }
        messageList.setLayoutParams(params);
    }

    private void stopPkMode(boolean isOwner) {
        rtcEngine().stopChannelMediaRelay();
        setupUIMode(false, isOwner);
        setupSingleBroadcastBehavior(isOwner,
                config().isAudioMuted(),
                config().isVideoMuted());
    }

    private void enterAnotherPkRoom(String roomId) {
        rtcEngine().leaveChannel();
        leaveRtmChannel(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {

            }
        });

        sendRequest(Request.LEAVE_ROOM, new RoomRequest(
                config().getUserProfile().getToken(), this.roomId));

        enterRoom(roomId);
    }

    private void updatePkGiftRank(int mine, int other) {
        if (mPkStarted && mPkLayout.getVisibility() == View.VISIBLE) {
            mPkLayout.setPoints(mine, other);
        }
    }

    @Override
    public void onRtcChannelMediaRelayStateChanged(int state, int code) {
        if (state == Constants.RELAY_STATE_CONNECTING) {
            XLog.d("channel media relay is connecting");
        } else if (state == Constants.RELAY_STATE_RUNNING) {
            XLog.d("channel media relay is running");
        } else if (state == Constants.RELAY_STATE_FAILURE) {
            XLog.e("channel media relay fails");
        }
    }

    @Override
    public void onRtcChannelMediaRelayEvent(int code) {

    }

    @Override
    public void onRoomListResponse(RoomListResponse response) {
        //super.onRoomListResponse(response);
        if (mPkRoomListActionSheet != null && mPkRoomListActionSheet.isShown()) {
            runOnUiThread(() -> {
                filterOutCurrentRoom(response.data);
                mPkRoomListActionSheet.appendUsers(response.data);
            });
        }
    }

    private void filterOutCurrentRoom(RoomListResponse.RoomList list) {
        RoomInfo temp = null;
        for (RoomInfo info : list.list) {
            if (roomId.equals(info.roomId)) {
                temp = info;
                break;
            }
        }

        if (temp != null) list.list.remove(temp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.live_bottom_btn_close:
                onBackPressed();
                break;
            case R.id.live_bottom_btn_more:
                if(isOwner) {
                    LiveRoomToolActionSheet toolSheet = (LiveRoomToolActionSheet) showActionSheetDialog(
                            ACTION_SHEET_TOOL, tabIdToLiveType(tabId), isOwner, true, this);
                    toolSheet.setEnableInEarMonitoring(inEarMonitorEnabled);
                }else {
                    heartanimation();
                }
                break;
            case R.id.live_bottom_btn_fun1:
                if (isOwner) {
                    showActionSheetDialog(ACTION_SHEET_BG_MUSIC, tabIdToLiveType(tabId), true, true, this);
                } else {
                    showActionSheetDialog(ACTION_SHEET_GIFT, tabIdToLiveType(tabId), false, true, this);
                }
                break;
            case R.id.live_bottom_btn_fun2:
                // this button is hidden when current user is not host.
                if (isOwner) {
                    showActionSheetDialog(ACTION_SHEET_BEAUTY, tabIdToLiveType(tabId), true, true, this);
                }
                break;
            case R.id.start_pk_button:
                if (isOwner) {
                    mPkRoomListActionSheet = (PkRoomListActionSheet) showActionSheetDialog(ACTION_SHEET_PK_ROOM_LIST, tabIdToLiveType(tabId), true, true, this);
                    mPkRoomListActionSheet.setup(proxy(), config().getUserProfile().getToken(), ClientProxy.ROOM_TYPE_PK);
                    mPkRoomListActionSheet.requestMorePkRoom();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mPkStarted) {
            String title = getString(R.string.dialog_pk_force_quit_title);
            String message = getString(R.string.dialog_pk_force_quit_message);
            message = String.format(message, mPKRoomUserName != null ? mPKRoomUserName : "");
            curDialog = showDialog(title, message,
                    R.string.dialog_positive_button,
                    R.string.dialog_negative_button,
                    v -> leaveRoom(),
                    v -> closeDialog());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        bottomButtons.clearStates(application());
    }

    @Override
    public void onPkRoomListActionSheetRoomSelected(int position, String roomId, int uid) {
        // The owner sends a request to invite another host for a PK session
        mPKRoomId = roomId;
        mPKManager.invitePK(this.roomId, mPKRoomId);
        dismissActionSheetDialog();
    }

    @Override
    public void onRtmPkReceivedFromAnotherHost(String userId, String userName, String pkRoomId) {
        // Received a pk request from another host,
        // here show a dialog to make a decision.
        String title = getResources().getString(R.string.live_room_pk_room_receive_pk_request_title);
        String messageFormat = getResources().getString(R.string.live_room_pk_room_receive_pk_request_message);
        String message = String.format(messageFormat, userName);

        runOnUiThread(() -> curDialog = showDialog(title, message,
                R.string.dialog_positive_button_accept, R.string.dialog_negative_button_refuse,
                view -> {
                    mPKManager.acceptPKInvitation(roomId, pkRoomId);
                    closeDialog();
                },
                view -> {
                    mPKManager.rejectPKInvitation(roomId, pkRoomId);
                    closeDialog();
                }));
    }

    @Override
    public void onRtmPkAcceptedByTargetHost(String userId, String userName, String pkRoomId) {
        runOnUiThread(() -> showShortToast(getResources().getString(R.string.live_room_pk_room_pk_invitation_accepted)));
    }

    @Override
    public void onRtmPkRejectedByTargetHost(String userId, String userName, String pkRoomId) {
        runOnUiThread(() -> showShortToast(getResources().getString(R.string.live_room_pk_room_pk_invitation_rejected)));
    }

    @Override
    public void onRtmReceivePKEvent(PKStateMessage.PKStateMessageBody messageData) {
        Log.e("HostPk ", "onRtmReceivePKEvent" + new Gson().toJson(messageData));
        runOnUiThread(() -> {
            if (messageData.event == PKConstant.PK_EVENT_START) {
                mPkStarted = true;
                mPKRoomId = messageData.remoteRoom.roomId;
                mPKRoomUserName = messageData.remoteRoom.owner.userName;
                setupUIMode(true, isOwner);
                setupPkBehavior(isOwner, messageData.countDown,
                        mPKRoomUserName,
                        messageData.relayConfig);
                startMediaRelay(messageData.relayConfig);
                updatePkGiftRank(messageData.localRank, messageData.remoteRank);
            } else if (messageData.event == PKConstant.PK_EVENT_RANK_CHANGED) {
                updatePkGiftRank(messageData.localRank, messageData.remoteRank);
            } else if (messageData.event == PKConstant.PK_EVENT_END) {
                mPkLayout.setResult(messageData.result);
                new Handler(getMainLooper()).postDelayed(() -> stopPkMode(isOwner), PK_RESULT_DISPLAY_LAST);
                mPkStarted = false;
                showShortToast(getResources().getString(R.string.pk_ends));
            }
        });
    }



    @Override
    public void onAddClick(String userId) {
        //showShortToast("user clicked "+umd.uid);
    }

    @Override
    public void onAvtarClick(String userId) {
        ProfilePreviewBottomSheetDialog profilePreviewBottomSheetDialog = new ProfilePreviewBottomSheetDialog();
        profilePreviewBottomSheetDialog.show(getSupportFragmentManager(), "profilePreviewBottomSheetDialog");
    }

    private void setLevelTags(){
        if(user_level!=null){
            ChipView chip1 = AgoraUiUtils.setChip(this, itemLayout, user_level,
                    AgoraUiUtils.getUriToResource(this, R.drawable.stars));
            chipMap.put("LEVEL", chip1);
        }

        if(user_diamond!=null){
            ChipView chip2 = AgoraUiUtils.setChip(this, itemLayout,
                    AgoraUiUtils.formatValue(Double.parseDouble(user_diamond)),
                    AgoraUiUtils.getUriToResource(this, R.drawable.gift_07_diamond));
            chipMap.put("DIAMOND", chip2);
        }
        if(user_heart!=null){
            ChipView chip3 = AgoraUiUtils.setChip(this, itemLayout,
                    AgoraUiUtils.formatValue(Double.parseDouble(user_heart)),
                    AgoraUiUtils.getUriToResource(this, R.drawable.ic_heart3));
            chipMap.put("HEART", chip3);
        }

        if(user_xp!=null){
            ChipView chip4 = AgoraUiUtils.setChip(this, itemLayout,
                    AgoraUiUtils.formatValue(Double.parseDouble(user_xp)),
                    AgoraUiUtils.getUriToResource(this, R.drawable.ic_star3));
            chipMap.put("XP", chip4);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////Gift////and////animations//////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @Override
    public void onRtmGiftMessage2(GiftMessage2.GiftMessageData gift) {
        //panel gift // play gift
        showFullScreenGift(lottieAnimationView, GifgBaseUrl+gift.json_animation);
        giftSendAnim(UserImageBaseUrl+gift.fromUserAvtar, gift.fromUserName, gift.fromUserLevel);
    }

    @Override
    public void onRtmGiftMessage3(HeartGiftInfo.GiftMessageData gift) {
        //heart gift
        playHeartGiftInfo(gift.fromUserAvtar, gift.fromUserName, gift.giftId);

    }
    /*gift send*/
    @Override
    protected void onSendGift(int position, GiftInfo2 info) {
        //play animation
        showFullScreenGift(lottieAnimationView, GifgBaseUrl+info.getJson_animation());
        //hit api
        Map<String, String> map = new HashMap<>();
        map.put("to", broadcaster_id);
        map.put("from", IndifunApplication.getInstance().getCredential().getId());
        map.put("giftid", String.valueOf(info.getId()));
        sendRequest(Request.SEND_GIFT, map);
        //send to target//
        GiftMessage2.GiftMessageData giftdata = new GiftMessage2.GiftMessageData();
        giftdata.fromUserId = IndifunApplication.getInstance().getCredential().getId();
        giftdata.fromUserName = IndifunApplication.getInstance().getCredential().getFullName();
        giftdata.fromUserAvtar = IndifunApplication.getInstance().getCredential().getProfilePicture();
        giftdata.fromUserLevel = "5";

        giftdata.toUserId = broadcaster_id;
        giftdata.toUserName = username;
        giftdata.giftId = info.getId();
        giftdata.gift_category = Integer.parseInt(info.getGift_category());
        giftdata.json_animation = info.getJson_animation();

        GiftMessage2 gift = new GiftMessage2(giftdata);

        getMessageManager().sendGiftMessage(new GsonBuilder().create().toJson(gift),
                new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {}

            @Override
            public void onFailure(ErrorInfo errorInfo) {}
        });
    }


    private void giftSendAnim(String userprogileimage, String username, String userlevel){
       /* GiftAnimView gav=new GiftAnimView(this);
        gav.setData(userprogileimage, username, userlevel, "gave gift", R.drawable.bg_red);
        giftAnim.generateView(gav);
        giftAnim.animateMe(gav,true);*/

        GiftAnimView gav=new GiftAnimView(this);
        gav.setData(userprogileimage, username, userlevel, "gave gift", R.drawable.bg_red);

        layout_gift_animation.addView(gav);
        AnimUtils.topGiftAnimView(this, layout_gift_animation, gav);
    }

    //send HeartGift//heart clicked
    private void heartanimation() {
        ImageView img = heartImage();
        layout_heart_animation_area.addView(img);
        AnimUtils.animateMe(layout_heart_animation_area, img);
        info_other_user_i_send_heart();
    }

    public final ImageView heartImage() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.heart_on);
        return imageView;
    }

    void info_other_user_i_send_heart(){
        HeartGiftInfo.GiftMessageData giftdata = new HeartGiftInfo.GiftMessageData();
        giftdata.fromUserId = IndifunApplication.getInstance().getCredential().getId();
        giftdata.fromUserName = IndifunApplication.getInstance().getCredential().getFullName();

        HeartGiftInfo gift = new HeartGiftInfo(giftdata);

        getMessageManager().sendGiftMessage(new GsonBuilder().create().toJson(gift),
                new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {}

            @Override
            public void onFailure(ErrorInfo errorInfo) {}
        });
    }

    public void playHeartGiftInfo(String avtar, String username, int giftid) {
        GiftHeartAnimView animView = new GiftHeartAnimView(this);
        layout_gift_2_animation.addView(animView);
        animView.setData(UserImageBaseUrl+avtar, username, "gave gift", R.drawable.heart_on, -1);
        AnimUtils.sideGiftAnim(layout_gift_2_animation, animView);
    }

    @Override
    protected void onRtmChannelJoin(String usernam) {
        room_enter_animation(usernam);
    }

    void room_enter_animation(String usernam){
       // layout_room_enter_animation

        RoomEnterAnima roomEnterAnima = new RoomEnterAnima(this);
        layout_room_enter_animation.addView(roomEnterAnima);
        roomEnterAnima.setName(usernam);
        float screenwidth = getScreenWidth(this);
        float halfW = screenwidth/4.0f;

        AnimatorSet animatorSet = new AnimatorSet();
        // translationX to move object along x axis
        // next values are position value
        float startvalue = screenwidth+roomEnterAnima.getWidth();
        ObjectAnimator lftToRgt = ObjectAnimator
                .ofFloat(roomEnterAnima,"translationX", startvalue, halfW )
                .setDuration(1000); // to animate left to right

        animatorSet.play( lftToRgt); // manage sequence
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(()->{
                    room_enter_animation_2(roomEnterAnima);
                }, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animatorSet.start(); // play the animation
    }


    void room_enter_animation_2(RoomEnterAnima roomEnterAnima){
        float screenwidth = getScreenWidth(this);
        float halfW = screenwidth/4.0f;

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rgtToLft = ObjectAnimator
                .ofFloat(roomEnterAnima,"translationX",halfW, -roomEnterAnima.getWidth() )
                .setDuration(1000); // to animate right to left

        animatorSet.play(rgtToLft);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                layout_room_enter_animation.removeView(roomEnterAnima);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animatorSet.start(); // play the animation
    }


}
