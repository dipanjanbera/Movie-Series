package com.dipanjan.app.moviezone.helper;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.dipanjan.app.moviezone.util.Constant;

/**
 * Created by LENOVO on 09-06-2018.
 */

public class Helper {
    public static String fetchMovieEndPoint(String categoty){
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.ACTION)){
            return Constant.ListDisplay.ACTION_MOVIE_URL;
        }
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.MOVIE_3D)){
            return Constant.ListDisplay.MOVIE_3D;
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
        if(categoty.equalsIgnoreCase(Constant.MovieCategory.MOVIE_POPULAR_DOWNLOAD)){
            return Constant.ListDisplay.POPULAR_DOWNLOAD;
        }
        return null;
    }

    public static String fetchMovieEndPointByGerne(String gerne){
        if(gerne!=null){
            return Constant.ListDisplay.BROWSE_MOVIE_BY_GERNE+gerne.toLowerCase()+"&page=";
        }
        return null;
    }

    public static String getFullHeaderName(String category){
       /* return Constant.MovieCategory.PREFIX+category+ Constant.MovieCategory.SUFFIX;*/
       return category;
    }

    public static String generateMagneticUrl(String hash,String movieName) throws UnsupportedEncodingException {
        //magnet:?xt=urn:btih:TORRENT_HASH&dn=Url+Encoded+Movie+Name&tr=http://track.one:1234/announce&tr=udp://track.two:80

        String baseString = "magnet:?xt=urn:btih:"+hash+"&dn=";
        String encodedMovieName = URLEncoder.encode(movieName, "utf-8").replace("+", "%20");
        baseString+=encodedMovieName;
        Log.e("TAG","after mName encode = "+baseString);

        String tracker1 = "udp://open.demonii.com:1337/announce";
        String tracker2 = "udp://tracker.openbittorrent.com:80";
        String tracker3 = "udp://tracker.coppersurfer.tk:6969";
        String tracker4 = "udp://glotorrents.pw:6969/announce";
        String tracker5 = "udp://tracker.opentrackr.org:1337/announce";
        String tracker6 = "udp://torrent.gresille.org:80/announce";
        String tracker7 = "udp://p4p.arenabg.com:1337";
        String tracker8 = "udp://tracker.leechers-paradise.org:6969";

        String[] trackerArray = {tracker1,tracker2,tracker3,tracker4,tracker5,tracker6,tracker7,tracker8};
        String trackers ="";


        for (String trackerItem: trackerArray) {
            trackers+="&tr="+URLEncoder.encode(trackerItem, "utf-8").replace("+", "%20");
        }
        Log.e("TAG","after tracker encode = "+trackers);
        Log.e("TAG","final magnetic url  = "+baseString+trackers);

        return baseString+trackers;

    }



    public static String generateURL(Integer urlIndexPos,String URL){
        if(urlIndexPos>-1){
            return Constant.BASE_URL[urlIndexPos]+URL;
        }
        return null;
    }

}
