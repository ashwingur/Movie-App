package com.example.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    // Model class for the movies

    private String title;
    private String posterPath;
    private String releaseDate;
    private int movieId;
    private float voteAverage;
    private String movieOverview;

    public MovieModel(String title, String posterPath, String releaseDate, int movieId, float voteAverage, String movieOverview) {
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.voteAverage = voteAverage;
        this.movieOverview = movieOverview;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        movieId = in.readInt();
        voteAverage = in.readFloat();
        movieOverview = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };


    // Getters
    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeInt(movieId);
        dest.writeFloat(voteAverage);
        dest.writeString(movieOverview);
    }
}
