package mprog.simon.simonilic_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovie();
            }
        });

        // get saved movies
        final SharedPreferences sharedPref = getSharedPreferences(
                "mprog.simon.simonilic_pset3_movieList", MODE_PRIVATE);
        Map<String, ?> movies = sharedPref.getAll();

        final List<String> movieTitles = new ArrayList<String>(), movieIDs = new ArrayList<String>();

        for (Map.Entry<String, ?> entry : movies.entrySet())
        {
            // get movie info as string
            String movieInfoString = (String) entry.getValue();
            // JSON-ify the result string
            JSONObject movieInfo = null;
            try {
                movieInfo = new JSONObject(movieInfoString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // get info strings
            String title = null, year = null;
            try {
                title = movieInfo.getString("Title");
                year = movieInfo.getString("Year");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            movieTitles.add(title + " (" + year + ")");
            movieIDs.add(entry.getKey());
        }
        // show message if no movies in list
        if (movieTitles.isEmpty()) {
            findViewById(R.id.noMoviesMessage).setVisibility(View.VISIBLE);
        }

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // define list adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, android.R.id.text1, movieTitles);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                displayMovie(sharedPref, movieIDs, position);
                }
            });
    }

    private void searchMovie() {
        // go to movie search activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void displayMovie(SharedPreferences sharedPref, List<String> movieIDs, int position) {
        // get movieID from list position
        String movieID = movieIDs.get(position);
        // get moveInfo as String from shared prefs
        String movieInfoString = sharedPref.getString(movieID, "");

        // go to display movie activity
        Intent intent = new Intent(this, DisplayMovieActivity.class);
        intent.putExtra("movie", movieInfoString);
        intent.putExtra("listed", true);
        startActivity(intent);
    }

}
