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
import com.example.topmovies.models.ResultSeries;
import com.example.topmovies.repo.Links;
import com.squareup.picasso.Picasso;

public class SeriesAdapter extends PagedListAdapter<ResultSeries, SeriesAdapter.ViewHolder> {
    private SeriesAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onTVClicked(ResultSeries tv);
    }

    private static DiffUtil.ItemCallback<ResultSeries> itemCallback = new DiffUtil.ItemCallback<ResultSeries>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResultSeries oldItem, @NonNull ResultSeries newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ResultSeries oldItem, @NonNull ResultSeries newItem) {
            return oldItem.equals(newItem);
        }
    };

    public SeriesAdapter(SeriesAdapter.OnItemClickListener itemClickListener) {
        super(itemCallback);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultSeries item = getItem(position);

        if (item.getPosterPath() != null) {
            Picasso.get()
                    .load(Links.IMAGE_PATH.concat(item.getPosterPath()))
                    .error(R.drawable.television)
                    .placeholder(R.drawable.television)
                    .into(holder.s_poster);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView s_poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            s_poster = itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(v -> {
               if (itemClickListener!=null){
                   if (getAdapterPosition()!=-1){
                       itemClickListener.onTVClicked(getItem(getAdapterPosition()));
                   }
               }
            });
        }
    }
}
