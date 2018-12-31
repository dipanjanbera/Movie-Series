package com.dipanjan.app.moviezone.activity;

import android.content.Context;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dipanjan.app.moviezone.adapter.GalleryAdapter;
import com.dipanjan.app.moviezone.adapter.MovieSeriesAdapter;
import com.dipanjan.app.moviezone.app.AppController;
import com.dipanjan.app.moviezone.bo.MovieDetailsBO;
import com.dipanjan.app.moviezone.helper.NetworkCheck;
import com.dipanjan.app.moviezone.model.DataModel;
import com.dipanjan.app.moviezone.model.MovieSeries;
import com.dipanjan.app.moviezone.util.Constant;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;

import info.dipanjan.app.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Integer URLIndexPosition=-1;

    private ArrayList<MovieSeries> movieSeries;
    ArrayList<DataModel> dataModelArrayList=null;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout,relativeLayoutForMessageText;
    private CoordinatorLayout coordinatorLayout;
    private TextView messageText;
    private MovieSeriesAdapter mAdapter;
    private RecyclerView recyclerView;

    public MainActivity() {
        dataModelArrayList = new ArrayList<DataModel>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_movie_activity);

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



    public void displayFetchedMovieItemAsList() {

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        mAdapter = new MovieSeriesAdapter(getApplicationContext(), movieSeries);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("images", movies);
                bundle.putInt("position", position);*/

                /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");*/

                Intent intent = new Intent(getApplicationContext(),ListMovieSeriesItems.class);
                intent.putExtra("MOVIESERIES", movieSeries.get(position));
                intent.putExtra("URLIndexPosition", URLIndexPosition);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

                //Toast.makeText(getApplicationContext(),"come here "+movieSeries.get(position).getMovieSeriesTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    public void callWebServiceTofetchMovieList(String url){

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //pDialog.hide();
                //movies.clear();
                movieSeries = MovieDetailsBO.loadMovieSeriesContents(response.toString(),URLIndexPosition);
                displayFetchedMovieItemAsList();


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

    public void startUpActivity(){
        if(isNetworkAvailable()){

            NetworkCheck networkCheck = (NetworkCheck) new NetworkCheck(new NetworkCheck.AsyncResponse() {
                @Override
                public Integer processFinish(Integer URLIndexPos) {
                    if(URLIndexPos!=1){
                        URLIndexPosition=URLIndexPos;
                        relativeLayoutForMessageText.setVisibility(View.GONE);
                        messageText.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        callWebServiceTofetchMovieList(Constant.JSON_URL);

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
                                callWebServiceTofetchMovieList(Constant.JSON_URL);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
            Intent intent = new Intent(getApplicationContext(),ListLikedMovieItems.class);
            intent.putExtra("URLIndexPosition", URLIndexPosition);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


interface DataFetchListener {
    void onDataFetchSuccessfull();

}