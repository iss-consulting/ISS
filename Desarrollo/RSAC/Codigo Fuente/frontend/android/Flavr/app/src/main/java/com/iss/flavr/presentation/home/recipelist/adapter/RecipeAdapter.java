package com.iss.flavr.presentation.home.recipelist.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iss.flavr.R;
import com.iss.flavr.data.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    public List<Recipe> recipeList;
    public Context context;

    public RecipeAdapter(Context context) {
        this.context = context;
    }

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.iv_photo);
        }

    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        RecipeAdapter.ViewHolder viewholder = new RecipeAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, final int position) {
        //Picasso.with(context).load(recipeList.get(position).getPhoto()).centerCrop().fit().into(holder.photo);
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(?);
                //context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        } else {
            return recipeList.size();
        }
    }
}