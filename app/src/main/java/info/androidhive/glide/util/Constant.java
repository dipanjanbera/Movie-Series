package info.androidhive.glide.util;

/**
 * Created by LENOVO on 09-06-2018.
 */

public interface Constant {

    static final String BASE_URL="https://yts-am.prox.fun";

    interface RowDisplay{
        static String ACTION_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?limit=5&sort_by=rating&order_by=desc&genre=action&with_rt_ratings=true";
        static String HORROR_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?limit=5&sort_by=rating&order_by=desc&genre=horror&with_rt_ratings=true";
        static String COMEDY_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?limit=5&sort_by=rating&order_by=desc&genre=comedy&with_rt_ratings=true";
        static String ROMANCE_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?limit=5&sort_by=rating&order_by=desc&genre=romance&with_rt_ratings=true";
        static String DOCUMENTARY_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?limit=5&sort_by=rating&order_by=desc&genre=documentary&with_rt_ratings=true";
        static String BIOGRAPHY_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?limit=5&sort_by=rating&order_by=desc&genre=biography&with_rt_ratings=true";
        static String LATEST_MOVIE = BASE_URL+"/api/v2/list_movies.json?limit=6&genre=all";
        static String BEST_MOVIES = BASE_URL+"/api/v2/list_movies.json?minimum_rating=8.5&sort_by=rating&limit=6";

    }
    interface ListDisplay{
        static String ACTION_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=action&with_rt_ratings=true&page=";
        static String HORROR_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=horror&with_rt_ratings=true&page=";
        static String COMEDY_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=comedy&with_rt_ratings=true&page=";
        static String ROMANCE_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=romance&with_rt_ratings=true&page=";
        static String DOCUMENTARY_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=documentary&with_rt_ratings=true&page=";
        static String BIOGRAPHY_MOVIE_URL = BASE_URL+"/api/v2/list_movies.json?sort_by=rating&order_by=desc&genre=biography&with_rt_ratings=true&page=";
        static String LATEST_MOVIE = BASE_URL+"/api/v2/list_movies.json?page=";
        static String BEST_MOVIES = BASE_URL+"/api/v2/list_movies.json?minimum_rating=8.5&sort_by=rating&page=";
        static String BROWSE_MOVIE_BY_GERNE = BASE_URL+"/api/v2/list_movies.json?genre=";
        static String SEARCH_MOVIE=BASE_URL+"/api/v2/list_movies.json?";

    }


    interface StaticUrls{
        String MOVIE_DETAILS = BASE_URL+"/api/v2/movie_details.json?with_images=true&with_cast=true&movie_id=";
        String SIMILAR_MOVIE_DETAILS = BASE_URL+"/api/v2/movie_suggestions.json?movie_id=";
    }

    interface MovieCategory{
        static String ACTION = "action";
        static String HORROR = "horror";
        static String DOCUMENTARY = "documentary";
        static String BIOGRAPHY = "biography";
        static String COMEDY = "comedy";
        static String ROMANCE = "romance";
        static String LATEST_MOVIES = "all";
        static String BEST_MOVIES_RATING_ABOVE = "8.5";
        static String MOVIE_GERNE = "gerne";
        static int CATEGORY_TOTAL_COUNT = 9;
    }

    interface TagConstant{
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




    }



}
