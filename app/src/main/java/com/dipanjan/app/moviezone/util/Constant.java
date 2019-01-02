package com.dipanjan.app.moviezone.util;

import java.util.ArrayList;

/**
 * Created by LENOVO on 09-06-2018.
 */

public interface Constant {

    // String BASE_URL = "https://yts.am";
    //String BASE_URL = "https://ytss.unblocked.ms/";
    //String BASE_URL = "https://yts.unblock.ws/";
    String[] BASE_URL= {"https://ytss.unblocked.si","https://yts.unblock.ws","https://ytss.unblocked.ms","https://yts.am"};



    String TAG = "Application";
    String MyPREFERENCES = "MyPrefs";
    String PREF_ENDPOINT = "ENDPOINT";
    String SOURCE_HOME = "HOME";
    String SOURCE_SEARCH = "SEARCH";
    String SOURCE_CATEGOTY_LIST = "CATEGORY_LIST";
    String INTENT_EXTRA_BACK_BUTTON_PRESS = "BACK_BUTTON_PRESS";
    String ACTIVITY_NAME = "Activity_Name";


    interface RowDisplay {
        String ACTION_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=action&with_rt_ratings=true";
        String HORROR_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=horror&with_rt_ratings=true";
        String COMEDY_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=comedy&with_rt_ratings=true";
        String ROMANCE_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=romance&with_rt_ratings=true";
        String DOCUMENTARY_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=documentary&with_rt_ratings=true";
        String BIOGRAPHY_MOVIE_URL = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&genre=biography&with_rt_ratings=true";
        String LATEST_MOVIE = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&genre=all";
        String BEST_MOVIES = "/api/v2/list_movies.json?minimum_rating=8&sort_by=rating&limit=" + MAX_ITEM_EACH_ROW;
        String MOVIE_3D = "/api/v2/list_movies.json?limit=" + MAX_ITEM_EACH_ROW + "&sort_by=rating&order_by=desc&with_rt_ratings=true&quality=3D";
        String POPULAR_DOWNLOAD = "/api/v2/list_movies.json?sort_by=download_count&limit=" + MAX_ITEM_EACH_ROW;

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
        String POPULAR_DOWNLOAD = "/api/v2/list_movies.json?sort_by=download_count&limit=" + MAX_ITEM_EACH_ROW + "&page=";

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
        String MOVIE_SHORT_DETAILS = "/api/v2/list_movies.json?query_term=";
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
        String MOVIE_3D = "3D";
        String MOVIE_POPULAR_DOWNLOAD = "download_count";
        int CATEGORY_TOTAL_COUNT = 9;
        String PREFIX = "Best of ";
        String SUFFIX = " Movies";
    }

    interface Header {
        String LATEST_MOVIE = "Latest Movies";
        String TOP_MOVIES = "Top Movies";
        String GENRE = "Browser By Genre";
        String MOVIES_3D = "Top 3D Movies";
        String POPULAR_DOWNLOAD = "Popular Download";
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
        String MOVIE_COUNT = "movie_count";
        String TORRENT_TYPE="type";


    }

    String[] colorArr = {"#fb8c00", "#f4e003", "#ffd600", "#ff6d00", "#ab47bc", "#aeea00"};
    int SNACKBAR_DISPALY_MODE_SUCCESS = 1;
    int SNACKBAR_DISPALY_MODE_FAILURE = 2;
    int SNACKBAR_DISPALY_MODE_SHORT = 3;
    int SNACKBAR_DISPALY_MODE_LONG = 4;
    int SNACKBAR_DISPALY_MODE_INFINITE = 5;

    String MESSAGE_NETWORK_NOT_AVIALABLE = "Network Connection not Available";
    String SUBTITLE_URL = "http://www.yifysubtitles.com/movie-imdb/";



    String JSON_URL = "https://gist.githubusercontent.com/dipanjanbera/f0ce53657a0a2a5b044407c8d27bfa86/raw/response.json";
    String IMAGE_PATH = "/assets/images/movies";
    //String JSON_TEXT = "{\"status\":\"ok\",\"status_message\":\"Query was successful\",\"movies\":[{\"title\":\"The Maze Runner\",\"poster\":\"\\/The_Maze_Runner_2014\\/medium-cover.jpg\",\"codes\":[\"tt1790864\",\"tt4046784\",\"tt4500922\"]},{\"title\":\"The Hunger Games \",\"poster\":\"\\/The_Hunger_Games_2012\\/medium-cover.jpg\",\"codes\":[\"tt1392170\",\"tt1951264\",\"tt1951265\",\"tt1951266\"]},{\"title\":\"The Hangover\",\"poster\":\"\\/The_Hangover_2009\\/medium-cover.jpg\",\"codes\":[\"tt1119646\",\"tt1411697\",\"tt1951261\"]},{\"title\":\"Harry Potter\",\"poster\":\"\\/Harry_Potter_and_the_Sorcerers_Stone_2001\\/medium-cover.jpg\",\"codes\":[\"tt0241527\",\"tt0295297\",\"tt0304141\",\"tt0330373\",\"tt0373889\",\"tt0417741\",\"tt0926084\",\"tt1201607\"]},{\"title\":\"Pirates of the Caribbean\",\"poster\":\"\\/pirates_of_the_caribbean_dead_men_tell_no_tales_2017\\/medium-cover.jpg\",\"codes\":[\"tt0325980\",\"tt0383574\",\"tt0449088\",\"tt1298650\",\"tt1790809\"]},{\"title\":\"Spider-Man\",\"poster\":\"\\/Spider_Man_Trilogy_2002\\/medium-cover.jpg\",\"codes\":[\"tt0145487\",\"tt0316654\",\"tt0413300\",\"tt0948470\",\"tt1872181\"]},{\"title\":\"The Dark Knight Trilogy\",\"poster\":\"\\/The_Dark_Knight_2008\\/medium-cover.jpg\",\"codes\":[\"tt0372784\",\"tt0468569\",\"tt1345836\"]},{\"title\":\"X-Men\",\"poster\":\"\\/X_Men_2000\\/medium-cover.jpg\",\"codes\":[\"tt0120903\",\"tt0290334\",\"tt0376994\",\"tt1270798\",\"tt0458525\",\"tt1877832\",\"tt1430132\",\"tt3385516\"]},{\"title\":\"SAW\",\"poster\":\"\\/Saw_UNRATED_2004\\/medium-cover.jpg\",\"codes\":[\"tt0387564\",\"tt0432348\",\"tt0489270\",\"tt0890870\",\"tt1132626\",\"tt1233227\"]},{\"title\":\"The Lord of the Rings\",\"poster\":\"\\/The_Lord_of_the_Rings_The_Fellowship_of_the_Ring_EXTENDED_2001\\/medium-cover.jpg\",\"codes\":[\"tt0120737\",\"tt0167261\",\"tt0167260\"]},{\"title\":\"Toy Story\",\"poster\":\"\\/Toy_Story_1995\\/medium-cover.jpg\",\"codes\":[\"tt0114709\",\"tt0120363\",\"tt0435761\"]},{\"title\":\"The Terminator\",\"poster\":\"\\/The_Terminator_1984\\/medium-cover.jpg\",\"codes\":[\"tt0088247\",\"tt0103064\",\"tt0181852\"]},{\"title\":\"The Maze Runner\",\"poster\":\"\\/The_Maze_Runner_2014\\/medium-cover.jpg\",\"codes\":[\"tt1790864\",\"tt4046784\",\"tt4500922\"]},{\"title\":\"The Hunger Games \",\"poster\":\"\\/The_Hunger_Games_2012\\/medium-cover.jpg\",\"codes\":[\"tt1392170\",\"tt1951264\",\"tt1951265\",\"tt1951266\"]}]}";
}
