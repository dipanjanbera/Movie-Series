package info.androidhive.glide.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.androidhive.glide.R;
import info.androidhive.glide.adapter.RecyclerViewDataAdapter;
import info.androidhive.glide.app.AppController;
import info.androidhive.glide.model.DataModel;
import info.androidhive.glide.model.Movie;
import info.androidhive.glide.model.SectionDataModel;
import info.androidhive.glide.util.Constant;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;


    ArrayList<DataModel> dataModelArrayList;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

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

        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }


        populateMovieData();


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

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, dataModelArrayList);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(),"On Pause "+dialogFragment,Toast.LENGTH_SHORT).show();
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }


    private final Object lock = new Object();
    int flag = 0;

    public void makeCount() {

        synchronized (lock) {
            flag++;

            if (flag == Constant.MovieCategory.CATEGORY_TOTAL_COUNT) {

                displayFetchedMovieItemAsList();
            }
        }
    }

    private void populateMovieData() {

        for (DataModel dataModel : initialisedListItems()) {
            if (dataModel != null) {
                fetchMovieData(dataModel);
            }
        }

        populateMovieGerne();

    }


    private void populateMovieGerne() {
        DataModel dataModel = new DataModel();
        List<String> movieGerneList = Arrays.asList(getResources().getStringArray(R.array.genre_array));
        for (String movieGerne : movieGerneList) {
            Movie movie = new Movie();
            movie.setTitle(movieGerne);
            movie.setId(movieGerne.toLowerCase());
            movie.setMediumCoverImage(null);
            dataModel.getSectionDataModel().getAllItemsInSection().add(movie);
        }
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

                movies.add(movie);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        dataFetchListener.onDataFetchSuccessfull();
        return movies;
    }


    private ArrayList<DataModel> initialisedListItems() {
        ArrayList<DataModel> dataModelList = new ArrayList<DataModel>();
        for (int index = 0; index < Constant.IDENTIFIER_LIST.length; index++) {
            DataModel dataModel = new DataModel(Constant.URL_LINK[index], Constant.HEADER_LIST[index], Constant.IDENTIFIER_LIST[index], Constant.QUERY_PARAMETER[index]);
            SectionDataModel sectionDataModel = new SectionDataModel();
            sectionDataModel.setHeaderTitle(Constant.HEADER_LIST[index]);
            sectionDataModel.setMovieIdentifier(Constant.IDENTIFIER_LIST[index]);
            sectionDataModel.setAllItemsInSection(new ArrayList<Movie>());
            dataModel.setSectionDataModel(sectionDataModel);
            dataModelList.add(dataModel);

        }
        return dataModelList;
    }


    private int fetchMovieData(final DataModel dataModel) {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                dataModel.getUrlLink(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
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


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("ERROR", error.getMessage());

            }
        });

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


}

interface DataFetchListener {
    void onDataFetchSuccessfull();

}





