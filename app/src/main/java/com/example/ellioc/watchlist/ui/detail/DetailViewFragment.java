package com.example.ellioc.watchlist.ui.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ellioc.watchlist.R;
import com.example.ellioc.watchlist.data.api.OmdbIntentService;
import com.example.ellioc.watchlist.data.model.OmdbObject;
import com.example.ellioc.watchlist.data.model.WatchListDbHelper;
import com.example.ellioc.watchlist.ui.OmdbResultReceiver;

public class DetailViewFragment extends Fragment implements OmdbResultReceiver.Receiver {
    public static final String ARG_OBJECT = "object";
    public static final String ARG_POS = "position";
    private OmdbObject omdbObject;
    private OmdbResultReceiver resultReceiver;
    private int position;
    private View root;

    public DetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            omdbObject = (OmdbObject) getArguments().get(ARG_OBJECT);
            position = getArguments().getInt(ARG_POS);
        }
        resultReceiver = new OmdbResultReceiver(new Handler());
        resultReceiver.setReceiver(this);
        initData();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail_view, container, false);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initData(){
        OmdbIntentService.startActionGetMovieInfoById(getActivity(), omdbObject.getMovieId(), resultReceiver);
    }

    private void inflateView(){
        TextView textViewTitle = (TextView) root.findViewById(R.id.textViewTitle);
        assert textViewTitle != null;
        textViewTitle.setText(omdbObject.getTitle());
        TextView textViewDirector = (TextView) root.findViewById(R.id.textViewDirector);
        assert textViewDirector != null;
        String director = "Director: " + omdbObject.getDirectors();
        textViewDirector.setText(director);
        TextView textViewActors = (TextView) root.findViewById(R.id.textViewActors);
        assert textViewActors != null;
        String actor = "Actors: " + omdbObject.getActors();
        textViewActors.setText(actor);
        TextView textViewYear = (TextView) root.findViewById(R.id.textViewYear);
        assert textViewYear != null;
        String year = "Year: " + omdbObject.getYear();
        textViewYear.setText(year);
        TextView textViewGenre = (TextView) root.findViewById(R.id.textViewGenre);
        assert textViewGenre != null;
        String genre = "Genre: " + omdbObject.getGenre();
        textViewGenre.setText(genre);
        TextView textViewRuntime = (TextView) root.findViewById(R.id.textViewRuntime);
        assert textViewRuntime != null;
        String runtime = "Runtime: " + omdbObject.getRuntime();
        textViewRuntime.setText(runtime);
        TextView textViewRating = (TextView) root.findViewById(R.id.textViewRating);
        assert textViewRating != null;
        String rating = "Rating: " + omdbObject.getRated();
        textViewRating.setText(rating);
        TextView textViewTomatoMeter = (TextView) root.findViewById(R.id.textViewTomatoMeter);
        assert textViewTomatoMeter != null;
        String tomatoMeter = "Rotten Tomatoes Rating: " + omdbObject.getTomatoMeter();
        textViewTomatoMeter.setText(tomatoMeter);
        TextView textViewImdbRating = (TextView) root.findViewById(R.id.textViewImdbRating);
        assert textViewImdbRating != null;
        String imdbRating = "IMDB Rating: " + omdbObject.getImdbRating();
        textViewImdbRating.setText(imdbRating);
        TextView textViewLanguage = (TextView) root.findViewById(R.id.textViewLanguage);
        assert textViewLanguage != null;
        String language = "Language: " + omdbObject.getLanguage();
        textViewLanguage.setText(language);
        TextView textViewPlot = (TextView) root.findViewById(R.id.textViewPlot);
        assert textViewPlot != null;
        String plot = "Plot: " + omdbObject.getPlot();
        textViewPlot.setText(plot);

        final Button button = (Button) root.findViewById(R.id.add_to_watch_list_button);
        assert button != null;

        final WatchListDbHelper watchListDbHelper = new WatchListDbHelper(getActivity());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchListDbHelper.addOmdbObject(omdbObject);
                button.setVisibility(View.GONE);
                CharSequence text = omdbObject.getTitle() + " Added to Watch List!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getActivity(), text, duration);
                toast.show();
            }
        });
        if (watchListDbHelper.getObjectByTitle(omdbObject.getTitle()) != null){
            button.setVisibility(View.GONE);
        }
    }
    public void inflateImageView(Bitmap bitmap){
        ImageView imageView = (ImageView) root.findViewById(R.id.poster_image_view);
        assert imageView != null;
        imageView.setImageBitmap(bitmap);
    }

    public void onReceiveResult(int val, Bundle b){
            if(val == 0){
                try {
                    omdbObject = (OmdbObject) b.getSerializable("results");
                    OmdbIntentService.startActionGetMoviePosterByURL(getActivity(), omdbObject.getPosterUrl(), resultReceiver);
                    inflateView();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if(val == 1){
                Bitmap bitmap = (Bitmap) b.get("image_bitmap");
                inflateImageView(bitmap);
            }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
