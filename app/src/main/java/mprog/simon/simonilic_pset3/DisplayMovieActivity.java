package mprog.simon.simonilic_pset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DisplayMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get intent extra's
        Intent parent_intent = getIntent();
        final String movieInfoString = parent_intent.getStringExtra("movie");
        boolean listed = parent_intent.getBooleanExtra("listed", false);

        // JSON-ify the result string
        JSONObject movieInfo = null;
        try {
            movieInfo = new JSONObject(movieInfoString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // get info strings
        String title = null, year = null, director = null,
                actors = null, plot = null, posterURL = null, imdbID = null;
        try {
            title = movieInfo.getString("Title");
            year = movieInfo.getString("Year");
            director = movieInfo.getString("Director");
            actors = movieInfo.getString("Actors");
            plot = movieInfo.getString("Plot");
            posterURL = movieInfo.getString("Poster");
            imdbID = movieInfo.getString("imdbID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set up floating action button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (listed) {
            fab.hide();
        }
        else {
            final String finalImdbID = imdbID;
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addMovie(finalImdbID, movieInfoString);
                }
            });
        }

        // get views
        TextView titleView = (TextView) findViewById(R.id.titleText);
        TextView castView = (TextView) findViewById(R.id.directorandcast);
        TextView plotView = (TextView) findViewById(R.id.plot);
        ImageView posterView = (ImageView) findViewById(R.id.poster);

        // set text in views
        titleView.setText(title + " (" + year + ')');
        castView.setText(director + "\n\n\n" + actors);
        plotView.setText(plot);

        // set poster image
        Picasso.with(getApplicationContext()).load(posterURL).into(posterView);
    }

    private void addMovie(String id, String movieInfoString) {
        // add movie to the shared pref list
        SharedPreferences sharedPref = getSharedPreferences(
                "mprog.simon.simonilic_pset3_movieList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(id, movieInfoString);
        editor.commit();

        // return to movie list activity
        Intent intent = new Intent(this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }
}
