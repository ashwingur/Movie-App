package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.movieapp.adapters.MovieRecyclerViewAdapter;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.List;

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

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SetupSearchView();

        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
    }

    // Get data from search view and query the api to get the results
    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMovieListViewModel.searchMovieApi(query, 1);
                recyclerView.smoothScrollToPosition(0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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