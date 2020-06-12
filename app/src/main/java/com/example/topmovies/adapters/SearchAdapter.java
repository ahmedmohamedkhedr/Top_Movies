package com.example.topmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topmovies.R;
import com.example.topmovies.models.SearchingResults;
import com.example.topmovies.repo.Links;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    List<SearchingResults> moviesList;
    private SearchAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(SearchingResults result);
    }


    public SearchAdapter(List<SearchingResults> moviesList, SearchAdapter.OnItemClickListener itemClickListener) {
        this.moviesList = moviesList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String type = moviesList.get(position).getMediaType();
        if ("tv".equals(type)) {
            holder.title.setText(moviesList.get(position).getOriginalName());

        } else {
            holder.title.setText(moviesList.get(position).getOriginalTitle());
        }
        holder.type.setText(moviesList.get(position).getMediaType());

        if (moviesList.get(position).getPosterPath() != null)
            Picasso.get()
                    .load(Links.IMAGE_PATH.concat(moviesList.get(position).getPosterPath()))
                    .placeholder(R.drawable.movies)
                    .error(R.drawable.movies)
                    .into(holder.poster);

        else Picasso.get().load(R.drawable.movies).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, type;
        ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            type = itemView.findViewById(R.id.itemType);
            poster = itemView.findViewById(R.id.itemPoster);
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClicked(moviesList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
