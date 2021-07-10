package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.movieapp.adapters.MovieRecyclerViewAdapter;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.Servicee;
import com.example.movieapp.response.MovieResponse;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
    private static final String TAG = "MovieListActivity";

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter mAdapter;

    // ViewModel
    private MovieListViewModel mMovieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);


        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        searchMovieApi("fast",1);
    }

    // Observing any data change
    private void ObserveAnyChange(){
         mMovieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
             @Override
             public void onChanged(List<MovieModel> movieModels) {
                 // Observing for any data change
                 if (movieModels != null){
                     for (MovieModel m: movieModels){
                         // Get data in log
                         Log.v(TAG, "onChanged: " + m.getTitle());
                         mAdapter.setMovies(movieModels);
                     }
                 }
             }
         });
    }

    // (4) Calling method in main activity
    private void searchMovieApi(String query, int pageNumber){
        mMovieListViewModel.searchMovieApi(query, pageNumber);
    }

    // Initialising recyclerview and adapter
    private void ConfigureRecyclerView(){
        mAdapter = new MovieRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onMovieClick(int position) {
        Toast.makeText(this, "Clicked on " + mMovieListViewModel.getMovies().getValue().get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(String category) {

    }
}