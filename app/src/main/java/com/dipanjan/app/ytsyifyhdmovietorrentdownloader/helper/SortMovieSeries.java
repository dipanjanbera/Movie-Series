package com.dipanjan.app.ytsyifyhdmovietorrentdownloader.helper;

import com.dipanjan.app.ytsyifyhdmovietorrentdownloader.model.Movie;

import java.util.Comparator;

/**
 * Created by LENOVO on 05-01-2019.
 */

public class SortMovieSeries implements Comparator<Movie> {
    public int compare(Movie obj1, Movie obj2)
    {
        if (obj1 == obj2) {
            return 0;
        }
        if (obj1 == null) {
            return -1;
        }
        if (obj2 == null) {
            return 1;
        }
        return obj1.getImdbCode().compareTo(obj2.getImdbCode());
    }
}
