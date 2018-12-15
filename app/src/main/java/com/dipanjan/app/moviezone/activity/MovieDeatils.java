package com.dipanjan.app.moviezone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dipanjan.app.moviezone.adapter.CastAdapter;
import com.dipanjan.app.moviezone.adapter.SectionListDataAdapter;
import com.dipanjan.app.moviezone.bo.MovieDetailsBO;
import com.dipanjan.app.moviezone.helper.Helper;
import com.dipanjan.app.moviezone.helper.NetworkCheck;
import com.dipanjan.app.moviezone.model.Cast;
import com.dipanjan.app.moviezone.model.Movie;
import com.dipanjan.app.moviezone.model.Torrent;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import info.dipanjan.app.R;

import com.dipanjan.app.moviezone.adapter.DownloadMovieAdapter;
import com.dipanjan.app.moviezone.app.AppController;
import com.dipanjan.app.moviezone.helper.FileDownloder;
import com.dipanjan.app.moviezone.util.Constant;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by LENOVO on 16-06-2018.
 */

//Created By Dipanjan

public class MovieDeatils extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView backgroundImageView;
    private TextView imdbRating,likeCount,year,mpaRating,movieHeader,pgRating,runtime,description,genres;
    private Button watchTrailer;
    private Button imdblink,movieSubtitle;
    private ProgressDialog pDialog;
    private String URL_END_POINT = Constant.StaticUrls.MOVIE_DETAILS;
    private String URL_END_POINT_FOR_SIMILAR_MOVIES = Constant.StaticUrls.SIMILAR_MOVIE_DETAILS;
    private RecyclerView recycleView,recyclerViewForSimilarMovies,downloadRecycleView;
    private CastAdapter castAdapter;
    private DownloadMovieAdapter downloadMovieAdapter;
    private SectionListDataAdapter itemListDataAdapter;
    private List<Cast> castArr = new ArrayList<>();
    private List<Torrent> torrentArr = new ArrayList<>();
    private ArrayList<Movie> movieArr = new ArrayList<>();
    private String movieTrailerCode = null;
    private String IMDB_CODE_MOVIE=null;
    private LinearLayout castLinearLayout,castLinearLayoutHeader;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout,spinkit_relative_layout;
    private LinearLayout linearLayout;
    private WebView webView;
    private ProgressBar progressBarForTopImage;
    private SharedPreferences sharedpreferences;
    private static CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_details_view);



        backgroundImageView = (ImageView) findViewById(R.id.imageView3);
        movieHeader = (TextView) findViewById(R.id.textView);
        description = (TextView) findViewById(R.id.textView4);
        imdbRating = (TextView) findViewById(R.id.textView5);
        likeCount = (TextView) findViewById(R.id.textView6);
        pgRating = (TextView) findViewById(R.id.textView7);
        year = (TextView) findViewById(R.id.textView10);
        genres = (TextView) findViewById(R.id.textView8);
        runtime = (TextView) findViewById(R.id.textView9);
        recycleView = (RecyclerView) findViewById(R.id.recycler_view);
        watchTrailer = (Button) findViewById(R.id.watchTrailer);
        imdblink = (Button) findViewById(R.id.imbdLink);
        movieSubtitle = (Button) findViewById(R.id.movie_subtitle);
        castLinearLayout = (LinearLayout) findViewById(R.id.castLayout);
        castLinearLayoutHeader = (LinearLayout) findViewById(R.id.castLayoutHeader);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        progressBarForTopImage = (ProgressBar) findViewById(R.id.spin_kit_top_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.spinKitLayout);
        spinkit_relative_layout = (RelativeLayout) findViewById(R.id.spinkit_relative_layout);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        pDialog = new ProgressDialog(this);
        webView= new WebView(getApplicationContext());

        if(toolbar!=null){
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setTitle("");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resolveBackButtonAction();
                }
            });

        }

        ThreeBounce threeBounce = new ThreeBounce();
        ThreeBounce threeBounceTransition = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounceTransition);
        progressBarForTopImage.setIndeterminateDrawable(threeBounce);
        linearLayout.setVisibility(View.GONE);
        recyclerViewForSimilarMovies=(RecyclerView) findViewById(R.id.similar_movies_recycler_view);
          downloadRecycleView = (RecyclerView) findViewById(R.id.download_recycler_view);

        castAdapter = new CastAdapter(getApplicationContext(),castArr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setAdapter(castAdapter);


        downloadMovieAdapter = new DownloadMovieAdapter(getApplicationContext(),torrentArr,coordinatorLayout);
        RecyclerView.LayoutManager mLayoutManagerForDownload = new LinearLayoutManager(getApplicationContext());
        downloadRecycleView.setLayoutManager(mLayoutManagerForDownload);
        downloadRecycleView.setItemAnimator(new DefaultItemAnimator());
        downloadRecycleView.setNestedScrollingEnabled(false);
        downloadRecycleView.setAdapter(downloadMovieAdapter);

       /* downloadRecycleView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), downloadRecycleView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Toast.makeText(getApplicationContext(),"Please click on Magnet or Download Icon ",Toast.LENGTH_SHORT).show();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/


        itemListDataAdapter = new SectionListDataAdapter(getApplicationContext(), movieArr,getURLIndexPosition());

        recyclerViewForSimilarMovies.setHasFixedSize(false);
        recyclerViewForSimilarMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewForSimilarMovies.setNestedScrollingEnabled(false);
        recyclerViewForSimilarMovies.setAdapter(itemListDataAdapter);



       // Toast.makeText(getApplicationContext(),"come here "+URL_END_POINT+getMovieIDFromActivity(),Toast.LENGTH_SHORT).show();
        fetchMovieDetails(Helper.generateURL(getURLIndexPosition(),URL_END_POINT)+getMovieIDFromActivity());
        fetchSimilarMovieDetails(Helper.generateURL(getURLIndexPosition(),URL_END_POINT_FOR_SIMILAR_MOVIES)+getMovieIDFromActivity());

        imdblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IMDB_CODE_MOVIE != null) {
                    new FinestWebView.Builder(getApplicationContext()).theme(R.style.FinestWebViewTheme)
                            .titleDefault("Loading..")
                            .toolbarScrollFlags(0)
                            .statusBarColorRes(R.color.blackPrimaryDark)
                            .toolbarColorRes(R.color.blackPrimary)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.blackPrimaryLight)
                            .iconDefaultColorRes(R.color.finestWhite)
                            .progressBarColorRes(R.color.finestWhite)
                            .swipeRefreshColorRes(R.color.blackPrimaryDark)
                            .menuSelector(R.drawable.selector_light_theme)
                            .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                            .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                            .dividerHeight(0)
                            .gradientDivider(false)
                            //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                            .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                                    R.anim.slide_right_out)
                            //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)

                            .show("https://www.imdb.com/title/" + IMDB_CODE_MOVIE);
                }

            }
        });
        watchTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieTrailerCode!=null){
                    //Toast.makeText(getApplicationContext(),"come here "+movieTrailerCode,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),YoutubeVideoPlayer.class);
                    intent.putExtra("VIDEOID", movieTrailerCode);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);

                }
            }
        });

        //fetchMovieDetails(URL_END_POINT+"3304");
        //fetchSimilarMovieDetails(URL_END_POINT_FOR_SIMILAR_MOVIES+"3304");

        sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);





    }

    private void resolveBackButtonAction() {
        finish();

    }

    private String getMovieIDFromActivity(){
       return getIntent().getStringExtra("MOVIEID");
    }

    private Integer getURLIndexPosition(){
        return getIntent().getIntExtra("URLIndexPosition",-1);
    }

    private void setMovieDetails(String response){
        Movie movie = MovieDetailsBO.fetchSingleMovieItemDetails(response);
        setMovieDetailsToView(movie);
    }


    private ArrayList<Cast> getCastListForMovie(Movie movie){
        if((movie!=null)&&(movie.getCastArr().size()>0)){
            return movie.getCastArr();
        }
        return null;
    }

    private String setRepleceStringForNullValue(String stgToBrCheck,String replacedBy){

        if(stgToBrCheck==null || stgToBrCheck.equals("")){
            return replacedBy;
        }
        return stgToBrCheck;
    }

    private void setMovieDetailsToView(final Movie movie){
        if(movie!=null){
            progressBar.setVisibility(View.GONE);
           // progressBarForTopImage.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            movieTrailerCode = movie.getYtTrailerCode();
            movieHeader.setText(movie.getTitle());
            imdbRating.setText(setRepleceStringForNullValue(movie.getRating(),"-"));
            likeCount.setText(setRepleceStringForNullValue(movie.getLikeCount(),"-"));
            pgRating.setText(setRepleceStringForNullValue(movie.getMpaRating(),"-"));
            year.setText(setRepleceStringForNullValue(movie.getYear(),"-"));
            runtime.setText(setRepleceStringForNullValue(movie.getRuntime(),"-")+" m");
            IMDB_CODE_MOVIE=movie.getImdbCode();
            Log.d("QWE",movie.getImdbCode());
            description.setText(movie.getDescriptionFull());
            genres.setText(movie.getGenres());
            RequestOptions myOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop();
            Glide.with(getApplicationContext()).load(movie.getBackgroundImage())
                    .transition(withCrossFade(700))
                    .thumbnail(1f)
                    .apply(myOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            progressBarForTopImage.setVisibility(View.GONE);
                            spinkit_relative_layout.setVisibility(View.GONE);
                            backgroundImageView.setVisibility(View.VISIBLE);
                            return false;
                        }


                    })
                    .into(backgroundImageView);

            if(movie.getCastArr()!=null && movie.getCastArr().size()>0){
                for(Cast cast:movie.getCastArr()){
                    castArr.add(cast);

                    castAdapter.notifyDataSetChanged();
                }
            }else{
                castLinearLayout.setVisibility(LinearLayout.GONE);
                castLinearLayoutHeader.setVisibility(LinearLayout.GONE);



            }



           for(Torrent torrent:movie.getTorrentArr()){
                torrent.setMovieTitle(movie.getTitle());
                torrentArr.add(torrent);

                downloadMovieAdapter.notifyDataSetChanged();
            }

            movieSubtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    /*new FinestWebView.Builder(getApplicationContext()).theme(R.style.FinestWebViewTheme)
                            .titleDefault("Loading..")
                            .toolbarScrollFlags(0)
                            .statusBarColorRes(R.color.blackPrimaryDark)
                            .toolbarColorRes(R.color.blackPrimary)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.blackPrimaryLight)
                            .iconDefaultColorRes(R.color.finestWhite)
                            .progressBarColorRes(R.color.finestWhite)
                            .swipeRefreshColorRes(R.color.blackPrimaryDark)
                            .menuSelector(R.drawable.selector_light_theme)
                            .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                            .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                            .dividerHeight(0)
                            .gradientDivider(false)
                            //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                            .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                                    R.anim.slide_right_out)
                            //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)

                            .show("http://www.yifysubtitles.com/movie-imdb/" + movie.getImdbCode());*/

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.SUBTITLE_URL+movie.getImdbCode()));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(browserIntent);
                }

            });
        }

    }

    private void fetchMovieDetails(String url) {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                pDialog.hide();
              //  Toast.makeText(getApplicationContext(),"come here "+response.toString(),Toast.LENGTH_SHORT).show();

                setMovieDetails(response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void fetchSimilarMovieDetails(String url) {

        //pDialog.setMessage("Fetching siililar Movie...");
        //pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
               // pDialog.hide();
                //  Toast.makeText(getApplicationContext(),"come here "+response.toString(),Toast.LENGTH_SHORT).show();

                MovieDetailsBO.populateMovieDetailsForSimilarMovies(response.toString(),movieArr);
                itemListDataAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

               // pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }





    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String content = FileDownloder.downLoadFromUrl(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Log.d("QWERTY",result);
            intent.setDataAndType(Uri.parse(result), "application/x-bittorrent");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            generateTorrentIntent(getApplicationContext(),"",intent);
           // startActivity(intent);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }



    public Intent generateTorrentIntent(Context context, String action, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo r : resolveInfo) {
                Intent progIntent = (Intent)intent.clone();
                String packageName = r.activityInfo.packageName;

                progIntent.setPackage(packageName);
                if (r.activityInfo.packageName.contains("torrent"))
                    targetedShareIntents.add(progIntent);

            }
            if (targetedShareIntents.size() > 0) {
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0),
                        "Select app to share");

                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[] {}));

                return chooserIntent;
            }
        }
        return null;
    }

    public void getVibratorService(){
        Vibrator vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(100);
    }





}




