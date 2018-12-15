package com.dipanjan.app.moviezone.util;

import java.util.ArrayList;

/**
 * Created by LENOVO on 09-06-2018.
 */

public interface Constant {

   // String BASE_URL = "https://yts.am";
    //String BASE_URL = "https://ytss.unblocked.ms/";
    //String BASE_URL = "https://yts.unblock.ws/";
     String[] BASE_URL= {"https://yts.am","https://ytss.unblocked.ms/","https://yts.unblock.ws/"};


    String TAG = "Application";
    String MyPREFERENCES = "MyPrefs" ;
    String PREF_ENDPOINT = "ENDPOINT";
    String SOURCE_HOME = "HOME";
    String SOURCE_SEARCH = "SEARCH";
    String SOURCE_CATEGOTY_LIST = "CATEGORY_LIST";
    String INTENT_EXTRA_BACK_BUTTON_PRESS = "BACK_BUTTON_PRESS";
    String ACTIVITY_NAME =  "Activity_Name";


    interface RowDisplay {
        String ACTION_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=action&with_rt_ratings=true";
        String HORROR_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=horror&with_rt_ratings=true";
        String COMEDY_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=comedy&with_rt_ratings=true";
        String ROMANCE_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=romance&with_rt_ratings=true";
        String DOCUMENTARY_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=documentary&with_rt_ratings=true";
        String BIOGRAPHY_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=biography&with_rt_ratings=true";
        String LATEST_MOVIE = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&genre=all";
        String BEST_MOVIES = "/api/v2/list_movies.json?minimum_rating=8&sort_by=rating&limit=" + MAX_ITEM_EACH_ROW;
        String MOVIE_3D= "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&with_rt_ratings=true&quality=3D";
        String POPULAR_DOWNLOAD="/api/v2/list_movies.json?sort_by=download_count&limit="+MAX_ITEM_EACH_ROW;

    }

    interface ListDisplay {
        String ACTION_MOVIE_URL = "/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=action&with_rt_ratings=true&page=";
        String HORROR_MOVIE_URL = "/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=horror&with_rt_ratings=true&page=";
        String COMEDY_MOVIE_URL = "/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=comedy&with_rt_ratings=true&page=";
        String ROMANCE_MOVIE_URL = "/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=romance&with_rt_ratings=true&page=";
        String DOCUMENTARY_MOVIE_URL = "/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=documentary&with_rt_ratings=true&page=";
        String BIOGRAPHY_MOVIE_URL = "/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=biography&with_rt_ratings=true&page=";
        String LATEST_MOVIE = "/api/v2/list_movies.json?page=";
        String BEST_MOVIES = "/api/v2/list_movies.json?minimum_rating=8&sort_by=rating&page=";
        String BROWSE_MOVIE_BY_GERNE = "/api/v2/list_movies.json?genre=";
        String SEARCH_MOVIE = "/api/v2/list_movies.json?";
        String MOVIE_3D = "/api/v2/list_movies.json?sort_by=rating&order_by=desc&with_rt_ratings=true&quality=3D&page=";
        String POPULAR_DOWNLOAD="/api/v2/list_movies.json?sort_by=download_count&limit="+MAX_ITEM_EACH_ROW+"&page=";

    }

    String[] IDENTIFIER_LIST = {MovieCategory.LATEST_MOVIES,
            MovieCategory.MOVIE_POPULAR_DOWNLOAD,
            MovieCategory.BEST_MOVIES_RATING_ABOVE,
            MovieCategory.MOVIE_3D};


    String[] QUERY_PARAMETER = {"genre",
            "sort_by",
            "minimum_rating",
            "quality",
            };

   /*String[] HEADER_LIST = {MovieCategory.LATEST_MOVIES,
            MovieCategory.BEST_MOVIES_RATING_ABOVE,
            MovieCategory.MOVIE_3D};*/

    String[] HEADER_LIST = {Header.LATEST_MOVIE,
            Header.POPULAR_DOWNLOAD,
            Header.TOP_MOVIES,
            Header.MOVIES_3D};

    String[] URL_LINK = {RowDisplay.LATEST_MOVIE,
            RowDisplay.POPULAR_DOWNLOAD,
            RowDisplay.BEST_MOVIES,
            RowDisplay.MOVIE_3D,
    };

    int MAX_ITEM_EACH_ROW = 6;


    interface StaticUrls {
        String MOVIE_DETAILS = "/api/v2/movie_details.json?with_images=true&with_cast=true&movie_id=";
        String SIMILAR_MOVIE_DETAILS = "/api/v2/movie_suggestions.json?movie_id=";
    }

    interface MovieCategory {
        String ACTION = "Action";
        String HORROR = "Horror";
        String DOCUMENTARY = "Documentary";
        String BIOGRAPHY = "Biography";
        String COMEDY = "Comedy";
        String ROMANCE = "Romance";
        String LATEST_MOVIES = "All";
        String BEST_MOVIES_RATING_ABOVE = "8";
        String MOVIE_GERNE = "gerne";
        String MOVIE_3D="3D";
        String MOVIE_POPULAR_DOWNLOAD="download_count";
        int CATEGORY_TOTAL_COUNT = 9;
        String PREFIX = "Best of ";
        String SUFFIX = " Movies";
    }

    interface Header{
        String LATEST_MOVIE = "Latest Movies";
        String TOP_MOVIES = "Top Movies";
        String GENRE = "Browser By Genre";
        String MOVIES_3D = "Top 3D Movies";
        String POPULAR_DOWNLOAD="Popular Download";
    }


    interface TagConstant {
        String ID = "id";
        String IMBD_CODE = "imdb_code";
        String TITLE_ENGLISH = "title_english";
        String YEAR = "year";
        String RATING = "rating";
        String RUNTIME = "runtime";
        String GENRES = "genres";
        String DOWNLOAN_COUNT = "download_count";
        String LIKE = "like_count";
        String DESCRIPTION_SHORT = "description_intro";
        String DESCRIPTION_FULL = "description_full";
        String YT_TRAILER_CODE = "yt_trailer_code";
        String LANGUAGE = "language";
        String MPA_RATING = "mpa_rating";
        String BACKGROUND_IMAGE_ORIGINAL = "background_image_original";
        String LARGE_COVER_IMAGE = "large_cover_image";
        String MEDIUM_COVER_IMAGE = "medium_cover_image";
        String SMALL_COVER_IMAGE = "small_cover_image";

        String ACTOR_NAME = "name";
        String ACTOR_ROLE = "character_name";
        String ACTOR_IMDB_CODE = "imdb_code";
        String ACTOR_PROFILE_PIC = "url_small_image";

        String TORRENT_URL = "url";
        String TORRENT_QUALITY = "quality";
        String TORRENT_SIZE = "size";
        String TORRENT_HASH = "hash";
        String TORRENT_SEEDS = "seeds";
        String TORRENT_PEERS = "peers";
        String MOVIE_COUNT="movie_count";



    }

    String[] colorArr = {"#fb8c00","#f4e003","#ffd600","#ff6d00","#ab47bc","#aeea00"};
    int SNACKBAR_DISPALY_MODE_SUCCESS = 1;
    int SNACKBAR_DISPALY_MODE_FAILURE = 2;
    String MESSAGE_NETWORK_NOT_AVIALABLE = "Network Connection not Available";
    String SUBTITLE_URL = "http://www.yifysubtitles.com/movie-imdb/";


}

