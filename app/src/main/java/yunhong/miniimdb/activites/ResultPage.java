package yunhong.miniimdb.activites;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yunhong.miniimdb.R;
import yunhong.miniimdb.widget.MovieListItem;
import yunhong.miniimdb.widget.ResultCustomAdapter;

/**
 * Created by YunhongXu1 on 10/23/15.
 */
public class ResultPage extends ListActivity {

    private Context context;
    private String url;
    private JSONArray jsonArray;
    private Boolean movieOrTV;

    List<MovieListItem> menuList = new ArrayList<MovieListItem>();
    ResultCustomAdapter adapter;
    HashMap infoMap = new HashMap();
    HashMap pathMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_list);
        Intent intent = getIntent();
        url = intent.getExtras().getString("searchURL", null);
        movieOrTV = intent.getExtras().getBoolean("movieOrTV", false);
        new ProgressTask(ResultPage.this).execute();
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog progressDialog;
        private ListActivity listActivity;

        public ProgressTask(ListActivity activity) {
            this.listActivity = activity;
            context = activity;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog.setMessage("progress start");
            this.progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            ListView pageEditList = getListView();
            TextView noResult = (TextView) findViewById(R.id.noResult);
            TextView listDes = (TextView) findViewById(R.id.resultList);
            if(menuList.size() == 0) {
                listDes.setVisibility(View.GONE);
            } else {
                noResult.setVisibility(View.GONE);
                adapter = new ResultCustomAdapter(context, R.layout.movie_result_item, menuList);
                pageEditList.setAdapter(adapter);
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            PostRequest postRequest = new PostRequest();
            jsonArray = postRequest.getJsonArray(url);
            for(int i=0; i<jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title;
                    String year;
                    if(movieOrTV) {
                        title = jsonObject.getString("original_title");
                        year  = jsonObject.getString("release_date");
                    } else {
                        title = jsonObject.getString("original_name");
                        year  = jsonObject.getString("first_air_date");
                    }
                    String detail = jsonObject.getString("overview");
                    String path = jsonObject.getString("poster_path");
                    infoMap.put(i, detail);
                    pathMap.put(i, path);
                    menuList.add(new MovieListItem(title, year, "N/A"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(ResultPage.this, MovieDetail.class);
        intent.putExtra("detail", infoMap.get(position).toString());
        intent.putExtra("path", pathMap.get(position).toString());
        startActivity(intent);
    }
}
