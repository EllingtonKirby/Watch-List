package com.example.ellioc.watchlist.data.api;

import com.example.ellioc.watchlist.data.model.OmdbObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface OmdbApi {
    @GET("/")
    Observable<List<OmdbObject>> getByTitle(@Query("s") String title);

    //Get specific book
    @GET("/")
    Observable<OmdbObject> getByImdbId(@Query("i") String id);
}
