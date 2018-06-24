package com.iss.flavr.presentation.home.recipelist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iss.flavr.R;
import com.iss.flavr.data.model.Recipe;
import com.iss.flavr.util.CircleTransform;
import com.squareup.picasso.Picasso;

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
        private ImageView photo, userPhoto;
        private TextView title, username, description;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.iv_photo);
            title = itemView.findViewById(R.id.tv_title);
            username = itemView.findViewById(R.id.tv_user);
            description = itemView.findViewById(R.id.tv_description);
            userPhoto = itemView.findViewById(R.id.iv_user);
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
        Picasso.with(context).load(recipeList.get(position).getRecipeImage())
                .placeholder(R.drawable.dish)
                .centerCrop().fit().into(holder.photo);
        holder.title.setText(recipeList.get(position).getTitle());
        holder.username.setText("De: " + recipeList.get(position).getAuthor().getUser().getUsername());
        holder.description.setText(recipeList.get(position).getDescription());
        Picasso.with(context).load(recipeList.get(position).getAuthor().getPersonImage())
                .placeholder(R.drawable.user)
                .transform(new CircleTransform())
                .centerCrop().fit().into(holder.userPhoto);
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, recipeList.get(position).getListIngredients().get(0).getName(), Toast.LENGTH_SHORT).show();
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