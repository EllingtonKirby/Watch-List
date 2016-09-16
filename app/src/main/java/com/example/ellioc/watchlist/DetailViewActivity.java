package com.example.ellioc.watchlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailViewActivity extends AppCompatActivity implements OmdbResultReceiver.Receiver{
    public OmdbResultReceiver omdbResultReceiver;
    public static final String OMDB_OBJECT_KEY = "omdb_object_key";
    public static final String START_KEY = "start";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omdb_specific_movie);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra(OMDB_OBJECT_KEY);

        omdbResultReceiver = new OmdbResultReceiver(new Handler());
        omdbResultReceiver.setReceiver(this);

        OmdbIntentService.startActionGetMovieInfoById(this, movieId, omdbResultReceiver);
    }


    public void onReceiveResult(int val, Bundle bundle){
        if(val == 0){
            try {
                OmdbObject omdbObject = (OmdbObject) bundle.getSerializable("results");
                OmdbIntentService.startActionGetMoviePosterByURL(this, omdbObject.getPosterUrl(), omdbResultReceiver);
                inflateView(omdbObject);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(val == 1){
            Bitmap bitmap = (Bitmap) bundle.get("image_bitmap");
            inflateImageView(bitmap);
        }
    }
    public void inflateView(final OmdbObject omdbObject){
        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        assert textViewTitle != null;
        textViewTitle.setText(omdbObject.getTitle());
        TextView textViewDirector = (TextView) findViewById(R.id.textViewDirector);
        assert textViewDirector != null;
        String director = "Director: " + omdbObject.getDirectors();
        textViewDirector.setText(director);
        TextView textViewActors = (TextView) findViewById(R.id.textViewActors);
        assert textViewActors != null;
        String actor = "Actors: " + omdbObject.getActors();
        textViewActors.setText(actor);
        TextView textViewYear = (TextView) findViewById(R.id.textViewYear);
        assert textViewYear != null;
        String year = "Year: " + omdbObject.getYear();
        textViewYear.setText(year);
        TextView textViewGenre = (TextView) findViewById(R.id.textViewGenre);
        assert textViewGenre != null;
        String genre = "Genre: " + omdbObject.getGenre();
        textViewGenre.setText(genre);
        TextView textViewRuntime = (TextView) findViewById(R.id.textViewRuntime);
        assert textViewRuntime != null;
        String runtime = "Runtime: " + omdbObject.getRuntime();
        textViewRuntime.setText(runtime);
        TextView textViewRating = (TextView) findViewById(R.id.textViewRating);
        assert textViewRating != null;
        String rating = "Rating: " + omdbObject.getRated();
        textViewRating.setText(rating);
        TextView textViewTomatoMeter = (TextView) findViewById(R.id.textViewTomatoMeter);
        assert textViewTomatoMeter != null;
        String tomatoMeter = "Rotten Tomatoes Rating: " + omdbObject.getTomatoMeter();
        textViewTomatoMeter.setText(tomatoMeter);
        TextView textViewImdbRating = (TextView) findViewById(R.id.textViewImdbRating);
        assert textViewImdbRating != null;
        String imdbRating = "IMDB Rating: " + omdbObject.getImdbRating();
        textViewImdbRating.setText(imdbRating);
        TextView textViewLanguage = (TextView) findViewById(R.id.textViewLanguage);
        assert textViewLanguage != null;
        String language = "Language: " + omdbObject.getLanguage();
        textViewLanguage.setText(language);
        TextView textViewPlot = (TextView) findViewById(R.id.textViewPlot);
        assert textViewPlot != null;
        String plot = "Plot: " + omdbObject.getPlot();
        textViewPlot.setText(plot);

        final Button button = (Button) findViewById(R.id.add_to_watch_list_button);
        assert button != null;
        final Context mCtx = this;
        final WatchListDbHelper watchListDbHelper = new WatchListDbHelper(mCtx);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchListDbHelper.addOmdbObject(omdbObject);
                button.setVisibility(View.GONE);
                CharSequence text = omdbObject.getTitle() + " Added to Watch List!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(mCtx, text, duration);
                toast.show();
            }
        });
        if (watchListDbHelper.getObjectByTitle(omdbObject.getTitle()) != null){
            button.setVisibility(View.GONE);
        }

    }
    public void inflateImageView(Bitmap bitmap){
        ImageView imageView = (ImageView) findViewById(R.id.poster_image_view);
        assert imageView != null;
        imageView.setImageBitmap(bitmap);
    }
}
