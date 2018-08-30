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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.androidhive.glide.R;
import info.androidhive.glide.adapter.RecyclerViewDataAdapter;
import info.androidhive.glide.app.AppController;
import info.androidhive.glide.model.Movie;
import info.androidhive.glide.model.SectionDataModel;
import info.androidhive.glide.util.Constant;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;


    ArrayList<SectionDataModel> allSampleData;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        relativeLayout = (RelativeLayout)findViewById(R.id.spinKitLayout);

        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);


        allSampleData = new ArrayList<SectionDataModel>();

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

    public void createDummyData() {


            SectionDataModel action = new SectionDataModel();
            action.setHeaderTitle("Action" );
            action.setMovieIdentifier("Action");
            action.setAllItemsInSection(actionMovieList);

            SectionDataModel horror = new SectionDataModel();
            horror.setHeaderTitle("Horror" );
            horror.setMovieIdentifier("Horror");
            horror.setAllItemsInSection(horrorMovieList);


            SectionDataModel romance = new SectionDataModel();
            romance.setHeaderTitle("Romance" );
            romance.setMovieIdentifier("Romance");
            romance.setAllItemsInSection(romanceMovieList);

            SectionDataModel biography = new SectionDataModel();
            biography.setHeaderTitle("Biography" );
            biography.setMovieIdentifier("Biography");
            biography.setAllItemsInSection(biographyMovieList);

            SectionDataModel documentary = new SectionDataModel();
            documentary.setHeaderTitle("Documentary" );
            documentary.setMovieIdentifier("Documentary");
            documentary.setAllItemsInSection(documentMovieList);

            SectionDataModel comedy = new SectionDataModel();
            comedy.setHeaderTitle("Comedy" );
            comedy.setMovieIdentifier("Comedy");
            comedy.setAllItemsInSection(comedyMovieList);

        SectionDataModel latestMovies = new SectionDataModel();
        latestMovies.setHeaderTitle("Latest Movies");
        latestMovies.setMovieIdentifier("all");
        latestMovies.setAllItemsInSection(latestMovieList);

        SectionDataModel bestMovies = new SectionDataModel();
        bestMovies.setHeaderTitle("Best Movies");
        bestMovies.setMovieIdentifier("8.5");
        bestMovies.setAllItemsInSection(bestMovieList);

        SectionDataModel movieGerne = new SectionDataModel();
        movieGerne.setHeaderTitle("Browse By Gerne");
        movieGerne.setMovieIdentifier(Constant.MovieCategory.MOVIE_GERNE);
        movieGerne.setAllItemsInSection(movieGenreList);


            allSampleData.add(latestMovies);
            allSampleData.add(bestMovies);
            allSampleData.add(movieGerne);
            allSampleData.add(action);
            allSampleData.add(horror);
            allSampleData.add(biography);
            allSampleData.add(documentary);
            allSampleData.add(romance);
            allSampleData.add(comedy);

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(false);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(),"On Pause "+dialogFragment,Toast.LENGTH_SHORT).show();
        if(dialogFragment!=null){
            dialogFragment.dismiss();
        }
    }

    ArrayList<Movie> actionMovieList = new ArrayList<>();
    ArrayList<Movie> horrorMovieList = new ArrayList<>();
    ArrayList<Movie> comedyMovieList = new ArrayList<>();
    ArrayList<Movie> romanceMovieList = new ArrayList<>();
    ArrayList<Movie> documentMovieList = new ArrayList<>();
    ArrayList<Movie> biographyMovieList = new ArrayList<>();
    ArrayList<Movie> latestMovieList = new ArrayList<>();
    ArrayList<Movie> bestMovieList = new ArrayList<>();
    ArrayList<Movie> movieGenreList = new ArrayList<>();
    private final Object lock = new Object();
    int flag = 0;
    public void makeCount() {

        synchronized (lock) {
            flag++;

            if(flag==Constant.MovieCategory.CATEGORY_TOTAL_COUNT){

                createDummyData();
            }
        }
    }

    private void populateMovieData(){


        fetchMovieData(Constant.RowDisplay.BIOGRAPHY_MOVIE_URL);
        fetchMovieData(Constant.RowDisplay.ACTION_MOVIE_URL);
        fetchMovieData(Constant.RowDisplay.HORROR_MOVIE_URL);
        fetchMovieData(Constant.RowDisplay.COMEDY_MOVIE_URL);
        fetchMovieData(Constant.RowDisplay.ROMANCE_MOVIE_URL);
        fetchMovieData(Constant.RowDisplay.DOCUMENTARY_MOVIE_URL);
        fetchMovieData(Constant.RowDisplay.LATEST_MOVIE);
        fetchMovieData(Constant.RowDisplay.BEST_MOVIES);
        populateMovieGerne();

    }


    private void populateMovieGerne(){
        List<String> movieGerneList = Arrays.asList(getResources().getStringArray(R.array.genre_array));
        for(String movieGerne:movieGerneList){
            Movie movie=new Movie();
            movie.setTitle(movieGerne);
            movie.setId(movieGerne.toLowerCase());
            movie.setMediumCoverImage(null);
            movieGenreList.add(movie);
        }
        makeCount();
    }


    public ArrayList<Movie> populateMovieList (String stg, ArrayList<Movie> movies,DataFetchListener dataFetchListener) {
        try {
           // Toast.makeText(getApplicationContext(),stg,Toast.LENGTH_SHORT).show();
            JSONObject jObj = new JSONObject(stg);
            String city = jObj.getString("status_message");
            System.out.println(city);

            JSONObject jObj1 = jObj.getJSONObject("data");
            JSONArray jArr = jObj1.getJSONArray("movies");
            for (int i=0; i < jArr.length(); i++) {
                Movie movie=new Movie();
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


    private int fetchMovieData(final String url){
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //String genre1 = url.substring(url.indexOf("genre")+6,url.lastIndexOf("&"));
                Uri uri = Uri.parse(url);
                String genre = uri.getQueryParameter("genre");
                String minimum_rating = uri.getQueryParameter("minimum_rating");
                if(genre!=null){
                    if(genre.equalsIgnoreCase(Constant.MovieCategory.BIOGRAPHY)){
                        biographyMovieList=populateMovieList(response.toString(), biographyMovieList, new DataFetchListener() {
                            @Override
                            public void onDataFetchSuccessfull() {
                                makeCount();
                            }
                        });


                    }
                }
                if(genre!=null) {
                    if (genre.equalsIgnoreCase(Constant.MovieCategory.ACTION)) {
                        actionMovieList = populateMovieList(response.toString(), actionMovieList, new DataFetchListener() {
                            @Override
                            public void onDataFetchSuccessfull() {
                                makeCount();
                            }
                        });

                    }
                }
                if(genre!=null) {
                    if (genre.equalsIgnoreCase(Constant.MovieCategory.COMEDY)) {
                        comedyMovieList = populateMovieList(response.toString(), comedyMovieList, new DataFetchListener() {
                            @Override
                            public void onDataFetchSuccessfull() {
                                makeCount();
                            }
                        });

                    }
                }
                if(genre!=null) {
                    if (genre.equalsIgnoreCase(Constant.MovieCategory.HORROR)) {
                        horrorMovieList = populateMovieList(response.toString(), horrorMovieList, new DataFetchListener() {
                            @Override
                            public void onDataFetchSuccessfull() {
                                makeCount();
                            }
                        });

                    }
                }
                if(genre!=null) {
                    if (genre.equalsIgnoreCase(Constant.MovieCategory.ROMANCE)) {
                        romanceMovieList = populateMovieList(response.toString(), romanceMovieList, new DataFetchListener() {
                            @Override
                            public void onDataFetchSuccessfull() {
                                makeCount();
                            }
                        });

                    }
                }
                if(genre!=null) {
                    if (genre.equalsIgnoreCase(Constant.MovieCategory.DOCUMENTARY)) {
                        documentMovieList = populateMovieList(response.toString(), documentMovieList, new DataFetchListener() {
                            @Override
                            public void onDataFetchSuccessfull() {
                                makeCount();
                            }
                        });

                    }
                }
                if(genre!=null) {
                    if (genre.equalsIgnoreCase("all")) {
                        latestMovieList = populateMovieList(response.toString(), latestMovieList, new DataFetchListener() {
                            @Override
                            public void onDataFetchSuccessfull() {
                                makeCount();
                            }
                        });

                    }
                }
                if(minimum_rating!=null){
                    if(minimum_rating.equalsIgnoreCase(Constant.MovieCategory.BEST_MOVIES_RATING_ABOVE)){
                        bestMovieList=populateMovieList(response.toString(), bestMovieList, new DataFetchListener() {
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



            }
        });

        AppController.getInstance().addToRequestQueue(strReq);
        return 1;
    }
    DialogFragment dialogFragment;
    private void openDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment = new SearchDialogFragment();
        dialogFragment.show(ft, "dialog");

    }

    ////Sample code
	private void initialisedListItems(){
		ArrayList<DataModel> dataModelList = new ArrayList<DataModel>();
		for(int index=0;index<Constant.IDENTIFIER_LIST.length;index++){
			DataModel dataModel=new DataModel(Constant.URL_LINK[index], Constant.HEADER_LIST[index], Constant.IDENTIFIER_LIST[index],Constant.QUERY_PARAMETER[index]);
			SectionDataModel sectionDataModel = new SectionDataModel();
			sectionDataModel.setHeaderTitle(Constant.HEADER_LIST[index]);
			sectionDataModel.setMovieIdentifier(Constant.IDENTIFIER_LIST[index]);
			sectionDataModel.setAllItemsInSection(new ArrayList<Movie>());
			dataModel.setSectionDataModel(sectionDataModel);
			dataModelList.add(dataModel);
		}
	}


}

interface DataFetchListener{
    void onDataFetchSuccessfull();

}


public class DataModel {

	private SectionDataModel sectionDataModel;
	private SingleItemModel singleItemModel;
	private String urlLink;
	private String category;
	private String identifier;
	private String queryParameter;
	public DataModel(String urlLink, String category, String identifier,String queryParameter) {
		super();
		this.urlLink = urlLink;
		this.category = category;
		this.identifier = identifier;
		this.queryParameter = queryParameter;
	}
	
	
	public String getQueryParameter() {
		return queryParameter;
	}


	public void setQueryParameter(String queryParameter) {
		this.queryParameter = queryParameter;
	}


	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public SectionDataModel getSectionDataModel() {
		return sectionDataModel;
	}
	public void setSectionDataModel(SectionDataModel sectionDataModel) {
		this.sectionDataModel = sectionDataModel;
	}
	public SingleItemModel getSingleItemModel() {
		return singleItemModel;
	}
	public void setSingleItemModel(SingleItemModel singleItemModel) {
		this.singleItemModel = singleItemModel;
	}
	public String getUrlLink() {
		return urlLink;
	}
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}

	public static final String[] IDENTIFIER_LIST = { "Latest", "Best" };
	public static final String[] QUERY_PARAMETER = { "gerbe", "gerbe" };
	public static final String[] HEADER_LIST = { "HLatest", "HBest" };
	public static final String[] URL_LINK = { "LINKLatest", "LINKBest" };



