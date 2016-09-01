package com.example.ellioc.watchlist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ellioc on 8/31/2016.
 */
public class OmdbJSONParser {
    final private String KEY_TITLE = "Title";
    final private String KEY_MOVIE_ID = "imdbID";
    final private String KEY_YEAR = "Year";
    final private String KEY_POSTER_URL = "Poster";
    final private String KEY_TYPE = "Type";

    public OmdbJSONParser() throws JSONException{}

    public ArrayList<OmdbObject> parseMovieObjects(JSONArray jsonObject)throws JSONException{
        ArrayList<OmdbObject> retVal = new ArrayList<>();
        for (int i = 0; i < jsonObject.length(); i++){
            JSONObject currentObject = jsonObject.getJSONObject(i);
            if(currentObject != null){
                OmdbObject currentMovie = new OmdbObject(currentObject.getString(KEY_TITLE),
                                                        currentObject.getString(KEY_MOVIE_ID),
                                                        currentObject.getString(KEY_YEAR),
                                                        currentObject.getString(KEY_TYPE),
                                                        currentObject.getString(KEY_POSTER_URL)
                        );
                retVal.add(currentMovie);
            }
        }
        return retVal;
    }

    public OmdbObject parseMovieById(JSONObject jsonObject) throws JSONException{
        final String plot = "Plot";
        final String tomatoMeter = "tomatoMeter";
        final String imdbRating = "imdbRating";
        final String genre = "Genre";
        final String rated = "Rated";
        final String runtime = "Runtime";
        final String actors = "Actors";
        final String directors = "Director";
        final String language = "Language";
        final String writers = "Writer";

        OmdbObject omdbObject = new OmdbObject(jsonObject.getString(KEY_TITLE),
                                                jsonObject.getString(KEY_MOVIE_ID),
                                                jsonObject.getString(KEY_YEAR),
                                                jsonObject.getString(KEY_TYPE),
                                                jsonObject.getString(KEY_POSTER_URL));

        omdbObject.setActors(jsonObject.getString(actors));
        omdbObject.setDirectors(jsonObject.getString(directors));
        omdbObject.setGenre(jsonObject.getString(genre));
        omdbObject.setImdbRating(jsonObject.getString(imdbRating));
        omdbObject.setLanguage(jsonObject.getString(language));
        omdbObject.setPlot(jsonObject.getString(plot));
        omdbObject.setRated(jsonObject.getString(rated));
        omdbObject.setRuntime(jsonObject.getString(runtime));
        omdbObject.setTomatoMeter(jsonObject.getString(tomatoMeter));
        omdbObject.setWriters(jsonObject.getString(writers));

        return omdbObject;
    }

}
