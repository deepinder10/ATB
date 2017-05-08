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
    Call<Requests> getRequestList(@Query("token") String token);

    @GET("movie/{id}")
    Call<Requests> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @POST("authenticate")
    @FormUrlEncoded
    Call<User> login(@Field("email") String email,
                        @Field("password") String password);
}
