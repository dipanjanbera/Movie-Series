package info.androidhive.glide.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.androidhive.glide.R;
import info.androidhive.glide.adapter.CastAdapter;
import info.androidhive.glide.adapter.MovieGerneListAdapter;
import info.androidhive.glide.model.Movie;
import info.androidhive.glide.model.SectionDataModel;

/**
 * Created by LENOVO on 22-07-2018.
 */

public class MovieGerneListLayout extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MovieGerneListAdapter movieGerneListAdapter;
    private List<String> movieGerneList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);



        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }

        movieGerneListAdapter = new MovieGerneListAdapter(getApplicationContext(),movieGerneList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieGerneListAdapter);

        populateMovieGerne();
    }

    private void populateMovieGerne(){
        List<String> movieGerneListLocal = Arrays.asList(getResources().getStringArray(R.array.genre_array_recycle_view));
        for(String stg:movieGerneListLocal){
            movieGerneList.add(stg);
            movieGerneListAdapter.notifyDataSetChanged();
        }

    }
}
