package com.example.topmovies.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topmovies.R;
import com.example.topmovies.models.ResultMovies;
import com.example.topmovies.repo.Links;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends PagedListAdapter<ResultMovies, MoviesAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onMovieClicked(ResultMovies movies);
    }

    private static DiffUtil.ItemCallback<ResultMovies> diffCallback = new DiffUtil.ItemCallback<ResultMovies>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResultMovies oldItem, @NonNull ResultMovies newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ResultMovies oldItem, @NonNull ResultMovies newItem) {
            return oldItem.equals(newItem);
        }
    };


    public MoviesAdapter(OnItemClickListener listener) {
        super(diffCallback);
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultMovies resultMovies = getItem(position);
        if (resultMovies.getPosterPath() != null) {
            Picasso.get()
                    .load(Links.IMAGE_PATH.concat(resultMovies.getPosterPath()))
                    .placeholder(R.drawable.movies)
                    .error(R.drawable.movies)
                    .into(holder.moviePoster);
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    if (getAdapterPosition() != -1) {
                        onItemClickListener.onMovieClicked(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
