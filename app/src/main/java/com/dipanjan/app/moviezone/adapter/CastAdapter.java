package com.dipanjan.app.moviezone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dipanjan.app.moviezone.model.Cast;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import info.dipanjan.app.R;

/**
 * Created by LENOVO on 14-07-2018.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    private List<Cast> castList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, role;
        public ImageView profilePic;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textView);
            role = (TextView) view.findViewById(R.id.textView1);
            profilePic = (ImageView) view.findViewById(R.id.thumbnail);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);


        }
    }


    public CastAdapter(Context context, List<Cast> castList) {
        this.castList = castList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Cast cast = castList.get(position);
        if(cast!=null){
            holder.name.setText(cast.getActorNmae());
            holder.role.setText(cast.getActorRole());

            Glide.with(context)
                    .load(cast.getProfilePic())

                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.profilePic);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(cast.getImdbCode()!=null){
                        new FinestWebView.Builder(context).theme(R.style.FinestWebViewTheme)
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

                                .show("https://www.imdb.com/name/nm" + cast.getImdbCode());
                    }
            }
        });


    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}