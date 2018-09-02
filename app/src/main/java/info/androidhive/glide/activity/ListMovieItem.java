package info.androidhive.glide.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.glide.R;
import info.androidhive.glide.adapter.GalleryAdapter;
import info.androidhive.glide.app.AppController;
import info.androidhive.glide.helper.Helper;
import info.androidhive.glide.model.Movie;


public class ListMovieItem extends AppCompatActivity {

    private String TAG = ListMovieItem.class.getSimpleName();
    //private static final String endpoint = "https://yts.am/api/v2/list_movies.json?page=";
    private static String endpoint = null;
    private ArrayList<Movie> movies;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private int pageCount=1;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_movie_activity);


        endpoint= resolveEntryPoint();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        relativeLayout = (RelativeLayout) findViewById(R.id.spinKitLayout);
        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);


        pDialog = new ProgressDialog(this);
        movies = new ArrayList<Movie>();
        mAdapter = new GalleryAdapter(getApplicationContext(), movies);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    //Toast.makeText(getApplicationContext(),"come here "+endpoint,Toast.LENGTH_LONG).show();
                    pageCount++;
                    fetchImages(endpoint+pageCount);

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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

                //Toast.makeText(getApplicationContext(),"come here "+movies.get(position).getId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchImages(endpoint+pageCount);


    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void fetchImages(String url) {

        //pDialog.setMessage("Fetching Movie...");
        //pDialog.show();


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
                    populateImage(response.toString(),movies);


                    mAdapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    //pDialog.hide();
                }
            });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

        public void populateImage (String stg, ArrayList<Movie> movies) {
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


        }

    private String resolveEntryPoint(){
        String category = getIntent().getStringExtra("CATEGORY");
        if(category!=null){
            return Helper.fetchMovieEndPaint(category);
        }else if(getIntent().getStringExtra("SEARCH")!=null){
            return getIntent().getStringExtra("SEARCH");
        }
        else{
            return Helper.fetchMovieEndPointByGerne(getIntent().getStringExtra("GERNE"));
        }
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