package com.example.ellioc.watchlist;

import java.io.Serializable;

/**
 * Created by Ellioc on 8/30/2016.
 */
public class OmdbObject implements Serializable{

    private int id;
    private String title;
    private String movieId;
    private String year;
    private String posterUrl;
    private String type;
    private String plot;
    private String tomatoMeter;
    private String imdbRating;
    private String genre;
    private String rated;
    private String runtime;
    private String actors;
    private String directors;
    private String language;
    private String writers;


    public OmdbObject(String title, String movieId, String year, String type, String posterUrl){
        this.title = title;
        this.movieId = movieId;
        this.year = year;
        this.posterUrl = posterUrl;
        this.type = type;

        plot = "";
        tomatoMeter = "";
        genre = "";
        rated = "";
        runtime = "";
        actors = "";
        directors = "";
        language = "";
        imdbRating = "";
        writers = "";
    }

    public String getTitle(){ return this.title;}
    public String getMovieId(){return this.movieId;}
    public String getYear(){return this.year;}
    public String getType(){ return this.type; }
    public String getPosterUrl(){return this.posterUrl;}

    public String getActors() {return actors;}
    public String getDirectors() {return directors;}
    public String getGenre() {return genre;}
    public String getLanguage() {return language;}
    public String getPlot() {return plot;}
    public String getRated() {return rated;}
    public String getRuntime() {return runtime;}
    public String getTomatoMeter() {return tomatoMeter;}
    public String getImdbRating() {return imdbRating;}
    public String getWriters() {return writers;}
    public int getId(){return id;}

    public void setActors(String actors) {
        this.actors = actors;
    }
    public void setDirectors(String directors) {
        this.directors = directors;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public void setRated(String rated) {
        this.rated = rated;
    }
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
    public void setTomatoMeter(String tomatoMeter) {
        this.tomatoMeter = tomatoMeter;
    }
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
    public void setWriters(String writers) {
        this.writers = writers;
    }
    public void setId(int id){this.id = id;}
}
