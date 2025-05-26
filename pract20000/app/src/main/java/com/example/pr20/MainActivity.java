package com.example.pr20;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText searchInput;
    Button searchButton;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView errorMessage;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.editTextSearch);
        searchButton = findViewById(R.id.buttonSearch);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        errorMessage = findViewById(R.id.errorMessage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(v -> {
            String keyword = searchInput.getText().toString().trim();
            if (!keyword.isEmpty()) {
                searchMovies(keyword);
            } else {
                errorMessage.setText("Введите запрос");
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void searchMovies(String keyword) {
        progressBar.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);

        ApiClient.getMovieApi().searchMovies(keyword, 1).enqueue(new Callback<MovieApi.MovieSearchResult>() {
            @Override
            public void onResponse(Call<MovieApi.MovieSearchResult> call, Response<MovieApi.MovieSearchResult> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieResponse> movies = response.body().films;
                    if (movies == null || movies.isEmpty()) {
                        errorMessage.setText("Фильмы не найдены");
                        errorMessage.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setAdapter(new MovieAdapter(MainActivity.this, movies));
                    }
                } else {
                    handleErrorCode(response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieApi.MovieSearchResult> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                errorMessage.setText("Ошибка подключения: " + t.getMessage());
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleErrorCode(int code) {
        String message;
        switch (code) {
            case 400: message = "Неверный запрос (400)"; break;
            case 401: message = "Нет доступа к API (401)"; break;
            case 404: message = "Фильм не найден (404)"; break;
            case 500: message = "Ошибка сервера (500)"; break;
            default: message = "Ошибка: " + code; break;
        }
        errorMessage.setText(message);
        errorMessage.setVisibility(View.VISIBLE);
    }
}
