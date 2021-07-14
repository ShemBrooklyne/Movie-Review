package org.geek.moviereview.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.geek.moviereview.DetailActivity;
import org.geek.moviereview.Models.Movie;
import org.geek.moviereview.R;
import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MDBAdapter extends RecyclerView.Adapter<MDBAdapter.ViewHolder> {

    private final List<Movie> mResults;
    private final Context mContext;

    public MDBAdapter(Context context, List<Movie> results) {
        mContext = context;
        mResults = results;
    }

    @NotNull
    @Override
    public MDBAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull MDBAdapter.ViewHolder holder, int position) {
        holder.bindMovies(mResults.get(position));
//        holder.title.setText(mResults.get(position).getOriginalTitle());
//        holder.year.setText(mResults.get(position).getReleaseDate());
        String poster = "https://image.tmdb.org/t/p/w500" + mResults.get(position).getPosterPath();

        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.notfound)
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.poster) ImageView poster;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.year) TextView year;

        private final Context mContext;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }


        public void bindMovies(Movie result) {
            title.setText(result.getOriginalTitle());
            year.setText(result.getReleaseDate());
        }

        @Override
        public void onClick(View view) {
//            Open more detail activity on click
            int itemPosition = getLayoutPosition();
            Movie clickedMovie = mResults.get(itemPosition);
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("movies", clickedMovie);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            Toast.makeText(view.getContext(), "More details about " + clickedMovie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
