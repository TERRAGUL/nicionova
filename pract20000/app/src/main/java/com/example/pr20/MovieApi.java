package com.example.pr20;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MovieApi {
    @Headers({
            "X-API-KEY: a60009e6-b32c-431d-b545-c94ca1baf9c8",
            "Accept: application/json"
    })
    @GET("api/v2.1/films/search-by-keyword")
    Call<MovieSearchResult> searchMovies(
            @Query("keyword") String keyword,
            @Query("page") int page
    );


    class MovieSearchResult {
        public List<MovieResponse> films;
    }
}

