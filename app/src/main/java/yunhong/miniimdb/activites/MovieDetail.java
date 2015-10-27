package yunhong.miniimdb.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import yunhong.miniimdb.R;

/**
 * Created by YunhongXu1 on 10/25/15.
 */
public class MovieDetail extends Activity{

    private String url = "http://image.tmdb.org/t/p/w1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        String detailInfo = intent.getExtras().getString("detail", null);
        String path = intent.getExtras().getString("path", null);

        TextView detailText = (TextView) findViewById(R.id.movieDetail);
        detailText.setText(detailInfo);
        //Log.d("Yunhong", "url from resultPage activity = " + url);

        if(path.matches("")) {
            Toast.makeText(getApplicationContext(), R.string.no_poster_find, Toast.LENGTH_LONG).show();
        } else {
           url += path;
            new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(url);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
