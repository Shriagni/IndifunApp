package com.deificindia.indifun1.rest;

import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.Model.CountryUsers;
import com.deificindia.indifun1.Model.GetFollow_Result;
import com.deificindia.indifun1.Model.Hotpostmodel;
import com.deificindia.indifun1.Model.LikeModel_Response;
import com.deificindia.indifun1.Model.Recommendadmodel;
import com.deificindia.indifun1.Model.Recommended;
import com.deificindia.indifun1.Model.Userlevel_Response;
import com.deificindia.indifun1.Model.agoramodel.GenerateToken;
import com.deificindia.indifun1.Model.agoramodel.RequestToken;
import com.deificindia.indifun1.Model.login.LoginResult;
import com.deificindia.indifun1.Model.retro.AddProfilePhotos;
import com.deificindia.indifun1.Model.retro.ChatModel_Response;
import com.deificindia.indifun1.Model.retro.Commentmodel_Response;
import com.deificindia.indifun1.Model.retro.GetPost;
import com.deificindia.indifun1.Model.retro.GetProfilePhotos;
import com.deificindia.indifun1.Model.retro.LanguageList;
import com.deificindia.indifun1.Model.retro.NewstarModal;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.Model.retro.TrendingModal;
import com.deificindia.indifun1.Model.retro.UserFollower;
import com.deificindia.indifun1.Model.retro.UserFollowing;
import com.deificindia.indifun1.Model.retro.UserFriends;
import com.deificindia.indifun1.Model.retro.UserGroupList;
import com.deificindia.indifun1.Model.retro.UserInterests;
import com.deificindia.indifun1.Model.retro.UserProfile;
import com.deificindia.indifun1.Model.retro.UserProfileUpdate;
import com.deificindia.indifun1.agoraapis.api.mod.GiftListResponse2;
import com.deificindia.indifun1.agorlive.proxy.model.response.GiftListResponse;
import com.deificindia.indifun1.bindingmodals.aristocracycenterprivilage.Aristocracy;
import com.deificindia.indifun1.bindingmodals.login.Login;
import com.deificindia.indifun1.bindingmodals.moment.MomentImage;
import com.deificindia.indifun1.bindingmodals.otheruserprofile.OtherUserProfile;
import com.deificindia.indifun1.bindingmodals.userlevel.UserLevel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoadInterface {

    @POST("login")
    Call<Login> login(@Query("contact") String mobile);

    @POST("verify_otp")
    Call<ResponseBody> verify_otp(@Query("user_id") String str_uid, @Query("otp") String verify_otp);


    @POST("signup")
    Call<ResponseBody> sign_up(@Query("contact") String number,
                               @Query("full_name") String name,
                               @Query("email") String email,
                               @Query("dob") String birthday,
                               @Query("gender") String gender,@Query("age")String age);

    @POST("follow_homepage")
    Call<GetFollow_Result> follow_homepage(@Query("user_id") String username);

    @POST("follow_homepage_recommended")
    Call<GetFollow_Result> follow_homepage_recommended(@Query("user_id") String username);

    @POST("hot_hompage")
    Call<GetFollow_Result> hot_homepage(@Query("user_id") String username);

    @POST("following_homepage")
    Call<ResponseBody> following_homepage(@Query("user_id") String user_id);

    ///Post fragment =>
    @GET("get_post")
    Call<Hotpostmodel> get_post1(@Query("user_id") String user_id);

    @POST("get_hotpost")
    Call<Hotpostmodel> get_hotpost(@Query("user_id") String user_id);

    @POST("recommended_list")
    Call<Recommended> getRecommendedPoast(@Query("filter_by_gender") String filter_by_gender,
                                          @Query("filter_by_age_from") String filter_by_age_from,
                                          @Query("filter_by_age_to") String filter_by_age_to);

    /*---login process---*/


    @GET("country")
    Call<CountryNamesResult> getCountry();

    @POST("user_by_country")
    Call<CountryUsers> getCountryUsers(@Query("country_id") String countryid);

    //201022
    @POST("add_post")
    Call<PostModal> add_post(
            @Query("post_by") String post_by,
            @Query("content") String content,
            @Field("image[]") List<String> image
    );


    //201023//////
    @POST("follow_add") //1
    Call<PostModal> live_follow_click(@Query("user_id") String user_id, @Query("following_id") String following_id);

    @POST("follow_post") //2 || like comment
    Call<PostModal> follow_post(@Query("user_id") String user_id,
                                @Query("post_id") String post_id,
                                @Query("is_like") int is_like,
                                @Query("comments") String comments
    );

    /*shamshad api*/
    @POST("notification_likes") //3
    Call<LikeModel_Response> getByUserid(@Query("user_id") String user_id);

    @POST("notification_comments") //3
    Call<Commentmodel_Response> getComments(@Query("user_id") String user_id);


    /*201024*/
    ///user profile APIs////
    @POST("user_profile") //1
    Call<UserProfile> user_profile(@Query("user_id") String user_id);

    @POST("other_user_profile")
    Call<OtherUserProfile> other_user_profile(@Query("user_id") String user_id,
                                              @Query("other_user_id") String other_user_id);

    @POST("get_profile_photos") //4 || like comment
    Call<GetProfilePhotos> get_profile_photos(@Query("user_id") String user_id);


    @POST("add_profile_photos") //5 || like comment
    Call<AddProfilePhotos> add_profile_photos(@Query("user_id") String user_id, @Query("image") String image);

    @POST("user_profile_update") //6 || like comment
    Call<UserProfileUpdate> user_profile_update(
            @Query("user_id") String user_id,
            @Query("full_name") String full_name,
            @Query("gender") String gender,
            @Query("whatsup") String whatsup,
            @Query("relationship") String relationship,
            @Query("country") String country,
            @Query("state") String state,
            @Query("city") String city
    );

    @POST("user_interests") //7 || like comment
    Call<UserInterests> user_interests(@Query("user_id") String user_id);

    @POST("get_user_level") //7 || like comment
    Call<UserLevel> get_user_level(@Query("user_id") String user_id);

    @POST("moment_image") //7 || like comment
    Call<MomentImage> moment_image(@Query("user_id") String user_id);

    @POST("language_list") //8 || like comment
    Call<LanguageList> language_list(@Query("user_id") String user_id);

    @POST("get_post") //9 || like comment
    Call<GetPost> get_post(@Query("user_id") String user_id);

    /*201026 anuj*/
    @POST("user_friends") //11 || like user_friends
    Call<UserFriends> user_friends(@Query("user_id") String user_id);
    @POST("user_following") //11 || like user_following
    Call<UserFollowing> user_following(@Query("user_id") String user_id);
    @POST("user_follower") //10 || like user_follower
    Call<UserFollower> user_follower(@Query("user_id") String user_id);
    @POST("user_groups_list") //11 || like user_groups_list
    Call<UserGroupList> user_groups_list(@Query("user_id") String user_id);

    /*201026 @spario*/
    @POST("newstar_india") //9 || like commenttrending
    Call<NewstarModal> newstar_india();

    @POST("trending") //9 || like commenttrending
    Call<TrendingModal> trending();

    @POST("user_chatbox")
    Call<ChatModel_Response> getChats(@Query("user_id") String id);

    @POST("joiner_name")
    Call<ResponseBody> joiner_name(@Query("user_id") String uisrid);

    @GET("get_gifts")//get gift list
    Call<GiftListResponse2> getGiftList(@Query("gift_category") int gift_category);

    @GET("gift_point")
    Call<GiftListResponse> gift_point(
            @Query("to") String to,
            @Query("from") String from,
            @Query("gift_id") String gift_id,
            @Query("broadcast_id") String broadcast_id);

    @POST("broadcast_between")
    Call<ResponseBody> broadcast_between(@Query("add_broadcast_1") String add_broadcast_1, @Query("add_broadcast_2") String add_broadcast_2);

    @POST("get_user_level")
    Call<Userlevel_Response> getUserLevel(@Query("user_id") String user_id);

    @POST("generate_token")
    Call<GenerateToken> generate_token(@Query("room_id") String room_id,
                                       @Query("channelName") String channelName,
                                       @Query("user_id") String user_id);

    @POST("get_channel")
    Call<RequestToken> request_token(@Query("room_id") String room_id,
                                     @Query("user_id") int user_id);

    @POST("generate_token_rtm")
    Call<ResponseBody> generate_rtc_token(@Query("user_id") String user_id);

    @POST("aristocracy_center_privileges")
    Call<Aristocracy> aristocracy_center_privileges(@Query("aristocracy_center_id") int aristocracy_center_id);

}
