package mprog.simon.simonilic_pset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements FetchMovieData.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void search(View view) {
        // get searched title
        EditText editTextView = (EditText) findViewById(R.id.searchField);
        String search_term = editTextView.getText().toString();

        // get year
        EditText etYear = (EditText) findViewById(R.id.etYear);
        String year = etYear.getText().toString();

        // create asyncTask class instance
        FetchMovieData asyncTask = new FetchMovieData(this, this);
        asyncTask.execute(search_term, year);
    }

    //this override the implemented method from AsyncResponse
    @Override
    public void processFinish(String output){
        // go to movie display
        Intent intent = new Intent(this, DisplayMovieActivity.class);
        intent.putExtra("movie", output);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // go back to movie list
        Intent intent = new Intent(this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }
}

