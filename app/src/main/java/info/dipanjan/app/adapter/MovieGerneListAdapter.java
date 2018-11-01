package info.dipanjan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import info.dipanjan.app.R;
import info.dipanjan.app.activity.ListMovieItem;
import info.dipanjan.app.util.Constant;

/**
 * Created by LENOVO on 22-07-2018.
 */


/**
 * Created by LENOVO on 14-07-2018.
 */
public class MovieGerneListAdapter extends RecyclerView.Adapter<MovieGerneListAdapter.MyViewHolder> {

    private List<String> gerneList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gerne;
        public RelativeLayout relativeLayout;


        public MyViewHolder(View view) {
            super(view);
            gerne = (TextView) view.findViewById(R.id.textView15);
            relativeLayout=(RelativeLayout) view.findViewById(R.id.layout_gerne);


        }
    }


    public MovieGerneListAdapter(Context context, List<String> gerneList) {
        this.gerneList = gerneList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_gerne_single_row_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String gerne = gerneList.get(position);
        if(gerne!=null){
            holder.gerne.setText(gerne);

        }


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Toast.makeText(v.getContext(), "click event on more, "+gerneList.get(position) , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ListMovieItem.class);
                    intent.putExtra("GERNE", gerneList.get(position));
                    intent.putExtra(Constant.ACTIVITY_NAME, gerneList.get(position)+" Movies");
                    v.getContext().startActivity(intent);





            }
        });



    }

    @Override
    public int getItemCount() {
        return gerneList.size();
    }
}
