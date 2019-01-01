package com.dipanjan.app.moviezone.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
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

        toolbar.setTitleTextAppearance(this,R.style.CodeFont_Movie_Details_Headers);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("My Favourite Movies");

        }


        startUpActivity();



    }

   /* @Override
    protected void onPostResume() {
        super.onPostResume();
       fetchLikedMovieDetails();
       if(mAdapter!=null){
           //mAdapter.notifyDataSetChanged();
       }else{
         //  displayFetchedMovieItemAsList();
       }

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movies=null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        movies=null;
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
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
                    movie.setMediumCoverImage(Constant.BASE_URL[getURLIndexPosition()]+movie.getMediumCoverImage());
                    Log.d("@@@@@@@@",movie.getMediumCoverImage());
                }
            }

        }
    }

    public void startUpActivity(){
        fetchLikedMovieDetails();
        if(movies!=null && movies.size()>0){
            displayFetchedMovieItemAsList();
        }else{
            displayAlert(coordinatorLayout,"No favourite movies added",Constant.SNACKBAR_DISPALY_MODE_FAILURE,"");
            progressBar.setVisibility(View.GONE);
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

    public void displayAlert(final CoordinatorLayout coordinatorLayout, String msg, int displayMode, String actionText){
        final Snackbar snackBar = Snackbar.make(coordinatorLayout,msg , Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackBar.getView();
        if(displayMode== Constant.SNACKBAR_DISPALY_MODE_SUCCESS){
            layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        }else if(displayMode== Constant.SNACKBAR_DISPALY_MODE_FAILURE){
            layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Color_Red));
        }


        /*TextView action = layout.findViewById(android.support.design.R.id.snackbar_action);
        action.setMaxLines(2);
        action.setTextColor(layout.getContext().getResources().getColor(android.R.color.black));
        snackBar.setAction(actionText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(
                        "https://play.google.com/store/search?q=torrent download"));
                getApplicationContext().startActivity(intent);
            }
        });*/

        snackBar.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.fav);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_movies) {
            openDialog();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    DialogFragment dialogFragment;

    private void openDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment = new SearchDialogFragment();
        dialogFragment.show(ft, "dialog");
    }


    private Integer getURLIndexPosition(){
        return getIntent().getIntExtra("URLIndexPosition",-1);
    }


}
