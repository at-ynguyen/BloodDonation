package com.project.ync.blooddonation.api;

import com.project.ync.blooddonation.api.body.ChangePasswordBody;
import com.project.ync.blooddonation.api.body.FindBloodBody;
import com.project.ync.blooddonation.api.body.LoginBody;
import com.project.ync.blooddonation.api.body.RegisterBody;
import com.project.ync.blooddonation.api.body.UserBody;
import com.project.ync.blooddonation.api.response.EventResponse;
import com.project.ync.blooddonation.api.response.LoginResponse;
import com.project.ync.blooddonation.api.response.ObjectResponse;
import com.project.ync.blooddonation.api.response.RegisterResponse;
import com.project.ync.blooddonation.model.Award;
import com.project.ync.blooddonation.model.FindBlood;
import com.project.ync.blooddonation.model.History;
import com.project.ync.blooddonation.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Api Service
 *
 * @author YNC
 */
public interface ApiService {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @POST("/api/user")
    Call<RegisterResponse> register(@Body RegisterBody registerBody);

    @GET("/api/user/update/notification/{token}")
    Call<Object> putTokenNotification(@Path("token") String token, @Query("email") String email);

    @GET("/api/event/{page}")
    Call<List<EventResponse>> getListEvent(@Path("page") int page);

    @GET("/api/event/detail/{id}")
    Call<EventResponse> getListEventResponse(@Path("id") int id);

    @GET("/api/history/top")
    Call<List<Award>> getListAward();

    @Multipart
    @POST("/api/find/create")
    Call<FindBloodBody> createFindBlood(@Header(Parameter.AUTHORIZATION) String accessToken, @Part MultipartBody.Part image, @Part("body") RequestBody json);

    @POST("/api/find/create/no_image")
    Call<FindBloodBody> createFindBloodNoImage(@Header(Parameter.AUTHORIZATION) String accessToken, @Body FindBloodBody findBloodBody);

    @GET("/api/find/{page}")
    Call<List<FindBlood>> getListFindBlood(@Path("page") int page);

    @GET("/api/find/detail/{id}")
    Call<FindBlood> getListFindBloodById(@Path("id") int id);

    @GET("/api/history/user")
    Call<List<History>> getListHistory(@Header(Parameter.AUTHORIZATION) String accessToken);

    @PUT("/api/user/update")
    Call<User> updateUser(@Header(Parameter.AUTHORIZATION) String accessToken, @Body UserBody userBody);

    @GET("/api/user/info")
    Call<User> getInfoUser(@Header(Parameter.AUTHORIZATION) String accessToken);

    @POST("/api/user/profile/change-password")
    Call<ObjectResponse> changePassword(@Header(Parameter.AUTHORIZATION) String accessToken, @Body ChangePasswordBody changePasswordBody);

    @GET("/api/event/member/{id}")
    Call<Object> joinEvent(@Path("id") int id, @Query("email") String email);

    @GET("/api/event/check/{id}")
    Call<ObjectResponse> checkEvent(@Header(Parameter.AUTHORIZATION) String accessToken, @Path("id") int id);

    @GET("/api/find/user")
    Call<List<FindBlood>> getFindBloodUser(@Header(Parameter.AUTHORIZATION) String accessToken);

    @GET("/api/find/user/check/{id}")
    Call<FindBlood> check(@Header(Parameter.AUTHORIZATION) String accessToken, @Path("id") int id);

}
