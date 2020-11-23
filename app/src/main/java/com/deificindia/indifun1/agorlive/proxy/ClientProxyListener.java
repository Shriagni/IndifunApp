package com.deificindia.indifun1.agorlive.proxy;

import com.deificindia.indifun1.agorlive.proxy.interfaces.RoomListResponse2;
import com.deificindia.indifun1.agorlive.proxy.interfaces.StartStopPkResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.AppVersionResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.AudienceListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.CreateRoomResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.CreateUserResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.EditUserResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.EnterRoomResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.GiftListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.GiftRankResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.LeaveRoomResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.LoginResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.ModifySeatStateResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.ModifyUserStateResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.MusicListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.OssPolicyResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.ProductListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.RefreshTokenResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.RoomListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.SeatStateResponse;

import com.deificindia.indifun1.Model.ApiResponseModal;
import com.deificindia.indifun1.Model.ModalInviteUser;
import com.deificindia.indifun1.Model.retro.AddBroadcastingModal;
import com.deificindia.indifun1.agorlive.proxy.model.response.SendGiftResponse;


import java.util.List;

public interface ClientProxyListener {
    void onAppVersionResponse(AppVersionResponse response);

    void onRefreshTokenResponse(RefreshTokenResponse refreshTokenResponse);

    void onOssPolicyResponse(OssPolicyResponse response);

    void onMusicLisResponse(MusicListResponse response);

    void onGiftListResponse(GiftListResponse response);

    void onRoomListResponse(RoomListResponse response);

    void onCreateUserResponse(CreateUserResponse response);

    void onEditUserResponse(EditUserResponse response);

    void onLoginResponse(LoginResponse response);

    void onCreateRoomResponse(CreateRoomResponse response);

    void onEnterRoomResponse(EnterRoomResponse response);

    void onLeaveRoomResponse(LeaveRoomResponse response);

    void onAudienceListResponse(AudienceListResponse response);

    void onRequestSeatStateResponse(SeatStateResponse response);

    void onModifyUserStateResponse(ModifyUserStateResponse response);

    void onSendGiftResponse(SendGiftResponse response);

    void onGiftRankResponse(GiftRankResponse response);

    void onGetProductListResponse(ProductListResponse response);

    void onProductStateChangedResponse(String productId, int state, boolean success);

    void onProductPurchasedResponse(boolean success);

    void onSeatInteractionResponse(long processId, String userId, int seatNo, int type);

    void onResponseError(int requestType, int error, String message);

}