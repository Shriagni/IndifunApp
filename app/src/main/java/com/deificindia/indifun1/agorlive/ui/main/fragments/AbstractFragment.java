package com.deificindia.indifun1.agorlive.ui.main.fragments;

import androidx.fragment.app.Fragment;

import com.deificindia.indifun1.Activity.HomeActivity;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.agorlive.Config;
import com.deificindia.indifun1.agorlive.proxy.ClientProxyListener;
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
import com.deificindia.indifun1.agorlive.proxy.model.response.ModifyUserStateResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.MusicListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.OssPolicyResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.ProductListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.RefreshTokenResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.RoomListResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.SeatStateResponse;
import com.deificindia.indifun1.agorlive.proxy.model.response.SendGiftResponse;


public abstract class AbstractFragment extends Fragment implements ClientProxyListener {

    protected IndifunApplication application() {
        return (IndifunApplication) getContext().getApplicationContext();
    }

    HomeActivity getContainer() {
        return (HomeActivity) getActivity();
    }

    protected Config config() {
        return application().config();
    }

    @Override
    public void onAppVersionResponse(AppVersionResponse response) {

    }

    @Override
    public void onRefreshTokenResponse(RefreshTokenResponse refreshTokenResponse) {

    }

    @Override
    public void onOssPolicyResponse(OssPolicyResponse response) {

    }

    @Override
    public void onMusicLisResponse(MusicListResponse response) {

    }

    @Override
    public void onGiftListResponse(GiftListResponse response) {

    }

    @Override
    public void onCreateUserResponse(CreateUserResponse response) {

    }

    @Override
    public void onLoginResponse(LoginResponse response) {

    }

    @Override
    public void onEditUserResponse(EditUserResponse response) {

    }

    @Override
    public void onCreateRoomResponse(CreateRoomResponse response) {

    }

    @Override
    public void onEnterRoomResponse(EnterRoomResponse response) {

    }

    @Override
    public void onLeaveRoomResponse(LeaveRoomResponse response) {

    }

    @Override
    public void onAudienceListResponse(AudienceListResponse response) {

    }

    @Override
    public void onRequestSeatStateResponse(SeatStateResponse response) {

    }

    @Override
    public void onModifyUserStateResponse(ModifyUserStateResponse response) {

    }



    @Override
    public void onGiftRankResponse(GiftRankResponse response) {

    }

   /* @Override
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
*/
    @Override
    public void onResponseError(int requestType, int error, String message) {

    }


    @Override
    public void onRoomListResponse(RoomListResponse response) {

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

}