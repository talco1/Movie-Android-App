package com.example.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {

    private String movies_api = "https://api.themoviedb.org/3/movie/top_rated?api_key=20a0f9ef8262bf9b90fcdab99be1a755";

    private List<Movie> movieList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();

        new getMoviesData().execute();

    }

    private class getMoviesData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            URL url = null;
            HttpURLConnection urlConnection = null;
            String data = "";

            try {
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
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject json = new JSONObject(s);
                JSONArray jsonResults = json.getJSONArray("results");

                for (int i = 0; i < jsonResults.length(); i++) {
                    JSONObject jsonObject = jsonResults.getJSONObject(i);
                    Movie movie = new Movie(jsonObject.getString("title"), jsonObject.getString("poster_path"));
                    movieList.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            initRecyclerView(movieList);
        }
    }

    private void initRecyclerView(List<Movie> movies) {
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, movies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}