package com.iss.flavr.presentation.home.recipelist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iss.flavr.data.repository.remote.ServiceGenerator;
import com.iss.flavr.data.repository.remote.request.RecipeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListPresenter implements RecipeListContract.Presenter {
    private RecipeListContract.View view;
    private Context context;
    private final String TAG = RecipeListPresenter.class.getSimpleName();

    public RecipeListPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onViewAttached(RecipeListContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private RecipeListContract.View getView() {
        return view;
    }

    @Override
    public void getRecipeList() {
        if (isAttached()) {
            getView().hideRecycler();
        }
        final RecipeRequest recipeRequest = ServiceGenerator.createService(RecipeRequest.class);
        Call<JsonArray> call = recipeRequest.getRecipes();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (isAttached()) {
                            //getView().setInfo(?);
                            getView().showRecycler();
                            getView().stopRefresh();
                        }
                        if (isAttached()) {
                            getView().stopRefresh();
                            getView().showWSError();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (isAttached()) {
                    getView().stopRefresh();
                    getView().showWSError();
                }
            }
        });
    }


    private boolean isAttached() {
        return getView() != null;
    }

}