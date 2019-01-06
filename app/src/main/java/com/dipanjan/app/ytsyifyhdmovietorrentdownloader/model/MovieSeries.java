package com.dipanjan.app.ytsyifyhdmovietorrentdownloader.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LENOVO on 24-12-2018.
 */

public class MovieSeries implements Serializable {

    private String MovieSeriesTitle;
    private String description;
    private Integer numberOfMovies;
    private String genres;
    private String moviePoster;
    private ArrayList<String> imdbCodes = new ArrayList<String>();

    public String getMovieSeriesTitle() {
        return MovieSeriesTitle;
    }

    public void setMovieSeriesTitle(String movieSeriesTitle) {
        MovieSeriesTitle = movieSeriesTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfMovies() {
        return numberOfMovies;
    }

    public void setNumberOfMovies(Integer numberOfMovies) {
        this.numberOfMovies = numberOfMovies;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public ArrayList<String> getImdbCodes() {
        return imdbCodes;
    }

    public void setImdbCodes(ArrayList<String> imdbCodes) {
        this.imdbCodes = imdbCodes;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    @Override
    public String toString() {
        return "MovieSeries{" +
                "MovieSeriesTitle='" + MovieSeriesTitle + '\'' +
                ", description='" + description + '\'' +
                ", numberOfMovies=" + numberOfMovies +
                ", genres='" + genres + '\'' +
                ", imdbCodes=" + imdbCodes +
                '}';
    }
}
