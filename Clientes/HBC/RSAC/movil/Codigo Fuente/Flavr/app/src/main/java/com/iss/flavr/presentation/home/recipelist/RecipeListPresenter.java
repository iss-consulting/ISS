package com.iss.flavr.presentation.home.recipelist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iss.flavr.data.model.Recipe;
import com.iss.flavr.data.repository.remote.ServiceGenerator;
import com.iss.flavr.data.repository.remote.request.RecipeRequest;

import java.util.List;

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
        Call<List<Recipe>> call = recipeRequest.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e(TAG, "1");
                        if (isAttached()) {
                            getView().setInfo(response.body());
                            getView().showRecycler();
                            getView().stopRefresh();
                        }
                    } else {
                        if (isAttached()) {
                            getView().stopRefresh();
                            getView().showWSError();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
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