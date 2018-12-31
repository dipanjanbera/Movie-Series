package com.dipanjan.app.moviezone.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dipanjan.app.moviezone.adapter.GalleryAdapter;
import com.dipanjan.app.moviezone.model.DataModel;
import com.dipanjan.app.moviezone.model.Movie;
import com.dipanjan.app.moviezone.util.Constant;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.dipanjan.app.R;

/**
 * Created by LENOVO on 28-12-2018.
 */

public class ListLikedMovieItems extends AppCompatActivity {

    private Toolbar toolbar;
    private Integer URLIndexPosition=-1;

    private ArrayList<Movie> movies;
    ArrayList<DataModel> dataModelArrayList=null;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout,relativeLayoutForMessageText;
    private CoordinatorLayout coordinatorLayout;
    private TextView messageText;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_movie_activity);
        sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);


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

        toolbar.setTitleTextAppearance(this,R.style.CodeFont_Movie_Details_Headers);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }


        startUpActivity();



    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
       fetchLikedMovieDetails();
       if(mAdapter!=null){
           mAdapter.notifyDataSetChanged();
       }else{
           displayFetchedMovieItemAsList();
       }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movies=null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        movies=null;
    }

    public void fetchLikedMovieDetails(){
        Gson gson = new Gson();
        String json = sharedpreferences.getString("likedMovies", null);
        if(json!=null){
            Type type = new TypeToken<List<Movie>>(){}.getType();
            ArrayList<Movie> movieList = gson.fromJson(json, type);
            this.movies=movieList;
            if(this.movies!=null && this.movies.size()>0){
                for(Movie movie:movies){
                    movie.setMediumCoverImage(Constant.BASE_URL[getURLIndexPosition()]+movie.getBackgroundImage());
                    Log.d("@@@@@@@@",movie.getMediumCoverImage());
                }
            }

        }
    }

    public void startUpActivity(){
        fetchLikedMovieDetails();
        if(movies!=null&&movies.size()>0){
            displayFetchedMovieItemAsList();
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public void displayFetchedMovieItemAsList() {

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        mAdapter = new GalleryAdapter(getApplicationContext(), movies);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(),MovieDeatils.class);
                intent.putExtra("MOVIEID", movies.get(position).getId());
                intent.putExtra("URLIndexPosition", getURLIndexPosition());
                intent.putExtra("BACK_TRACK", "LIST_LIKED_MOVIE");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                finish();
                //Toast.makeText(getApplicationContext(),"come here "+movieSeries.get(position).getMovieSeriesTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }



    private Integer getURLIndexPosition(){
        return getIntent().getIntExtra("URLIndexPosition",-1);
    }


}
