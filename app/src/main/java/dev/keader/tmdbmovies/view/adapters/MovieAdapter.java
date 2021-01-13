package dev.keader.tmdbmovies.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.databinding.ItemMovieBinding;

public class MovieAdapter extends PagedListAdapter<MovieDTO, MovieAdapter.ViewHolder> {

    public MovieAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieDTO item = getItem(position);
        if (item != null)
            holder.bind(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemMovieBinding binding;

        public ViewHolder(@NonNull View itemView, ItemMovieBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void bind(MovieDTO movieDTO) {
            binding.setMovie(movieDTO);
        }

        public static ViewHolder from(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemMovieBinding binding = ItemMovieBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(binding.getRoot(), binding);
        }
    }

    private static DiffUtil.ItemCallback<MovieDTO> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieDTO>() {

                @Override
                public boolean areItemsTheSame(MovieDTO oldItem, MovieDTO newItem) {
                    // The ID property identifies when items are the same.
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(MovieDTO oldItem, MovieDTO newItem) {
                    // Don't use the "==" operator here. Either implement and use .equals(),
                    // or write custom data comparison logic here.
                    return oldItem.equals(newItem);
                }
            };

}
