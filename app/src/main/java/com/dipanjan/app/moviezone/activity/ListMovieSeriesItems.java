package com.dipanjan.app.moviezone.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dipanjan.app.moviezone.adapter.GalleryAdapter;
import com.dipanjan.app.moviezone.app.AppController;
import com.dipanjan.app.moviezone.bo.MovieDetailsBO;
import com.dipanjan.app.moviezone.helper.Helper;
import com.dipanjan.app.moviezone.helper.NetworkCheck;
import com.dipanjan.app.moviezone.model.Movie;
import com.dipanjan.app.moviezone.model.MovieSeries;
import com.dipanjan.app.moviezone.util.Constant;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.dipanjan.app.R;

/**
 * Created by LENOVO on 24-12-2018.
 */

public class ListMovieSeriesItems extends AppCompatActivity
{
    private String TAG = ListMovieSeriesItems.class.getSimpleName();
    //private static final String endpoint = "https://yts.am/api/v2/list_movies.json?page=";
    private static String endpoint = null;
    private ArrayList<Movie> movies;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private int pageCount=1;
    private ProgressBar progressBar,progressBarBottomPanel;
    private RelativeLayout relativeLayout,relativeLayoutBottomPanel,relativeLayoutForMessageText;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences sharedpreferences;
    int movieCount=0;
    private TextView messageText;
    private Integer URLIndexPosition;
    private MovieSeries movieSeries;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_movie_activity);
        URLIndexPosition=-1;

        movieSeries = (MovieSeries) getIntent().getSerializableExtra("MOVIESERIES");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this,R.style.CodeFont_Movie_Details_Headers);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);

        relativeLayout = (RelativeLayout) findViewById(R.id.spinKitLayout);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        relativeLayoutForMessageText = (RelativeLayout)findViewById(R.id.messageLayout);
        messageText = (TextView)findViewById(R.id.messageText);
        relativeLayoutForMessageText.setVisibility(View.GONE);
        messageText.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);



        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        pDialog = new ProgressDialog(this);
        movies = new ArrayList<Movie>();




        startUpActivity();


    }

    private void startUpActivity(){

        if(isNetworkAvailable()){

            NetworkCheck networkCheck = (NetworkCheck) new NetworkCheck(new NetworkCheck.AsyncResponse() {
                @Override
                public Integer processFinish(Integer URLIndexPos) {
                    if(URLIndexPos!=1){
                        URLIndexPosition=URLIndexPos;
                        relativeLayoutForMessageText.setVisibility(View.GONE);
                        messageText.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        fetchMovieDetails(movieSeries);

                    }else{
                        relativeLayout.setVisibility(View.GONE);
                        relativeLayoutForMessageText.setVisibility(View.VISIBLE);
                        messageText.setVisibility(View.VISIBLE);
                        messageText.setText(NetworkCheck.DISPLAY_MSG_IF_HOST_NOT_RESOLVE);
                        displayNetworkInfoAlert(coordinatorLayout, NetworkCheck.DISPLAY_SNACBAR_MSG_IF_HOST_NOT_RESOLVE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
                    }
                    return null;
                }
            }).execute();


        }else {
            progressBar.setVisibility(View.GONE);
            displayNetworkInfoAlert(coordinatorLayout, Constant.MESSAGE_NETWORK_NOT_AVIALABLE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
        }
    }



    public void displayNetworkInfoAlert(final CoordinatorLayout coordinatorLayout, String msg,int displayMode){
        final Snackbar snackBar = Snackbar.make(coordinatorLayout,msg , Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackBar.getView();
        if(displayMode== Constant.SNACKBAR_DISPALY_MODE_SUCCESS){
            layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        }else if(displayMode== Constant.SNACKBAR_DISPALY_MODE_FAILURE){
            layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Color_Red));
        }


        TextView action = layout.findViewById(android.support.design.R.id.snackbar_action);
        action.setMaxLines(5);
        action.setTextColor(layout.getContext().getResources().getColor(android.R.color.black));
        snackBar.setAction("Try Again", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    NetworkCheck networkCheck = (NetworkCheck) new NetworkCheck(new NetworkCheck.AsyncResponse() {
                        @Override
                        public Integer processFinish(Integer URLIndexPos) {
                            if(URLIndexPos!=-1){
                                URLIndexPosition=URLIndexPos;
                                relativeLayoutForMessageText.setVisibility(View.GONE);
                                messageText.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                fetchMovieDetails(movieSeries);
                            }else{
                                relativeLayout.setVisibility(View.GONE);
                                relativeLayoutForMessageText.setVisibility(View.VISIBLE);
                                messageText.setVisibility(View.VISIBLE);
                                messageText.setText(NetworkCheck.DISPLAY_MSG_IF_HOST_NOT_RESOLVE);
                                displayNetworkInfoAlert(coordinatorLayout, NetworkCheck.DISPLAY_SNACBAR_MSG_IF_HOST_NOT_RESOLVE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
                            }
                            return null;
                        }
                    }).execute();
                    snackBar.dismiss();
                    relativeLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                    displayNetworkInfoAlert(coordinatorLayout, Constant.MESSAGE_NETWORK_NOT_AVIALABLE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
                }
            }
        });

        snackBar.show();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void fetchMovieDetails(MovieSeries movieSeries){
       ArrayList<String> urlArr= MovieDetailsBO.generateURLForMovieSeries(URLIndexPosition,movieSeries.getImdbCodes());

       for(String url:urlArr){
           callWebServiceTofetchDetails(url);
       }

    }

    public void callWebServiceTofetchDetails(String url){

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //pDialog.hide();
                //movies.clear();

                populateMovies(response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //pDialog.hide();
            }
        });


        // Adding request to request queue
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq);
    }


    private int flag=0;
    ArrayList<String> responseList = new ArrayList<String>();
    public  synchronized void populateMovies(String response){
        if(response!=null){
            flag++;
            responseList.add(response);
            if(flag==movieSeries.getImdbCodes().size()){
                buildMovieObj(responseList);
            }
        }

    }

    public void displayFetchedMovieItemAsList(){
        mAdapter = new GalleryAdapter(getApplicationContext(), movies);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setAdapter(mAdapter);





        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", movies);
                bundle.putInt("position", position);

                /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");*/

                Intent intent = new Intent(getApplicationContext(),MovieDeatils.class);
                intent.putExtra("MOVIEID", movies.get(position).getId());
                intent.putExtra("URLIndexPosition", URLIndexPosition);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

                //Toast.makeText(getApplicationContext(),"come here "+movies.get(position).getId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    public void buildMovieObj(ArrayList<String> responseArr){
        for(String response:responseArr){
            populateMovieObject(response);
        }

        displayFetchedMovieItemAsList();
    }

    public void populateMovieObject (String stg) {
        String constructGenreString = null;
        try {
            JSONObject jObj = new JSONObject(stg);
            JSONObject jObj1 = jObj.getJSONObject("data");

            String movieCount = jObj1.getString(Constant.TagConstant.MOVIE_COUNT);
            if(movieCount!=null){
                try{
                    this.movieCount=Integer.parseInt(movieCount);
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
            if(jObj1.has("movies")){
                JSONArray jArr = jObj1.getJSONArray("movies");
                if(jArr!=null && jArr.length()>0){
                    for (int i=0; i < jArr.length(); i++) {
                        Movie movie=new Movie();
                        JSONObject obj = jArr.getJSONObject(i);
                        movie.setMediumCoverImage(obj.getString("large_cover_image"));
                        movie.setTitle(obj.getString("title"));
                        movie.setRating(obj.getString("rating"));
                        movie.setYear(obj.getString("year"));
                        movie.setRuntime(obj.getString("runtime"));
                        movie.setId(obj.getString("id"));
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

                        movies.add(movie);
                    }
                }


            }




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}


