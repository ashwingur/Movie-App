package com.example.movieapp.respositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.models.MovieModel;

import java.util.List;

public class MovieRepository {
    // This class is acting as repositories

    private static MovieRepository instance;

    // LiveData
    private MutableLiveData<List<MovieModel>> mMovies = new MutableLiveData<>();

    public static MovieRepository getInstance(){
        if (instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){ return mMovies; }
}
