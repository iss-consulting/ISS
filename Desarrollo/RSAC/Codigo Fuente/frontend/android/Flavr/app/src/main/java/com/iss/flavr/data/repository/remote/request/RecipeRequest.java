package com.iss.flavr.data.repository.remote.request;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luciano on 08/06/2018.
 */

public interface RecipeRequest {


        //getBettingSites
        @GET("/recipe/recipe-api/")
        Call<JSONObject> getBettingSites();

}
