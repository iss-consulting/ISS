package com.iss.flavr.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("born_date")
    @Expose
    private String bornDate;
    @SerializedName("person_image")
    @Expose
    private String personImage;
    
    public Author(Integer id, User user, String genre, String bornDate, String personImage) {
        super();
        this.id = id;
        this.user = user;
        this.genre = genre;
        this.bornDate = bornDate;
        this.personImage = personImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Author withId(Integer id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Author withUser(User user) {
        this.user = user;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Author withGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public Author withBornDate(String bornDate) {
        this.bornDate = bornDate;
        return this;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public Author withPersonImage(String personImage) {
        this.personImage = personImage;
        return this;
    }

}