package info.androidhive.glide.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.UnsupportedEncodingException;
import java.util.List;

import info.androidhive.glide.R;
import info.androidhive.glide.activity.MovieDeatils;
import info.androidhive.glide.app.AppController;
import info.androidhive.glide.bo.MovieDetailsBO;
import info.androidhive.glide.helper.Helper;
import info.androidhive.glide.model.Torrent;


public class DownloadMovieAdapter extends RecyclerView.Adapter<DownloadMovieAdapter.MyViewHolder> {

    private List<Torrent> torrentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView torrentQuality, torrentSize,torrentInfo;
        public ImageView rowImage,magnetLink,downloadLink;

        public MyViewHolder(View view) {
            super(view);
            torrentQuality = (TextView) view.findViewById(R.id.textView28);
            torrentSize = (TextView) view.findViewById(R.id.textView29);
            torrentInfo = (TextView) view.findViewById(R.id.textView30);
            rowImage = (ImageView) view.findViewById(R.id.imageView);
            magnetLink = (ImageView) view.findViewById(R.id.imageView16);
            downloadLink = (ImageView) view.findViewById(R.id.imageView17);

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

        final Torrent torrent = torrentList.get(position);
        if(torrent!=null){
            holder.torrentQuality.setText(torrent.getQuality());
            holder.torrentSize.setText("("+torrent.getSize()+")");
            holder.torrentInfo.setText("Torrent File (S:"+torrent.getSeeds()+" / P: "+torrent.getPeers()+")");

            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.movie_gerne))

                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.rowImage);

            holder.magnetLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                            String URI = Helper.generateMagneticUrl(torrent.getHash(),torrent.getMovieTitle());
                            AppController.openMagneturi(URI,context);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                }
            });

            holder.downloadLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(torrent.getUrl()));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return torrentList.size();
    }



}