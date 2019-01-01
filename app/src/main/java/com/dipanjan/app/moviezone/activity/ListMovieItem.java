package com.dipanjan.app.moviezone.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
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
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dipanjan.app.moviezone.adapter.GalleryAdapter;
import com.dipanjan.app.moviezone.helper.NetworkCheck;
import com.dipanjan.app.moviezone.model.Movie;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.dipanjan.app.R;

import com.dipanjan.app.moviezone.app.AppController;
import com.dipanjan.app.moviezone.helper.Helper;
import com.dipanjan.app.moviezone.util.Constant;


public class ListMovieItem extends AppCompatActivity {

    private String TAG = ListMovieItem.class.getSimpleName();
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



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_movie_activity);
        URLIndexPosition=-1;

        endpoint= resolveEntryPoint();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this,R.style.CodeFont_Movie_Details_Headers);
        if(resolveActivityName()!=null){
            getSupportActionBar().setTitle(resolveActivityName());
        }


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


        //relativeLayoutBottomPanel.setVisibility(View.GONE);
        // progressBarBottomPanel.setVisibility(View.GONE);

        pDialog = new ProgressDialog(this);
        movies = new ArrayList<Movie>();
        mAdapter = new GalleryAdapter(getApplicationContext(), movies);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    //           Toast.makeText(getApplicationContext(),""+movieCount,Toast.LENGTH_SHORT).show();

                    final Snackbar snackBar = Snackbar.make(coordinatorLayout, "Fetching movie list...", Snackbar.LENGTH_INDEFINITE);
                    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackBar.getView();
                    layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    pageCount++;
                    if(movieCount>(pageCount*15)){

                        snackBar.show();
                    }




                    fetchImages(Helper.generateURL(URLIndexPosition,endpoint) + pageCount, new ListFetchListerner() {
                        @Override
                        public void onSuccessFullFetch() {
                            snackBar.dismiss();
                        }
                    });

                }
            }
        });




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


        startUpActivity();


    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void fetchImages(String url, final ListFetchListerner listFetchListerner) {

        //pDialog.setMessage("Fetching Movie...");
        //pDialog.show();

        Log.d("@@@@@@@@@@@@ URl",""+URLIndexPosition);

        // Tag used to cancel the request
        String  tag_string_req = "string_req";



        //final ProgressDialog pDialog = new ProgressDialog(this);
        //pDialog.setMessage("Loading...");wa
        //pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //pDialog.hide();
                //movies.clear();
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);
                populateMovieObject(response.toString(),movies,listFetchListerner);
                mAdapter.notifyDataSetChanged();

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
                retryNetworkConnection();
                snackBar.dismiss();
            }
        });

        snackBar.show();
    }

    private void retryNetworkConnection(){
        if(isNetworkAvailable()){
            NetworkCheck networkCheck = (NetworkCheck) new NetworkCheck(new NetworkCheck.AsyncResponse() {
                @Override
                public Integer processFinish(Integer URLIndexPos) {
                    if(URLIndexPos!=-1){
                        URLIndexPosition=URLIndexPos;
                        relativeLayoutForMessageText.setVisibility(View.GONE);
                        messageText.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        fetchImages(Helper.generateURL(URLIndexPos,endpoint) + pageCount, new ListFetchListerner() {
                            @Override
                            public void onSuccessFullFetch() {
                                //  Toast.makeText(getApplicationContext(),"come dddd "+movieCount,Toast.LENGTH_SHORT).show();
                                if(getIntent().getStringExtra("SEARCH")!=null){
                                    if(movieCount>1){
                                        getSupportActionBar().setTitle(movieCount+" Movies found");
                                    }else{
                                        getSupportActionBar().setTitle(movieCount+" Movie found");
                                    }

                                }
                            }
                        });
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

            relativeLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
            displayNetworkInfoAlert(coordinatorLayout, Constant.MESSAGE_NETWORK_NOT_AVIALABLE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private int retryConnectionCount=0;
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
                        fetchImages(Helper.generateURL(URLIndexPos,endpoint) + pageCount, new ListFetchListerner() {
                            @Override
                            public void onSuccessFullFetch() {
                                //  Toast.makeText(getApplicationContext(),"come dddd "+movieCount,Toast.LENGTH_SHORT).show();
                                if(getIntent().getStringExtra("SEARCH")!=null){
                                    if(movieCount>1){
                                        getSupportActionBar().setTitle(movieCount+" Movies found");
                                    }else{
                                        getSupportActionBar().setTitle(movieCount+" Movie found");
                                    }

                                }
                            }
                        });

                    }else{
                        if(retryConnectionCount==0){
                            retryNetworkConnection();
                            retryConnectionCount++;
                        }
                        /*relativeLayout.setVisibility(View.GONE);
                        relativeLayoutForMessageText.setVisibility(View.VISIBLE);
                        messageText.setVisibility(View.VISIBLE);
                        messageText.setText(NetworkCheck.DISPLAY_MSG_IF_HOST_NOT_RESOLVE);
                        displayNetworkInfoAlert(coordinatorLayout, NetworkCheck.DISPLAY_SNACBAR_MSG_IF_HOST_NOT_RESOLVE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);*/
                    }
                    return null;
                }
            }).execute();


        }else {
            progressBar.setVisibility(View.GONE);
            displayNetworkInfoAlert(coordinatorLayout, Constant.MESSAGE_NETWORK_NOT_AVIALABLE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
        }
    }




    public void populateMovieObject (String stg, ArrayList<Movie> movies, ListFetchListerner listFetchListerner) {
        // Toast.makeText(this.getApplicationContext(),"come here ",Toast.LENGTH_SHORT).show();
        String constructGenreString = null;
        try {
            //Toast.makeText(this.getApplicationContext(),""+stg,Toast.LENGTH_SHORT).show();
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

            listFetchListerner.onSuccessFullFetch();


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private String resolveActivityName(){
        String activityName = getIntent().getStringExtra(Constant.ACTIVITY_NAME);
        return activityName;
    }


    private String resolveEntryPoint(){
        String category = getIntent().getStringExtra("CATEGORY");

        if(category!=null){
            return Helper.fetchMovieEndPoint(category);
        }else if(getIntent().getStringExtra("SEARCH")!=null){
            return getIntent().getStringExtra("SEARCH");
        }
        else {
            return Helper.fetchMovieEndPointByGerne(getIntent().getStringExtra("GERNE"));
        }
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
        if (id == R.id.fav) {
            Intent intent = new Intent(getApplicationContext(),ListLikedMovieItems.class);
            intent.putExtra("URLIndexPosition", URLIndexPosition);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
            return true;
        }
        if (id == R.id.search_movies) {
            openDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    DialogFragment dialogFragment;

    private void openDialog() {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment = new SearchDialogFragment();
        dialogFragment.show(ft, "dialog");
    }




    @Override
    protected void onPause() {
        super.onPause();
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // Toast.makeText(getApplicationContext(),"hj",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }







}

interface ListFetchListerner{

    void onSuccessFullFetch();
}