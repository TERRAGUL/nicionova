package com.example.pr20;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MovieResponse implements Serializable {

    @SerializedName("nameEn")
    private String nameOriginal;

    @SerializedName("nameRu")
    private String nameRu;

    @SerializedName("year")
    private String year;

    @SerializedName("rating")
    private String rating;

    @SerializedName("posterUrl")
    private String posterUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("genres")
    private List<Genre> genres;

    public String getNameOriginal() {
        return nameOriginal;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}