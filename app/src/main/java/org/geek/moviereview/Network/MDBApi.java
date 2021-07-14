package org.geek.moviereview.Network;

import org.geek.moviereview.Models.MDBres;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MDBApi {
    @GET("movie/popular")
    Call<MDBres> getPopularMovies (
          @Query("api_key") String apiKey
    );

    @GET("movie/top_rated")
    Call<MDBres> getTopRatedMovies (
            @Query("api_key") String apiKey
    );

    @GET("discover/tv")
    Call<MDBres> DiscoverTvShows (
            @Query("api_key") String apiKey
    );
}
