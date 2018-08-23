package info.androidhive.glide.adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.style.ThreeBounce;


import java.util.ArrayList;

import info.androidhive.glide.R;
import info.androidhive.glide.activity.MovieDeatils;
import info.androidhive.glide.model.Movie;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<Movie> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context context, ArrayList<Movie> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, int i) {

        Movie movie = itemsList.get(i);

        ThreeBounce threeBounce = new ThreeBounce();
        holder.progressBar.setIndeterminateDrawable(threeBounce);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvTitle.setTag(movie.getId());

        if(movie.getMediumCoverImage()!=null){
            RequestOptions myOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop();
                Glide.with(mContext)
                    .load(movie.getMediumCoverImage())
                    .transition(withCrossFade(700))
                    .apply(myOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.relativeLayout.setVisibility(View.GONE);
                            holder.itemImage.setVisibility(View.VISIBLE);

                            return false;
                        }


                    })
                    .into(holder.itemImage);
        }else{

            RequestOptions myOptions = new RequestOptions()
                    .placeholder(R.drawable.if_additional_icons).diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop();
            Glide.with(mContext)
                    .load(mContext.getResources().getDrawable(R.drawable.movie_gerne))
                    .transition(withCrossFade(700))
                    .apply(myOptions)
                    .thumbnail(0.5f)
                    .into(holder.itemImage);
        }



    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;

        protected ProgressBar progressBar;
        private RelativeLayout relativeLayout;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.spinKitLayout);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //Toast.makeText(v.getContext(), (String)tvTitle.getTag(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext,MovieDeatils.class);
                    intent.putExtra("MOVIEID", (String)tvTitle.getTag());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }
            });


        }

    }

}