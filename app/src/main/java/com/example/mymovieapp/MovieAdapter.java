package com.example.mymovieapp;

import android.content.Context;
import android.graphics.Movie;
import android.graphics.Point;
import android.icu.text.Transliterator;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Movie adapter.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    /**
     * The ArrayList which will be filled with MovieObjects
     */
    public ArrayList<Movies> list;
    /**
     * The constant MOVIE_BASE_URL.
     */
    public static final String MOVIE_BASE_URL = "https://image.tmdb.org/t/p/w342";
    /**
     * The constant itemWidth.
     */
    public static int itemWidth;
    /**
     * The constant itemhight.
     */
    public static int itemhight;
    private MovieViewHolder.OnItemClickListener mOnItemClickListener;

    /**
     * Instantiates a new Movie adapter.
     *
     * @param movieList           the movie list
     * @param onItemClickListener the on item click listener
     */
    public MovieAdapter(ArrayList<Movies> movieList, MovieViewHolder.OnItemClickListener onItemClickListener) {
        this.list = movieList;
        this.mOnItemClickListener = onItemClickListener;
    }

    //this method creates the viewholders
    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, null);
        return new MovieViewHolder(view, mOnItemClickListener);
    }

    //this sets the information to the specific viewholder
    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Picasso.get().load(MOVIE_BASE_URL + list.get(position).getPosterPath()).placeholder(R.drawable.noimage).into(movieViewHolder.movieItemImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * The type Movie view holder.
     */
    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView movieItemImage;
        /**
         * The On item click listener.
         */
        OnItemClickListener onItemClickListener;

        /**
         * Instantiates a new Movie view holder.
         * @param itemView            the item view
         * @param onItemClickListener the on item click listener
         */
        public MovieViewHolder(final View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            movieItemImage = (ImageView) itemView.findViewById(R.id.item_image);
            setDisplaySize();
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * Sets display size, for the single views.
         */
        public void setDisplaySize() {
            ViewGroup.LayoutParams params = movieItemImage.getLayoutParams();
            params.width = itemWidth / 2;
            params.height = itemhight / 3;
            movieItemImage.setLayoutParams(params);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }

        /**
         * The interface On item click listener.
         */
        interface OnItemClickListener {
            /**
             * On item click.
             * @param Position the position of the clicked view.
             */
            void onItemClick(int Position);
        }
    }
}




