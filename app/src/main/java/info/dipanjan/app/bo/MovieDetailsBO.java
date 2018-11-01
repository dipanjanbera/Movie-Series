package info.dipanjan.app.bo;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.dipanjan.app.model.Cast;
import info.dipanjan.app.model.Movie;
import info.dipanjan.app.model.Torrent;
import info.dipanjan.app.util.Constant;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by LENOVO on 24-06-2018.
 */

public class MovieDetailsBO {

    public static Movie fetchSingleMovieItemDetails(String jsonStr){
        Movie movie=null;
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONObject innerJsonObject = jsonObject.getJSONObject("data");

            JSONObject jobject = innerJsonObject.getJSONObject("movie");
            movie = new Movie();
            buildMovieObject(movie,jobject);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return movie;
    }

    private static String constructGenreString=null;
    private static void buildMovieObject(Movie movie, JSONObject obj) throws JSONException {

        movie.setTitle(obj.getString(Constant.TagConstant.TITLE_ENGLISH));
        movie.setBackgroundImage(obj.getString(Constant.TagConstant.LARGE_COVER_IMAGE));
        movie.setRating(obj.getString(Constant.TagConstant.RATING));
        movie.setYear(obj.getString(Constant.TagConstant.YEAR));
        movie.setLikeCount(obj.getString(Constant.TagConstant.LIKE));
        movie.setMpaRating(obj.getString(Constant.TagConstant.MPA_RATING));

        movie.setRating(obj.getString(Constant.TagConstant.RATING));
        movie.setRuntime(obj.getString(Constant.TagConstant.RUNTIME));
        movie.setDescriptionFull(obj.getString(Constant.TagConstant.DESCRIPTION_FULL));
        movie.setYtTrailerCode(obj.getString(Constant.TagConstant.YT_TRAILER_CODE));
        movie.setImdbCode(obj.getString(Constant.TagConstant.IMBD_CODE));
        if(obj.has(Constant.TagConstant.GENRES)){
            JSONArray genreJsonArray = obj.getJSONArray(Constant.TagConstant.GENRES);
            for(int index=0;index<genreJsonArray.length();index++){

                if(constructGenreString==null){
                    constructGenreString = genreJsonArray.getString(index);
                }else{
                    constructGenreString = constructGenreString+" / "+genreJsonArray.getString(index);
                }


            }
            movie.setGenres(constructGenreString);
            constructGenreString=null;
        }

        if(obj.has("cast")){
            JSONArray castJsonArray = obj.getJSONArray("cast");
            ArrayList<Cast> castList = new ArrayList<Cast>();
            for(int index=0;index<castJsonArray.length();index++){
                JSONObject castObject = castJsonArray.getJSONObject(index);
                Cast cast = new Cast();
                cast.setActorNmae(castObject.getString(Constant.TagConstant.ACTOR_NAME));
                cast.setActorRole(castObject.getString(Constant.TagConstant.ACTOR_ROLE));
                if(castObject.has("url_small_image")){
                    cast.setProfilePic(castObject.getString(Constant.TagConstant.ACTOR_PROFILE_PIC));
                }
                if(castObject.has(Constant.TagConstant.IMBD_CODE)){
                    cast.setImdbCode(castObject.getString(Constant.TagConstant.IMBD_CODE));
                }
                castList.add(cast);
            }
            movie.setCastArr(castList);
        }

        if(obj.has("torrents")){
            JSONArray torrentJsonArray = obj.getJSONArray("torrents");
            Log.d("ABCD",""+torrentJsonArray.length());
            ArrayList<Torrent> torrentList = new ArrayList<Torrent>();
            for(int index=0;index<torrentJsonArray.length();index++){
                JSONObject torrentObj = torrentJsonArray.getJSONObject(index);
                Log.d("ABCD",""+torrentObj.toString());
                Torrent torrent = new Torrent();
                torrent.setUrl(torrentObj.getString(Constant.TagConstant.TORRENT_URL));
                torrent.setQuality(torrentObj.getString(Constant.TagConstant.TORRENT_QUALITY));
                torrent.setSize(torrentObj.getString(Constant.TagConstant.TORRENT_SIZE));
                torrent.setHash(torrentObj.getString(Constant.TagConstant.TORRENT_HASH));
                torrent.setSeeds(torrentObj.getString(Constant.TagConstant.TORRENT_SEEDS));
                torrent.setPeers(torrentObj.getString(Constant.TagConstant.TORRENT_PEERS));
                torrentList.add(torrent);
                Log.d("ABCD",torrent.getQuality());
            }

            movie.setTorrentArr(torrentList);
        }

    }



    public static void  populateMovieDetailsForSimilarMovies (String stg, ArrayList<Movie> movies) {
        // Toast.makeText(this.getApplicationContext(),"come here ",Toast.LENGTH_SHORT).show();

        try {
            JSONObject jObj = new JSONObject(stg);
            String city = jObj.getString("status_message");
            System.out.println(city);

            JSONObject jObj1 = jObj.getJSONObject("data");
            JSONArray jArr = jObj1.getJSONArray("movies");
            for (int i=0; i < jArr.length(); i++) {
                Movie movie=new Movie();
                JSONObject obj = jArr.getJSONObject(i);
                movie.setMediumCoverImage(obj.getString("medium_cover_image"));
                movie.setTitle(obj.getString("title"));
                movie.setRating(obj.getString("rating"));
                movie.setYear(obj.getString("year"));
                movie.setRuntime(obj.getString("runtime"));
                movie.setId(obj.getString("id"));
                movie.setYtTrailerCode(obj.getString("yt_trailer_code"));

                movies.add(movie);
            }



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public static long DownloadData (Context context, String uri, View v) {


        long downloadReference;
        DownloadManager downloadManager;
        // Create request for android download manager
        downloadManager = (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = null;

            request = new DownloadManager.Request(Uri.parse(uri));


        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,"AndroidTutorialPoint.jpg");
        Toast.makeText(context,"come here "+Environment.DIRECTORY_DOWNLOADS,Toast.LENGTH_LONG).show();
        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);



        return downloadReference;
    }
}


