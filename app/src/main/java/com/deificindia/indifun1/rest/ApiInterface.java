package com.deificindia.indifun1.rest;

import com.deificindia.indifun1.Model.StreamDetails;
import com.deificindia.indifun1.Model.login.LoginResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/streamdetail")
    Call<StreamDetails> streamdetail(@FieldMap Map<String, String> params);

    /********************************/

    @POST("login")
    Observable<LoginResult> login(@Query("contact") String mobile);

    @POST("verify_otp")
    Observable<ResponseBody> verify_otp(@Query("user_id") String str_uid, @Query("otp") String verify_otp);

    @POST("resend_otp")
    Observable<ResponseBody> resend_otp(@Query("contact") String contact);


}

