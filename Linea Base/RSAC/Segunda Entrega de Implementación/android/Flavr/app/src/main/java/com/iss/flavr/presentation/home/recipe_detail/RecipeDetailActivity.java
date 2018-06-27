package com.iss.flavr.presentation.home.recipe_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.flavr.R;
import com.iss.flavr.data.model.Recipe;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {


    Recipe recipe;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_ing_title)
    TextView tvIngTitle;
    @BindView(R.id.tv_ing)
    TextView tvIng;
    @BindView(R.id.tv_procedure_title)
    TextView tvProcedureTitle;
    @BindView(R.id.tv_procedure)
    TextView tvProcedure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        setUpToolbar();
        setContent();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setTitle(recipe.getTitle());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setContent() {
        Picasso.with(this).load("http://" + recipe.getRecipeImage())
                .placeholder(R.drawable.dish)
                .centerCrop().fit().into(ivPhoto);
        tvName.setText(recipe.getTitle());
        tvAuthor.setText("Por: "+recipe.getAuthor().getUser().getUsername());
        String ing="";
        for(int i=0;i<recipe.getListIngredients().size();i++){
            ing=ing+"-"+recipe.getListIngredients().get(i);
            if(i!=recipe.getListIngredients().size()-1){
                ing=ing+"\n";
            }
        }
        tvIng.setText(ing);
        tvProcedure.setText(recipe.getDescription());
        tvIngTitle.setText("Ingredientes");
        tvProcedureTitle.setText("Procedimiento");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
