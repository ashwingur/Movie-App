package com.example.movieapp.respositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    // This class is acting as repositories

    private static MovieRepository instance;

    private MovieApiClient mMovieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){
        if (instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        mMovieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){ return mMovieApiClient.getMovies(); }

    // (2) Calling the method
    public void searchMovieApi(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        mMovieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchNextPage(){
        this.searchMovieApi(mQuery, mPageNumber + 1);
    }
}
