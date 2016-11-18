package mprog.simon.simonilic_pset3;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Simon on 15-11-2016.
 */

public class FetchMovieData extends AsyncTask<String, Void, String>{

    public AsyncResponse delegate = null;
    private Activity mActivity;

    public FetchMovieData(Activity activity, AsyncResponse delegate){
        mActivity = activity;
        this.delegate = delegate;
    }

    // Interface to relay data back to MainActivity
    public interface AsyncResponse {
        void processFinish(String output);
    }

    protected void onPreExecute() {
        Toast.makeText(mActivity.getApplicationContext(),
                "Fetching data from server", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected String doInBackground(String... args) {

        String api_url = "http://www.omdbapi.com/?t=";

        URL url = null;
        try {
            url = new URL(api_url + args[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            Toast.makeText(mActivity, "Error fetching data", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    protected void onPostExecute(String result) {
        if (result == null) {
            return;
        }

        Toast.makeText(mActivity.getApplicationContext(), "Fetched data",
                Toast.LENGTH_SHORT).show();

        delegate.processFinish(result);


    }
}