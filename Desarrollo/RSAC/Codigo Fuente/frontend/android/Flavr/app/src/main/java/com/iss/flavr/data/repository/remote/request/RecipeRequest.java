package com.iss.flavr.data.repository.remote.request;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luciano on 08/06/2018.
 */

public interface RecipeRequest {

    //getRecipes
    @GET("/recipe/recipe-api/")
    Call<JsonArray> getRecipes();

}
