package com.iss.flavr.data.repository.remote.request;

import com.google.gson.JsonArray;
import com.iss.flavr.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luciano on 08/06/2018.
 */

public interface RecipeRequest {

    //getRecipes
    @GET("/recipe/recipe-api/")
    Call<List<Recipe>> getRecipes();

}
