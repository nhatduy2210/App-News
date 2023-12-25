package com.example.androidnetworking.helpers;

import com.example.androidnetworking.models.ForgotPassRequest;
import com.example.androidnetworking.models.ForgotPassResponse;
import com.example.androidnetworking.models.NewsModelDetailResponse;
import com.example.androidnetworking.models.NewsModelResponse;
import com.example.androidnetworking.models.TopicModelResponse;
import com.example.androidnetworking.models.UserDetailResponse;
import com.example.androidnetworking.models.UserLoginRequest;
import com.example.androidnetworking.models.UserLoginResponse;
import com.example.androidnetworking.models.UserRegisterRequest;
import com.example.androidnetworking.models.UserRegisterResponse;
import com.example.androidnetworking.models.VideoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitRouter {
    //
    @POST("/login.php")
    Call<UserLoginResponse> login(@Body UserLoginRequest user);

    @POST("/register-user.php")
    Call<UserRegisterResponse> register(@Body UserRegisterRequest user);

    //trả về danh sách , k có body
    @GET("/get-news.php")
    Call<List<NewsModelResponse>> getNew();

    @GET("/get-topic.php")
    Call<List<TopicModelResponse>> getTopic();

    // Thêm tham số newsId vào đây
    @GET("/get-news-detail.php")
    Call<NewsModelDetailResponse> getNewDetail(@Query("id") int newsId);

    // Thêm tham số newsId vào đây
    @GET("/get-news-by-user.php")
    Call<List<NewsModelResponse>> getNewByUser(@Query("user_id") int userId);


    //sEARCH
    @GET("/get-news-by-keyword.php")
    Call<List<NewsModelResponse>> searchNews(@Query("keyWord") String keyWord);

    @GET("/get-user-detail.php")
    Call<UserDetailResponse> getUserDetail(@Query("id") int userId);


    //quên mật khẩu
    @POST("/forgot-password.php")
    Call<ForgotPassResponse> forgot_pass(@Body ForgotPassRequest email);

    @GET("/get-video-detail.php")
    Call<VideoResponse> getVideoDetail(@Query("id") int viId);
}
