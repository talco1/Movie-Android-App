package com.example.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListener {

    private String movies_api = "https://api.themoviedb.org/3/movie/top_rated?api_key=20a0f9ef8262bf9b90fcdab99be1a755";

    private List<Movie> movieList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create new array list of movies, in order to save the data from the API
        movieList = new ArrayList<>();
        //get data from the API and parse json
        new getMoviesData().execute();
    }

    /* AsyncTask subclass to get the data from the API and save it in the movies list */
    private class getMoviesData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            URL url = null;
            HttpURLConnection urlConnection = null;
            String data = "";

            try {
                //read data of API from the URL and save it in "data"
                url = new URL(movies_api);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(in);
                int reader = streamReader.read();
                while(reader != -1) {
                    data += (char)reader;
                    reader = streamReader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //close the HttpURLConnection once the response body has been read
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return data;
        }

        /* Runs after doInBackground, input: s (the returned value from doInBackground)*/
        @Override
        protected void onPostExecute(String s) {
            try {
                //parse the JSON file and extract necessary information from it
                JSONObject json = new JSONObject(s);
                JSONArray jsonResults = json.getJSONArray("results");
                //create new Movie object and add it to the movies list
                for (int i = 0; i < jsonResults.length(); i++) {
                    JSONObject jsonObject = jsonResults.getJSONObject(i);
                    Movie movie = new Movie(jsonObject.getString("title"), jsonObject.getString("poster_path"),
                            jsonObject.getString("release_date"), jsonObject.getString("overview"),
                            jsonObject.getString("vote_average"));
                    movieList.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //after parsing the JSON, call initRecyclerView
            initRecyclerView(movieList);
        }
    }

    /* Initialize recycler view and the adapter */
    private void initRecyclerView(List<Movie> movies) {
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, movies);
        recyclerView.setAdapter(adapter);
        //set the layout of 2 movies in one row
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    /* When a movie cell is clicked, start the movie activity */
    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieActivity.class);
        //pass relevant information
        intent.putExtra("image", movieList.get(position).getImage());
        intent.putExtra("name", movieList.get(position).getName());
        intent.putExtra("description", movieList.get(position).getDescription());
        intent.putExtra("releaseDate", movieList.get(position).getReleaseDate());
        intent.putExtra("rating", movieList.get(position).getVoteRating());
        startActivity(intent);
    }
}