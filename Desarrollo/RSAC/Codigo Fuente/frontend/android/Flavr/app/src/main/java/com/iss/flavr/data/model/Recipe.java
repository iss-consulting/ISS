package com.iss.flavr.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("recipe_image")
    @Expose
    private String recipeImage;
    @SerializedName("list_ingredients")
    @Expose
    private List<String> listIngredients = null;

    public Recipe(Integer id, Author author, String title, String description, String creationDate, String recipeImage, List<String> listIngredients) {
        super();
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.recipeImage = recipeImage;
        this.listIngredients = listIngredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe withId(Integer id) {
        this.id = id;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Recipe withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Recipe withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Recipe withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public Recipe withRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
        return this;
    }

    public List<String> getListIngredients() {
        return listIngredients;
    }

    public void setListIngredients(List<String> listIngredients) {
        this.listIngredients = listIngredients;
    }

    public Recipe withListIngredients(List<String> listIngredients) {
        this.listIngredients = listIngredients;
        return this;
    }
}