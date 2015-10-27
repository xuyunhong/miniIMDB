package yunhong.miniimdb.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import yunhong.miniimdb.R;

/**
 * Created by YunhongXu1 on 10/23/15.
 */
public class ResultCustomAdapter extends ArrayAdapter<MovieListItem> {

    Context context;
    List<MovieListItem> resultItemList;
    int layoutResId;

    public ResultCustomAdapter(Context context, int layoutResourceID, List<MovieListItem> resultItemList) {
        super(context, layoutResourceID, resultItemList);
        this.context = context;
        this.layoutResId = layoutResourceID;
        this.resultItemList = resultItemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResultItemHolder itemHolder;
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ResultItemHolder();
            view = inflater.inflate(layoutResId, parent, false);
            itemHolder.movieName = (TextView) view.findViewById(R.id.movie_name);
            itemHolder.releaseDate = (TextView) view.findViewById(R.id.release_date);
            itemHolder.director = (TextView) view.findViewById(R.id.director);
            view.setTag(itemHolder);
        } else {
            itemHolder = (ResultItemHolder) view.getTag();
        }
        MovieListItem mItem = (MovieListItem) this.resultItemList.get(position);
        itemHolder.movieName.setText(mItem.getMovieTitle());
        itemHolder.releaseDate.setText(mItem.getMovieYear());
        itemHolder.director.setText(mItem.getMovieDirector());
        return view;
    }

    private static class ResultItemHolder {
        TextView movieName;
        TextView releaseDate;
        TextView director;
    }
}
