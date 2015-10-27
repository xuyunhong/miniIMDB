package yunhong.miniimdb.activites;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yunhong.miniimdb.R;

public class MainActivity extends AppCompatActivity {

    EditText movieName;
    EditText movieYear;
    EditText TVName;
    EditText TVYear;
    Boolean movieOrTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieName = (EditText)findViewById(R.id.movieName);
        movieYear = (EditText)findViewById(R.id.movieYear);
        TVName = (EditText)findViewById(R.id.TVName);
        TVYear = (EditText)findViewById(R.id.TVYear);
        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()) {
                    String movieNameString = movieName.getText().toString();
                    String movieYearString = movieYear.getText().toString();
                    String TVNameString = TVName.getText().toString();
                    String TVYearString = TVYear.getText().toString();
                    if(movieNameString.matches("") && TVNameString.matches("")) {
                        Toast.makeText(getApplicationContext(), R.string.no_input_find, Toast.LENGTH_LONG).show();
                    } else if(!movieNameString.matches("") && !TVNameString.matches("")) {
                        Toast.makeText(getApplicationContext(), R.string.only_one_input, Toast.LENGTH_LONG).show();
                    } else {
                        String searchURL;
                        if(!movieNameString.matches("")) searchURL = composeURL(movieNameString, movieYearString, true);
                        else searchURL = composeURL(TVNameString, TVYearString, false);
                        Intent intent = new Intent(MainActivity.this, ResultPage.class);
                        intent.putExtra("searchURL", searchURL);
                        intent.putExtra("movieOrTV", movieOrTV);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_network_find, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String composeURL(String name, String year, boolean check) {
        movieOrTV = check;
        //String url = "http://api.themoviedb.org/3/search/movie?api_key=8da06736d03a3ce8d12019485815d9c5&query=iron+sky";
        String url = "http://api.themoviedb.org/3/search/";
        if(check) url += "movie";
        else url += "tv";

        name = name.replaceAll("\\s", "+");
        url += ("?api_key=8da06736d03a3ce8d12019485815d9c5&query=" + name);
        if(!year.matches("")) url += ("&year=" + year);
        return url;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
