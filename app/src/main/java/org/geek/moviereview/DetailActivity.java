package org.geek.moviereview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.geek.moviereview.Models.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

//    @BindView(R.id.selected_poster) ImageView selected;
//    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.thumbnail) ImageView moviePoster;
    @BindView(R.id.movie_title) TextView title;
    @BindView(R.id.overview) TextView overview;
    @BindView(R.id.release) TextView released;
    @BindView(R.id.avg_ratings) TextView ratings;

    Movie movie;
    String thumbnail, movie_name, plot_overview, rating, date_released;
    int movie_id;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);
        ButterKnife.bind(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        activateCollapsingToolbar();

        Intent intent = getIntent();
        if (intent.hasExtra("movies")) {
            movie = getIntent().getParcelableExtra("movies");
//            Selected movie data
            thumbnail = movie.getPosterPath();
            movie_name = movie.getOriginalTitle();
            plot_overview = movie.getOverview();
            rating = Double.toString(movie.getVoteAverage());
            date_released = movie.getReleaseDate();
            movie_id = movie.getId();

            String poster = "https://image.tmdb.org/t/p/w500" + thumbnail;

            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.notfound)
                    .into(moviePoster);

            title.setText(movie_name);
            overview.setText(plot_overview);
            released.setText(String.format("Release Date: %s", date_released));
            ratings.setText(rating);
        } else {
            Toast.makeText(this, R.string.More_details_ermsg, Toast.LENGTH_SHORT).show();
        }

    }

//    private void activateCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBar);
//        collapsingToolbarLayout.setTitle(" ");
//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
//        appBarLayout.setExpanded(true);
//
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1){
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0){
//                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
//                    isShow = true;
//                }else if (isShow){
//                    collapsingToolbarLayout.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });
//    }
}