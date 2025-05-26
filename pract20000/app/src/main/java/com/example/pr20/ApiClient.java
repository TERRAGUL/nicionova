package com.example.pr20;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
    private static MovieApi movieApi;

    public static MovieApi getMovieApi() {
        if (movieApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            movieApi = retrofit.create(MovieApi.class);
        }
        return movieApi;
    }
}

