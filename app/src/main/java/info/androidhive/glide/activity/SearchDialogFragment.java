package info.androidhive.glide.activity;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import info.androidhive.glide.R;
import info.androidhive.glide.util.Constant;

public class SearchDialogFragment extends DialogFragment {
    private EditText queryTerm;
    private Spinner quality, gerne, rating, orderBy;
    private Button search;
    String[] gerne_values, quality_values, order_by_values, rating_values;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_dialog, container, false);
        queryTerm = (EditText) view.getRootView().findViewById(R.id.editText2);
        quality = (Spinner) view.getRootView().findViewById(R.id.spinner2);
        gerne = (Spinner) view.getRootView().findViewById(R.id.spinner3);
        rating = (Spinner) view.getRootView().findViewById(R.id.spinner4);
        orderBy = (Spinner) view.getRootView().findViewById(R.id.spinner5);
        search = (Button) view.findViewById(R.id.button4);
        initialiseDropDownValue();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(view.getContext(),""+constractQuereTerm(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ListMovieItem.class);
                intent.putExtra("SEARCH", constractQuereTerm());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }


        });


        return view;
    }

    private void initialiseDropDownValue() {
        gerne_values = getResources().getStringArray(R.array.genre_values);
        quality_values = getResources().getStringArray(R.array.quality_values);
        order_by_values = getResources().getStringArray(R.array.orderby_values);
        rating_values = getResources().getStringArray(R.array.rating_values);
    }

    private String constractQuereTerm() {

        String quality_val = "&quality=" + String.valueOf(quality_values[quality.getSelectedItemPosition()]);
        String gerne_val = "&genre=" + String.valueOf(gerne_values[gerne.getSelectedItemPosition()]);
        String rating_val = "&minimum_rating=" + String.valueOf(rating_values[rating.getSelectedItemPosition()]);
        String orderby_val = "&sort_by=" + String.valueOf(order_by_values[orderBy.getSelectedItemPosition()]);
        String queryTermValue = "&query_term=" + queryTerm.getText().toString();
        String query = Constant.ListDisplay.SEARCH_MOVIE + quality_val + gerne_val + rating_val + orderby_val + queryTermValue + "&page=";
        return query;
    }
}