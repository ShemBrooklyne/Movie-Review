package org.geek.moviereview;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gs.myratingbarlibrary.MyRatingBar;

import org.geek.moviereview.Adapters.MDBTAdapter;
import org.geek.moviereview.Models.MDBres;
import org.geek.moviereview.Models.Movie;
import org.geek.moviereview.Models.Trailer;
import org.geek.moviereview.Models.TrailerResponse;
import org.geek.moviereview.Network.MDBApi;
import org.geek.moviereview.Network.MDBClient;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.thumbnail)
    ImageView moviePoster;
    @BindView(R.id.movie_title)
    TextView title;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.release)
    TextView released;
    @BindView(R.id.avg_ratings)
    MyRatingBar ratings;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public List<Trailer> trailer;
    MDBTAdapter mdbtAdapter;

    MyRatingBar ratingBar;
    Movie movie;
    String thumbnail, movie_name, plot_overview, rating, date_released;
    int movie_id;

    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        Intent intent = getIntent();
        if (intent.hasExtra("movies")) {
            movie = getIntent().getParcelableExtra("movies");
//            Selected movie data
            thumbnail = movie.getPosterPath();
            movie_name = movie.getOriginalTitle();
//            Tv-shows has different param
//            movie_name = movie.getOriginalName();
            plot_overview = movie.getOverview();
            rating = String.valueOf((movie.getVoteAverage()));
            date_released = movie.getReleaseDate();
            //            Tv-shows has different param
//            date_released = movie.getAirDate();
            movie_id = movie.getId();

            String poster = getString(R.string.image_URL) + thumbnail;

            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.notfound)
                    .into(moviePoster);

            title.setText(movie_name);
            overview.setText(plot_overview);
            released.setText(String.format(getString(R.string.Release_DT), date_released));
            ratings.setRatingCount(Float.parseFloat(rating) / 2);

        } else {
            Toast.makeText(this, R.string.More_details_ermsg, Toast.LENGTH_SHORT).show();
        }

        setViews();

    }

    private void setViews() {
        mdbtAdapter = new MDBTAdapter(DetailActivity.this, trailer);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mdbtAdapter);
        mdbtAdapter.notifyDataSetChanged();

        ConfigJson();
    }

    private void ConfigJson() {
        try {
            MDBApi client = MDBClient.getClient();
            Call<TrailerResponse> call = client.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    if (response.isSuccessful()) {
                        hideProgressBar();
                        assert response.body() != null;
                        trailer = response.body().getResults();
                        recyclerView.setAdapter(new MDBTAdapter(DetailActivity.this, trailer));
                        recyclerView.smoothScrollToPosition(0);
                        showTrailers();
                    } else {
                        showUnsuccessfulMessage();
                    }
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    hideProgressBar();
                    showFailureMessage();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showFailureMessage() {
        Toast.makeText(DetailActivity.this, "Unable to load trailer at the moment", Toast.LENGTH_SHORT).show();
    }

    private void showUnsuccessfulMessage() {
        Toast.makeText(DetailActivity.this, "Error while loading trailer.", Toast.LENGTH_SHORT).show();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void showTrailers() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}