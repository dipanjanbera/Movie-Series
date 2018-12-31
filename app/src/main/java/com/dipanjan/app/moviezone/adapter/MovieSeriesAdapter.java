
package com.dipanjan.app.moviezone.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dipanjan.app.moviezone.model.MovieSeries;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.List;

import info.dipanjan.app.R;

import com.dipanjan.app.moviezone.model.Movie;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Lincoln on 31/03/16.
 */

public class MovieSeriesAdapter extends RecyclerView.Adapter<MovieSeriesAdapter.MyViewHolder> {

    private List<MovieSeries> movieSeries;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, count, details, genre;
        public ImageView thumbnail, overflow, star;
        public CardView cardView;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
            title = view.findViewById(R.id.title);
            //  count = view.findViewById(R.id.count);
            star = view.findViewById(R.id.imageView2);
            details = view.findViewById(R.id.textView2);
            overflow = view.findViewById(R.id.overflow);
            cardView = view.findViewById(R.id.card_view);
            genre = (TextView) view.findViewById(R.id.textView17);

        }
    }


    public MovieSeriesAdapter(Context context, List<MovieSeries> movieSeries) {
        mContext = context;
        this.movieSeries = movieSeries;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MovieSeries singlemovieSeries = movieSeries.get(position);
        ThreeBounce threeBounce = new ThreeBounce();
        holder.progressBar.setIndeterminateDrawable(threeBounce);

        RequestOptions myOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(mContext).load(singlemovieSeries.getMoviePoster())
                .transition(withCrossFade(700))
                .apply(myOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // holder.progressBar.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);

                        return false;
                    }


                })
                .into(holder.thumbnail);


        holder.title.setText(singlemovieSeries.getMovieSeriesTitle());
        holder.title.setTag(singlemovieSeries.getMovieSeriesTitle());

        String text = "Collection of "+singlemovieSeries.getNumberOfMovies()+" Movies";
        holder.details.setVisibility(View.GONE);
        holder.star.setVisibility(View.GONE);
        holder.genre.setText(text);


    }

    @Override
    public int getItemCount() {
        return movieSeries.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;

        private GalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}