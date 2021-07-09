package com.example.movieapp.viewmodels;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.respositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    // This class is used for viewmodel

    private MovieRepository mMovieRepository;

    // Constructor
    public MovieListViewModel() {
        mMovieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovieRepository.getMovies();
    }


}
