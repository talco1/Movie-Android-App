package com.example.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieActivity extends AppCompatActivity {
    ImageView image;
    TextView description, rating, releaseDate;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        image = findViewById(R.id.image);
        description = findViewById(R.id.description);
        rating = findViewById(R.id.movie_rating);
        releaseDate = findViewById(R.id.release_date);

        description.setText("Description:\n" + getIntent().getStringExtra("description"));
        rating.setText("vote average:\n" + getIntent().getStringExtra("rating"));
        releaseDate.setText("release date:\n" + getIntent().getStringExtra("releaseDate"));

        String image_path = "https://image.tmdb.org/t/p/w500";
        Glide.with(this).load(image_path + getIntent().getStringExtra("image")).into(image);

    }
}