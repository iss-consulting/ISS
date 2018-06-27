package com.iss.flavr.presentation.home.recipelist;

import com.iss.flavr.data.model.Recipe;

import java.util.List;

public class RecipeListContract {
    interface View {
        void setInfo(List<Recipe> recipeList);
        void showRecycler();
        void hideRecycler();
        void stopRefresh();
        void showWSError();
    }
    interface Presenter {
        void getRecipeList();
        void onViewDettached();
        void onViewAttached(RecipeListContract.View view);
    }
}
