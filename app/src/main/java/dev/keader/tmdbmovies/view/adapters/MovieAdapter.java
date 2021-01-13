package dev.keader.tmdbmovies.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;
import dev.keader.tmdbmovies.databinding.ItemMovieBinding;
import dev.keader.tmdbmovies.view.home.MovieActions;

public class MovieAdapter extends PagedListAdapter<MovieWithRelations, MovieAdapter.ViewHolder> {
    private MovieActions movieActions;

    public MovieAdapter(MovieActions movieActions) {
        super(DIFF_CALLBACK);
        this.movieActions = movieActions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.from(parent, movieActions);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieWithRelations item = getItem(position);
        if (item != null)
            holder.bind(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemMovieBinding binding;
        private MovieActions movieActions;

        public ViewHolder(@NonNull View itemView, ItemMovieBinding binding, MovieActions movieActions) {
            super(itemView);
            this.binding = binding;
            this.movieActions = movieActions;
        }

        public void bind(MovieWithRelations movieDTO) {
            binding.setMovieWithRelations(movieDTO);
            binding.setMovieActions(movieActions);
        }

        public static ViewHolder from(ViewGroup parent, MovieActions movieActions) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemMovieBinding binding = ItemMovieBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(binding.getRoot(), binding, movieActions);
        }
    }

    private static DiffUtil.ItemCallback<MovieWithRelations> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieWithRelations>() {

                @Override
                public boolean areItemsTheSame(MovieWithRelations oldItem, MovieWithRelations newItem) {
                    // The ID property identifies when items are the same.
                    return oldItem.getMovie().getId() == newItem.getMovie().getId();
                }

                @Override
                public boolean areContentsTheSame(MovieWithRelations oldItem, MovieWithRelations newItem) {
                    // Don't use the "==" operator here. Either implement and use .equals(),
                    // or write custom data comparison logic here.
                    return oldItem.equals(newItem);
                }
            };

}
