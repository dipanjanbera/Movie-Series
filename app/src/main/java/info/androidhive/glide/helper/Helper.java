package info.androidhive.glide.helper;

import info.androidhive.glide.util.Constant;

/**
 * Created by LENOVO on 09-06-2018.
 */

public class Helper {
    public static String fetchMovieEndPaint(String categoty){
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.ACTION)){
            return Constant.ListDisplay.ACTION_MOVIE_URL;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.HORROR)){
            return Constant.ListDisplay.HORROR_MOVIE_URL;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.DOCUMENTARY)){
            return Constant.ListDisplay.DOCUMENTARY_MOVIE_URL;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.COMEDY)){
            return Constant.ListDisplay.COMEDY_MOVIE_URL;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.BIOGRAPHY)){
            return Constant.ListDisplay.BIOGRAPHY_MOVIE_URL;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.ROMANCE)){
            return Constant.ListDisplay.ROMANCE_MOVIE_URL;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.LATEST_MOVIES)){
            return Constant.ListDisplay.LATEST_MOVIE;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.BEST_MOVIES_RATING_ABOVE)){
            return Constant.ListDisplay.BEST_MOVIES;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.MOVIE_GERNE)){
            return Constant.ListDisplay.BEST_MOVIES;
        }
        return null;
    }

    public static String fetchMovieEndPointByGerne(String gerne){
        if(gerne!=null){
            return Constant.ListDisplay.BROWSE_MOVIE_BY_GERNE+gerne.toLowerCase()+"&page=";
        }
        return null;
    }
}
