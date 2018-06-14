package com.iss.flavr.presentation.home.recipelist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iss.flavr.R;
import com.iss.flavr.data.model.Recipe;
import com.iss.flavr.presentation.home.recipelist.adapter.RecipeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeListFragment extends Fragment implements RecipeListContract.View {
    RecyclerView rvRecipes;
    SwipeRefreshLayout swRecipes;

    private RecipeListContract.Presenter presenter;
    private Context context;
    private final String TAG = RecipeListFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        context = getActivity();

        swRecipes = rootView.findViewById(R.id.sw_recipes);
        rvRecipes = rootView.findViewById(R.id.rv_recipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(context));
        rvRecipes.setAdapter(new RecipeAdapter(context));

        presenter = new RecipeListPresenter(context);
        presenter.getRecipeList();

        swRecipes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getRecipeList();
            }
        });

        return rootView;
    }

    private RecipeListContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setInfo(List<Recipe> recipeList) {
        RecipeAdapter adapter = new RecipeAdapter(recipeList, context);
        rvRecipes.setAdapter(adapter);
    }

    @Override
    public void hideRecycler() {
        swRecipes.setVisibility(View.GONE);
    }

    @Override
    public void stopRefresh() {
        swRecipes.setRefreshing(false);
    }

    @Override
    public void showWSError() {
        Toast.makeText(context, "Service error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRecycler() {
        swRecipes.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(RecipeListFragment.this);
        showRecycler();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
        stopRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
