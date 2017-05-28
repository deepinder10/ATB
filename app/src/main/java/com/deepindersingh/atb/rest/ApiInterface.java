package com.deepindersingh.atb.rest;

import com.deepindersingh.atb.model.Requests;
import com.deepindersingh.atb.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by deepindersingh on 06/05/17.
 */

public interface ApiInterface {
    @POST("api/requests")
    Call<User> getRequestList(@Query("token") String token);

    @POST("authenticate")
    @FormUrlEncoded
    Call<User> login(@Field("email") String email,
                        @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<User> register(@Field("email") String email, @Field("password") String password,
                        @Field("donorName") String donorName,@Field("gender") String gender,
                        @Field("age") String age,@Field("mobile") String phone,
                        @Field("blood_group") int b_id,@Field("state") int state_id,
                        @Field("city") int city_id,@Field("district") int district_id);

    @POST("requestCreate")
        @FormUrlEncoded
        Call<User> requestCreate(@Field("email") String email, @Field("hospital") String hospital,
                            @Field("donorName") String donorName,@Field("gender") String gender,
                            @Field("age") String age,@Field("mobile") String phone,
                            @Field("blood_group") String blood_group);


}
