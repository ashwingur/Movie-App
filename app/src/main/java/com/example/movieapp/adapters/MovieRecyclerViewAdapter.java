package com.example.movieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MovieListActivity;
import com.example.movieapp.R;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.utils.Credentials;

import java.io.FileNotFoundException;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerViewAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieViewHolder)holder).title.setText(mMovies.get(position).getTitle());
        ((MovieViewHolder)holder).release_date.setText(mMovies.get(position).getRelease_date());
        ((MovieViewHolder)holder).duration.setText(mMovies.get(position).getOriginal_language());

        // Divide by 2 because voting is out of 10, but rating bar is out of 5
        ((MovieViewHolder)holder).ratingBar.setRating(mMovies.get(position).getVote_average() / 2);

        // Image view: Using glide library
        if (mMovies.get(position).getPoster_path() != null){
            Glide.with(((MovieViewHolder) holder).imageView.getContext())
                    .load(Credentials.BASE_IMAGE_URL + mMovies.get(position).getPoster_path())
                    .into(((MovieViewHolder)holder).imageView);
        } else {
            Glide.with(((MovieViewHolder) holder).imageView.getContext())
                    .load("https://upload.wikimedia.org/wikipedia/en/c/c8/Very_Black_screen.jpg")
                    .into(((MovieViewHolder)holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mMovies != null){
            return mMovies.size();
        }
        return 0;
    }

    public void setMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }
}
