package mprog.simon.simonilic_pset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements FetchMovieData.AsyncResponse {

    ListView listView;

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
    }
}

