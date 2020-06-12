package com.example.topmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topmovies.R;
import com.example.topmovies.dataBase.FavouriteItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private final List<FavouriteItem> favouriteItems;
    private final FavoritesAdapter.OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onItemClicked(FavouriteItem item , int startPosition , int endPosition);
    }

    public FavoritesAdapter(List<FavouriteItem> favouriteItems , FavoritesAdapter.OnItemClickListener onItemClickListener) {
        this.favouriteItems = favouriteItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.title.setText(favouriteItems.get(position).title);
       holder.date.setText(favouriteItems.get(position).date);
        Picasso.get()
                .load(favouriteItems.get(position).posterUri)
                .placeholder(R.drawable.movies)
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return favouriteItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title , date;
        ImageView poster,deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favouriteTitle);
            date = itemView.findViewById(R.id.favouriteDate);
            poster = itemView.findViewById(R.id.favouritePoster);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            deleteBtn.setOnClickListener(v -> {
                if (onItemClickListener!=null){
                    if (getAdapterPosition()!= RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClicked(favouriteItems.get(getAdapterPosition())
                                , getAdapterPosition()
                                , favouriteItems.size()-1);
                        favouriteItems.remove(getAdapterPosition());
                    }
                }
            });
        }
    }
}
