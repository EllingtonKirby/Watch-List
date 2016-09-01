package com.example.ellioc.watchlist;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class OmdbSearchActivity extends ListActivity implements OmdbResultReceiver.Receiver{
    public OmdbResultReceiver omdbResultReceiver;
    private OmdbListAdapter omdbListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omdb_search);
        String query = "";
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }
        omdbResultReceiver = new OmdbResultReceiver(new Handler());
        omdbResultReceiver.setReceiver(this);

        omdbListAdapter = new OmdbListAdapter(this);
        setListAdapter(omdbListAdapter);

        OmdbService.startActionGetMoviesByTitle(this, query, omdbResultReceiver);
    }

    public void onReceiveResult(int val, Bundle bundle){
        if(val == 0){
            Log.i("JSON", bundle.getString("results"));
            try {
                OmdbJSONParser jsonParser = new OmdbJSONParser();
                ArrayList<OmdbObject> parsedObjects = jsonParser.parseMovieObjects(new JSONArray(bundle.getString("results")));
                omdbListAdapter.upDateEntries(parsedObjects);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        else{
            TextView textView = (TextView) findViewById(R.id.empty);
            textView.setVisibility(View.VISIBLE);
        }
    }


}
