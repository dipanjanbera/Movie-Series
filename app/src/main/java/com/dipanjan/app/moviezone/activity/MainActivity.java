package com.dipanjan.app.moviezone.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.dipanjan.app.moviezone.adapter.RecyclerViewDataAdapter;
import com.dipanjan.app.moviezone.helper.NetworkCheck;
import com.dipanjan.app.moviezone.model.Movie;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.dipanjan.app.R;

import com.dipanjan.app.moviezone.app.AppController;
import com.dipanjan.app.moviezone.helper.Helper;
import com.dipanjan.app.moviezone.model.DataModel;
import com.dipanjan.app.moviezone.model.SectionDataModel;
import com.dipanjan.app.moviezone.util.Constant;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Integer URLIndexPosition=-1;


    ArrayList<DataModel> dataModelArrayList=null;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout,relativeLayoutForMessageText;
    private CoordinatorLayout coordinatorLayout;
    private TextView messageText;

    public MainActivity() {
        dataModelArrayList = new ArrayList<DataModel>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        relativeLayout = (RelativeLayout) findViewById(R.id.spinKitLayout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        relativeLayoutForMessageText = (RelativeLayout)findViewById(R.id.messageLayout);
        messageText = (TextView)findViewById(R.id.messageText);

        relativeLayoutForMessageText.setVisibility(View.GONE);
        messageText.setVisibility(View.GONE);

        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        toolbar.setTitleTextAppearance(this,R.style.CodeFont_Movie_Details_Headers);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }


        startUpActivity();



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

                                URLIndexPosition = URLIndexPos;
                                relativeLayoutForMessageText.setVisibility(View.GONE);
                                messageText.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                populateMovieData(URLIndexPos);
                            }else{
                                relativeLayout.setVisibility(View.GONE);
                                relativeLayoutForMessageText.setVisibility(View.VISIBLE);
                                messageText.setVisibility(View.VISIBLE);
                                messageText.setText(NetworkCheck.DISPLAY_MSG_IF_HOST_NOT_RESOLVE);
                                displayNetworkInfoAlert(coordinatorLayout, NetworkCheck.DISPLAY_SNACBAR_MSG_IF_HOST_NOT_RESOLVE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
                            }
                            return null;
                        }
                    });
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



    private void startUpActivity(){

        if(isNetworkAvailable()){

            NetworkCheck networkCheck = (NetworkCheck) new NetworkCheck(new NetworkCheck.AsyncResponse() {
                @Override
                public Integer processFinish(Integer URLIndexPos) {
                    if(URLIndexPos!=-1){
                        //Toast.makeText(getApplicationContext(),URLIndexPos+"---"+Constant.BASE_URL[URLIndexPos],Toast.LENGTH_SHORT).show();
                        URLIndexPosition=URLIndexPos;
                        relativeLayoutForMessageText.setVisibility(View.GONE);
                        messageText.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        populateMovieData(URLIndexPos);
                    }else{
                        relativeLayout.setVisibility(View.GONE);
                        relativeLayoutForMessageText.setVisibility(View.VISIBLE);
                        messageText.setVisibility(View.VISIBLE);
                        messageText.setText(NetworkCheck.DISPLAY_MSG_IF_HOST_NOT_RESOLVE);
                        displayNetworkInfoAlert(coordinatorLayout, NetworkCheck.DISPLAY_SNACBAR_MSG_IF_HOST_NOT_RESOLVE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
                    }
                    return null;
                }
            });


        }else {
            progressBar.setVisibility(View.GONE);
            displayNetworkInfoAlert(coordinatorLayout, Constant.MESSAGE_NETWORK_NOT_AVIALABLE, Constant.SNACKBAR_DISPALY_MODE_FAILURE);
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        //startUpActivity();
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
            openDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayFetchedMovieItemAsList() {

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(false);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, dataModelArrayList,URLIndexPosition);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }


    private final Object lock = new Object();
    int flag = 0;

    public void makeCount() {

        synchronized (lock) {
            flag++;

            if (flag == Constant.IDENTIFIER_LIST.length+1) {

                displayFetchedMovieItemAsList();
            }
        }
    }

    private void populateMovieData(Integer URLIndexPos) {
        Log.d("CALL","Y");
        initialisedListItems(URLIndexPos);
        for (DataModel dataModel : dataModelArrayList) {
            if (dataModel != null) {
                fetchMovieData(dataModel);
            }
        }

        populateMovieGerne(URLIndexPos);

    }


    private void populateMovieGerne(Integer URLIndexPos) {
        DataModel dataModel = new DataModel();
        SectionDataModel sectionDataModel = new SectionDataModel();
        sectionDataModel.setHeaderTitle("Browse By Genre");
        sectionDataModel.setMovieIdentifier(Constant.MovieCategory.MOVIE_GERNE);
        dataModel.setSectionDataModel(sectionDataModel);
        List<String> movieGerneList = Arrays.asList(getResources().getStringArray(R.array.genre_array_recycle_view));
        ArrayList<Movie> movieGenreList = new ArrayList<Movie>();
        for (String movieGerne : movieGerneList) {
            if(movieGenreList.size()!= Constant.MAX_ITEM_EACH_ROW) {
                Movie movie = new Movie();
                movie.setTitle(movieGerne);
                movie.setId(movieGerne.toLowerCase());
                movie.setMediumCoverImage(null);
                movie.setCategoryDescriptorTab(true);
                movieGenreList.add(movie);
            }else{
                break;
            }
        }
        dataModel.getSectionDataModel().setAllItemsInSection(movieGenreList);
        dataModelArrayList.add(2,dataModel);
        makeCount();
    }


    public ArrayList<Movie> populateMovieList(String stg, ArrayList<Movie> movies, DataFetchListener dataFetchListener) {
        try {
            // Toast.makeText(getApplicationContext(),stg,Toast.LENGTH_SHORT).show();
            JSONObject jObj = new JSONObject(stg);
            String city = jObj.getString("status_message");
            System.out.println(city);

            JSONObject jObj1 = jObj.getJSONObject("data");
            JSONArray jArr = jObj1.getJSONArray("movies");
            for (int i = 0; i < jArr.length(); i++) {
                Movie movie = new Movie();
                JSONObject obj = jArr.getJSONObject(i);
                movie.setMediumCoverImage(obj.getString("large_cover_image"));
                movie.setTitle(obj.getString("title"));
                movie.setRating(obj.getString("rating"));
                movie.setYear(obj.getString("year"));
                movie.setRuntime(obj.getString("runtime"));
                movie.setId(obj.getString("id"));
                movie.setCategoryDescriptorTab(false);

                movies.add(movie);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        dataFetchListener.onDataFetchSuccessfull();
        return movies;
    }


    private ArrayList<DataModel> initialisedListItems(Integer URLIndexPos) {

        for (int index = 0; index < Constant.IDENTIFIER_LIST.length; index++) {
            ArrayList<Movie> movieArr = new ArrayList<Movie>();
            DataModel dataModel = new DataModel(Helper.generateURL(URLIndexPos,Constant.URL_LINK[index]), Constant.IDENTIFIER_LIST[index], Constant.IDENTIFIER_LIST[index], Constant.QUERY_PARAMETER[index], Helper.getFullHeaderName(Constant.HEADER_LIST[index]));
            SectionDataModel sectionDataModel = new SectionDataModel();
            sectionDataModel.setHeaderTitle(Helper.getFullHeaderName(Constant.HEADER_LIST[index]));
            sectionDataModel.setMovieIdentifier(Constant.IDENTIFIER_LIST[index]);
            sectionDataModel.setAllItemsInSection(movieArr);
            dataModel.setSectionDataModel(sectionDataModel);
            dataModelArrayList.add(dataModel);

        }
        return dataModelArrayList;
    }


    private int fetchMovieData(final DataModel dataModel) {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                dataModel.getUrlLink(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    Uri uri = Uri.parse(dataModel.getUrlLink());
                    String queryParameter = uri.getQueryParameter(dataModel.getQueryParameter());
                    if (queryParameter != null) {
                        if (queryParameter.equalsIgnoreCase(dataModel.getCategory())) {
                            populateMovieList(response.toString(), dataModel.getSectionDataModel().getAllItemsInSection(), new DataFetchListener() {
                                @Override
                                public void onDataFetchSuccessfull() {
                                    makeCount();
                                }
                            });


                        }
                    }

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    ex.printStackTrace();
                }


            }
        });

        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq);
        return 1;
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


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}


interface DataFetchListener {
    void onDataFetchSuccessfull();

}