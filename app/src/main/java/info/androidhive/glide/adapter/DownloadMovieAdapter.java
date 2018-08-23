package info.androidhive.glide.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import info.androidhive.glide.R;
import info.androidhive.glide.activity.MovieDeatils;
import info.androidhive.glide.bo.MovieDetailsBO;
import info.androidhive.glide.model.Torrent;


public class DownloadMovieAdapter extends RecyclerView.Adapter<DownloadMovieAdapter.MyViewHolder> {

    private List<Torrent> torrentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView torrentQuality, torrentSize;
        public ImageView rowImage;

        public MyViewHolder(View view) {
            super(view);
            torrentQuality = (TextView) view.findViewById(R.id.textView28);
            torrentSize = (TextView) view.findViewById(R.id.textView29);
            rowImage = (ImageView) view.findViewById(R.id.imageView);

        }
    }


    public DownloadMovieAdapter(Context context, List<Torrent> torrentList) {
        this.torrentList = torrentList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.download_single_row, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Torrent torrent = torrentList.get(position);
        if(torrent!=null){
            holder.torrentQuality.setText(torrent.getQuality());
            holder.torrentSize.setText("("+torrent.getSize()+")");

            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.movie_gerne))

                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.rowImage);
        }



    }

    @Override
    public int getItemCount() {
        return torrentList.size();
    }



}