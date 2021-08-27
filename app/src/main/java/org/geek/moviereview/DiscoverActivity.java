package org.geek.moviereview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.geek.moviereview.Adapters.MDBAdapter;
import org.geek.moviereview.Models.MDBres;
import org.geek.moviereview.Models.Movie;
import org.geek.moviereview.Network.MDBApi;
import org.geek.moviereview.Network.MDBClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverActivity extends AppCompatActivity {

    public static final String LOG_TAG = MDBAdapter.class.getName();
    public List<Movie> results;
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorTextView)
    TextView errorTextView;
    private MDBAdapter mdbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        ButterKnife.bind(this);

        loadPopular();
    }

    private void loadPopular() {
        try {
            MDBApi client = MDBClient.getClient();
            Call<MDBres> call = client.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);

            call.enqueue(new Callback<MDBres>() {
                @Override
                public void onResponse(@NotNull Call<MDBres> call, @NotNull Response<MDBres> response) {
                    if (response.isSuccessful()) {
                        hideProgressBar();
                        assert response.body() != null;
                        results = response.body().getResults();
                        mdbAdapter = new MDBAdapter(DiscoverActivity.this, results);
                        recyclerView.setAdapter(mdbAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DiscoverActivity.this);
                        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.smoothScrollToPosition(0);
                        recyclerView.setHasFixedSize(true);
                        showPosters();

                    } else {
                        showUnsuccessfulMessage();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MDBres> call, @NotNull Throwable t) {
                    hideProgressBar();
                    showFailureMessage();
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void loadTrending() {
        try {
            MDBApi client = MDBClient.getClient();
            Call<MDBres> call = client.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);

            call.enqueue(new Callback<MDBres>() {
                @Override
                public void onResponse(@NotNull Call<MDBres> call, @NotNull Response<MDBres> response) {
                    if (response.isSuccessful()) {
                        hideProgressBar();
                        assert response.body() != null;
                        results = response.body().getResults();
                        mdbAdapter = new MDBAdapter(DiscoverActivity.this, results);
                        recyclerView.setAdapter(mdbAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DiscoverActivity.this);
                        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.smoothScrollToPosition(0);
                        recyclerView.setHasFixedSize(true);
                        showPosters();

                    } else {
                        showUnsuccessfulMessage();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MDBres> call, @NotNull Throwable t) {
                    hideProgressBar();
                    showFailureMessage();
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void loadSeries() {
        try {
            MDBApi client = MDBClient.getClient();
            Call<MDBres> call = client.DiscoverTvShows(BuildConfig.THE_MOVIE_DB_API_TOKEN);

            call.enqueue(new Callback<MDBres>() {
                @Override
                public void onResponse(@NotNull Call<MDBres> call, @NotNull Response<MDBres> response) {
                    if (response.isSuccessful()) {
                        hideProgressBar();
                        assert response.body() != null;
                        results = response.body().getResults();
                        mdbAdapter = new MDBAdapter(DiscoverActivity.this, results);
                        recyclerView.setAdapter(mdbAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DiscoverActivity.this);
                        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.smoothScrollToPosition(0);
                        recyclerView.setHasFixedSize(true);
                        showPosters();

                    } else {
                        showUnsuccessfulMessage();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MDBres> call, @NotNull Throwable t) {
                    hideProgressBar();
                    showFailureMessage();
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showPosters() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showFailureMessage() {
        errorTextView.setText(R.string.FAILURE_MSG);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void showUnsuccessfulMessage() {
        errorTextView.setText(R.string.UNSUCCESSFUL_MSG);
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            loadPopular();
        } else {
            if (id == R.id.trend) {
                loadTrending();
            } else {
                if (id == R.id.tv) {
                    loadSeries();
                } else {
                    if (id == R.id.exit) {
                        finish();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
