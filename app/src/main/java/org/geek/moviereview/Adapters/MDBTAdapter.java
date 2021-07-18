package org.geek.moviereview.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;

import org.geek.moviereview.Models.Trailer;
import org.geek.moviereview.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//Trailer Adapter
public class MDBTAdapter extends RecyclerView.Adapter<MDBTAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Trailer> mTrailer;

    public MDBTAdapter(Context mContext, List<Trailer> mTrailer) {
        this.mContext = mContext;
        this.mTrailer = mTrailer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bindTrailers(mTrailer.get(position));
//        holder.title.setText(mTrailer.get(position).getName());
//        holder.player.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=" + mTrailer.get(position).getKey()));

    }


    @Override
    public int getItemCount() {
        return mTrailer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_title)
        TextView title;
        @BindView(R.id.player)
        ImageView player;

        private final Context mContext;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void bindTrailers(Trailer trailer) {
            title.setText(trailer.getName());
//            player.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=" + trailer.getKey()));
//            player.setMediaController(new MediaController(this));
//            player.start();
        }
    }
}
