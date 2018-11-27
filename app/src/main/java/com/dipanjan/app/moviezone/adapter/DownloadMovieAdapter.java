package com.dipanjan.app.moviezone.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dipanjan.app.moviezone.activity.MovieDeatils;
import com.dipanjan.app.moviezone.app.AppController;
import com.dipanjan.app.moviezone.model.Torrent;

import java.io.UnsupportedEncodingException;
import java.util.List;

import info.dipanjan.app.R;

import com.dipanjan.app.moviezone.helper.Helper;
import com.dipanjan.app.moviezone.util.Constant;


public class DownloadMovieAdapter extends RecyclerView.Adapter<DownloadMovieAdapter.MyViewHolder> {

    private List<Torrent> torrentList;
    private Context context;
    public CoordinatorLayout coordinatorLayout;

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


    public DownloadMovieAdapter(Context context, List<Torrent> torrentList,CoordinatorLayout coordinatorLayout) {
        this.torrentList = torrentList;
        this.context=context;
        this.coordinatorLayout=coordinatorLayout;
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
                            openMagneturi(URI,context,coordinatorLayout);

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

    public void openMagneturi(String url, final Context c,CoordinatorLayout coordinatorLayout){
        Log.e("TAG","open Magneturi magnet");

        if(url.startsWith("magnet:")) {
            Log.e("TAG","url starts with magnet");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PackageManager manager = c.getPackageManager();
            List<ResolveInfo> infos = manager.queryIntentActivities(browserIntent, 0);
            if (infos.size() > 0) {
                c.startActivity(browserIntent);
                Log.e("TAG","yes act to handle");
            } else {
                //Toast.makeText(c,"No Torrent client found to perform this task",Toast.LENGTH_SHORT).show();
                displayAlert(coordinatorLayout,"No Torrent client found", Constant.SNACKBAR_DISPALY_MODE_FAILURE,"Download >");


            }
        }else{
            Log.e("TAG","url does not starts with magnet");

        }
    }

    public void displayAlert(final CoordinatorLayout coordinatorLayout, String msg, int displayMode, String actionText){
        final Snackbar snackBar = Snackbar.make(coordinatorLayout,msg , Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackBar.getView();
        if(displayMode== Constant.SNACKBAR_DISPALY_MODE_SUCCESS){
            layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }else if(displayMode== Constant.SNACKBAR_DISPALY_MODE_FAILURE){
            layout.setBackgroundColor(ContextCompat.getColor(context, R.color.Color_Red));
        }


        TextView action = layout.findViewById(android.support.design.R.id.snackbar_action);
        action.setMaxLines(2);
        action.setTextColor(layout.getContext().getResources().getColor(android.R.color.black));
        snackBar.setAction(actionText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "https://play.google.com/store/search?q=torrent download"));
                context.startActivity(intent);
            }
        });

        snackBar.show();
    }




}