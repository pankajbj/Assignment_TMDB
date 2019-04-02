package com.example.pankaj.assignment_tmdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/movie/{category}")
    Call<MovieResults> getMovies(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );


    //https://api.themoviedb.org/3/movie/popular?api_key=955c8a14117387021399e34318ab8b5c&language=en-US&page=1

}
