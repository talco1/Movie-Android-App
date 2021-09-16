package com.example.mymovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<Movie> mData;

    public RecyclerViewAdapter(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    /* RecyclerView calls this method whenever it needs to create a new ViewHolder.
        The method creates and initializes the ViewHolder and its associated View */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_cell_activity, parent, false);
        //mContext is a MovieListener
        return new ViewHolder(view, (MovieListener) mContext);
    }

    /* RecyclerView calls this method to associate a ViewHolder with data.
       The method fetches the appropriate data and uses the data to fill in the view holder's layout */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
        String image_path = "https://image.tmdb.org/t/p/w500";
        Glide.with(mContext).load(image_path + mData.get(position).getImage()).into(holder.image);
    }

    /* RecyclerView calls this method to get the size of the data set */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /* A view holder class provides all the functionality for the movies list */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name;
        MovieListener movieListener;

        public ViewHolder(@NonNull View itemView, MovieListener movieListener) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_image);
            name = itemView.findViewById(R.id.movie_name);
            this.movieListener = movieListener;
            itemView.setOnClickListener(this);
        }

        //when a movie cell is pressed, call onMovieClick method
        @Override
        public void onClick(View v) {
            movieListener.onMovieClick(getBindingAdapterPosition());
        }
    }
}
