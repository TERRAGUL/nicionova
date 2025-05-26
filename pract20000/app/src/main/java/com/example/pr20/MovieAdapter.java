package com.example.pr20;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<MovieResponse> movies;
    private final Context context;

    public MovieAdapter(Context context, List<MovieResponse> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieResponse movie = movies.get(position);
        String title = movie.getNameOriginal() != null
                ? movie.getNameOriginal()
                : movie.getNameRu();
        holder.title.setText(title);
        holder.year.setText("Год: " + movie.getYear());
        holder.rating.setText("Рейтинг: " + movie.getRating());

        Glide.with(context)
                .load(movie.getPosterUrl())
                .into(holder.poster);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        TextView year;
        TextView rating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.thumbPoster);
            title  = itemView.findViewById(R.id.thumbTitle);
            year   = itemView.findViewById(R.id.thumbYear);
            rating = itemView.findViewById(R.id.thumbRating);
        }
    }
}