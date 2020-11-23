package com.deificindia.indifun1.agoraapis.api;

import com.deificindia.indifun1.Model.retro.AddBroadcastingModal;
import com.deificindia.indifun1.bindingmodals.userleve.UserLevel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface LocalApis1 {

    /*if user broadcast has not finished*/
    @POST("is_broadcasting")
    Call<ResponseBody> is_broadcasting(@Query("user_id") String str_uid);

    //start a new broadcast get broadcast id AddBroadcast
    @POST("add_broadcasting")
    Call<AddBroadcastingModal> go_live_video_mode(@Query("user_id") String str_uid,
                                                  @Query("title") String title);

    /*update after broadcast started*/
    @POST("broadcasting_room_id")
    Call<ResponseBody> broadcasting_room_id(@Query("id") String broadcastid,
                                            @Query("room_id") String str_roomid,
                                            @Query("broadcasting_type") int btype
    );

    /*update on end of broadcast tell to end session*/
    @POST("update_broadcasting")
    Call<ResponseBody> update_broadcasting(
            @Query("user_id") String str_uid,
            @Query("id") String broadid);

    /*-----user info apis-----*/
    @POST("get_user_level")
    Call<UserLevel> get_user_level(@Query("user_id") String str_uid);

    //////////////////////////////////////////////////////////
    ////////////////Agora-services////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    @POST("agora_login")
    Call<UserLevel> agora_login(@Query("user_id") String str_uid);

    @POST("agora_login")
    Call<UserLevel> token(@Query("user_id") String str_uid);

    @POST("agora_login")
    Call<UserLevel> enter_room(@Query("token") String token,
                               @Query("room_id") String room_id,
                               @Query("channelName") String channelName);

    @POST("agora_login")
    Call<UserLevel> pkstate(@Query("room_id") String room_id);


}
