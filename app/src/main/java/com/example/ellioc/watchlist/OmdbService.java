package com.example.ellioc.watchlist;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OmdbService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_MOVIES_BY_TITLE = "com.example.ellioc.watchlist.action.GET_MOVIES";
    private static final String ACTION_GET_MOVIE_INFO_BY_ID = "com.example.ellioc.watchlist.action.GET_INFO";
    private static final String ACTION_GET_MOVIE_POSTER_BY_URL = "com.example.ellioc.watchlist.action.GET_POSTER";

    // TODO: Rename parameters
    private static final String PARAM_TITLE = "com.example.ellioc.watchlist.extra.TITLE";
    private static final String PARAM_ID = "com.example.ellioc.watchlist.extra.ID";
    private static final String PARAM_POSTER_URL = "com.example.ellioc.watchlist.extra.POSTER_URL";

    private static final String RECEIVER_TAG = "receiver";
    private static final String IMAGE_VIEW_TAG = "image_view";

    public OmdbService() {
        super("OmdbService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetMoviesByTitle(Context context, String title, OmdbResultReceiver receiver) {
        Intent intent = new Intent(context, OmdbService.class);
        intent.setAction(ACTION_GET_MOVIES_BY_TITLE);
        intent.putExtra(PARAM_TITLE, title);
        intent.putExtra(RECEIVER_TAG, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
  //   TODO: Customize helper method
    public static void startActionGetMovieInfoById(Context context, String movieId, ResultReceiver receiver) {
        Intent intent = new Intent(context, OmdbService.class);
        intent.setAction(ACTION_GET_MOVIE_INFO_BY_ID);
        intent.putExtra(PARAM_ID, movieId);
        intent.putExtra(RECEIVER_TAG, receiver);
        context.startService(intent);
    }

    public static void startActionGetMoviePosterByURL(Context context, String url, ResultReceiver receiver){
        Intent intent = new Intent(context, OmdbService.class);
        intent.setAction(ACTION_GET_MOVIE_POSTER_BY_URL);
        intent.putExtra(PARAM_POSTER_URL, url);
        intent.putExtra(RECEIVER_TAG, receiver);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_MOVIES_BY_TITLE.equals(action)) {
                final String title = intent.getStringExtra(PARAM_TITLE);
                ResultReceiver rec = intent.getParcelableExtra(RECEIVER_TAG);
                handleActionGetMoviesByTitle(title, rec);

            } else if (ACTION_GET_MOVIE_INFO_BY_ID.equals(action)) {
                final String movieId = intent.getStringExtra(PARAM_ID);
                ResultReceiver rec = intent.getParcelableExtra(RECEIVER_TAG);
                handleActionGetMovieById(movieId, rec);
            }
            else if (ACTION_GET_MOVIE_POSTER_BY_URL.equals(action)){
                final String posterUrl = intent.getStringExtra(PARAM_POSTER_URL);
                ResultReceiver rec = intent.getParcelableExtra(RECEIVER_TAG);
                handleActionGetMoviePosterByURL(posterUrl, rec);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetMoviesByTitle(String title, final ResultReceiver receiver) {
        String getMoviesByTitleBaseURL = "http://www.omdbapi.com/?s=";
        String getMoviesByTitleParams = "&y=&r=json";
        title = title.replaceAll("\\s+", "+");
        String url = getMoviesByTitleBaseURL + title + getMoviesByTitleParams;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getString("Response").equals("True")){
                                int totalResults = Integer.parseInt(response.get("totalResults").toString());
                                String searchResults = response.getString("Search");
                                Bundle b = new Bundle();
                                b.putString("results", searchResults);
                                receiver.send(0, b);
//                                OmdbJSONParser jsonParser = new OmdbJSONParser(searchResults);
//                                ArrayList<OmdbObject> parsedObjects = jsonParser.parseMovieObjects();
//                                omdbListAdapter.upDateEntries(parsedObjects);
                            }
                            else{
                                String result = response.getString("Error");
                                Bundle b = new Bundle();
                                b.putString("results", result);
                                receiver.send(1, b);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        OmdbSingleton.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetMovieById(String movieId, final ResultReceiver receiver) {
        // TODO: Handle action Baz
        Log.i("ID", movieId);
        final String getMoviesByIdBaseURL = "http://www.omdbapi.com/?i=";
        final String getMoviesByIdParams = "&plot=short&r=json&tomatoes=true";
        String url = getMoviesByIdBaseURL + movieId + getMoviesByIdParams;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.i("JSON", response.toString());
                            if(response.getString("Response").equals("True")){
                                Bundle b = new Bundle();
                                b.putString("results", response.toString());
                                receiver.send(0, b);
                            }
                            else{
                                String result = response.getString("Error");
                                Bundle b = new Bundle();
                                b.putString("results", result);
                                receiver.send(2, b);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        OmdbSingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void handleActionGetMoviePosterByURL(String url, final ResultReceiver receiver){
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Bundle b = new Bundle();
                        b.putParcelable("image_bitmap", bitmap);
                        receiver.send(1,b);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        OmdbSingleton.getInstance(this).addToRequestQueue(request);
    }
}
