package com.example.ellioc.watchlist.data.api;

import com.example.ellioc.watchlist.data.RxSchedulers;
import com.example.ellioc.watchlist.data.model.OmdbObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Response;

import java.util.List;

import retrofit.Call;
import rx.Observable;


/**
 * Created by Ellioc on 10/15/2016.
 */

public class OmdbApiClient implements OmdbApi{
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static final String QUERY_TITLE = "title";
    public static final String QUERY_IMDB_ID = "imdb_id";

    public static final HttpUrl API_URL = HttpUrl.parse("http://www.omdbapi.com");
    public static final HttpUrl TITLE_SEARCH_URL = getApiBuilder()
            .addQueryParameter("r", "json")
            .build();
    public static final HttpUrl ID_SEARCH_URL = getApiBuilder()
            .addQueryParameter("r", "json")
            .addQueryParameter("plot", "short")
            .addQueryParameter("tomatoes", "true")
            .build();

    private final OmdbApi api;

    public OmdbApiClient(OmdbApi api) {
        this.api = api;
    }

    public static HttpUrl.Builder getApiBuilder(){
        return API_URL.newBuilder();
    }

    @Override public Observable<List<OmdbObject>> getByTitle(final String title){
        return api.getByTitle(title).compose(RxSchedulers.Scheduler());
    }

    @Override public Observable<OmdbObject> getByImdbId(final String id){
        return api.getByImdbId(id).compose(RxSchedulers.Scheduler());
    }
}
