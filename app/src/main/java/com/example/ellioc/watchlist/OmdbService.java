package com.example.ellioc.watchlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ellioc on 9/16/2016.
 */
public interface OmdbService  {
    @GET("/?r=json")
    Call<List<OmdbObject>> getByTitle(@Query("s") String title);

    //Get specific book
    @GET("/?plot=short&r=json&tomatoes=true")
    Call<OmdbObject> getByImdbId(@Query("i") String id);
}
