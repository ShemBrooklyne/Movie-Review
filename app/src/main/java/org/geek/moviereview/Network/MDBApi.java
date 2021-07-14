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
}
