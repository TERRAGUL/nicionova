package com.example.pr20;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView nameOriginal, year, rating, description, genres;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        nameOriginal = findViewById(R.id.textTitle);
        year = findViewById(R.id.textYear);
        rating = findViewById(R.id.textRating);
        description = findViewById(R.id.textDescription);
        genres = findViewById(R.id.textYear);
        poster = findViewById(R.id.imagePoster);

        MovieResponse movie = (MovieResponse) getIntent().getSerializableExtra("movie");

        if (movie != null) {
            String title = movie.getNameOriginal() != null ? movie.getNameOriginal() : movie.getNameRu();
            nameOriginal.setText("Название: " + title);
            year.setText("Год: " + movie.getYear());
            rating.setText("Рейтинг: " + movie.getRating());
            description.setText(movie.getDescription());

            StringBuilder genresText = new StringBuilder();
            for (Genre genre : movie.getGenres()) {
                genresText.append(genre.getGenre()).append(", ");
            }
            if (genresText.length() > 0) {
                genresText.setLength(genresText.length() - 2);
            }
            genres.setText("Жанры: " + genresText.toString());

            Glide.with(this)
                    .load(movie.getPosterUrl())
                    .into(poster);
        }
    }
}