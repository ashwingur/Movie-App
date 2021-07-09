package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class MovieListActivity extends AppCompatActivity {
    private static final String TAG = "MovieListActivity";
    Button btn;

    // ViewModel
    private MovieListViewModel mMovieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);

        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserveAnyChange();

        // Testing the method
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovieApi("Fast", 1);
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
                     }
                 }
             }
         });
    }

    // (4) Calling method in main activity
    private void searchMovieApi(String query, int pageNumber){
        mMovieListViewModel.searchMovieApi(query, pageNumber);
    }

    private void getRetrofitResponseAccordingToID() {

        MovieApi movieApi = Servicee.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(
                343611,
                Credentials.API_KEY
        );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if (response.code() == 200){
                    //MovieModel movie = response.body().getMovie();
                    Log.v(TAG, "The response " + response.body().toString());
                } else {
                    try{
                        Log.v(TAG, "Error " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    private void getRetrofitResponse() {
        MovieApi movieApi = Servicee.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
                Credentials.API_KEY,
                "Action",
                1
        );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200){
                    // Successfully got a response
                    Log.d(TAG, "onResponse: The response: " + response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel m : movies){
                        Log.v(TAG, "The release date: " + m.getRelease_date());
                    }
                } else {
                    try {
                        Log.v(TAG, "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

}