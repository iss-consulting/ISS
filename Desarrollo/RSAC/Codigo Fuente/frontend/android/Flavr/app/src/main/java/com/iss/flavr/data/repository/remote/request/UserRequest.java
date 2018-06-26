package com.iss.flavr.data.repository.remote.request;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Luciano on 08/06/2018.
 */

public interface UserRequest {

    //Login
    @FormUrlEncoded
    @POST("login-api/")
    Call<JsonObject> login(@Field("username") String email,
                           @Field("password") String password);

}
