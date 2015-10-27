package yunhong.miniimdb.widget;

/**
 * Created by YunhongXu1 on 10/23/15.
 */
public class MovieListItem {

    private String movieTitle;
    private String movieYear;
    private String movieDirector;

    public MovieListItem(String movieTitle, String movieYear, String movieDirector) {
        super();
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

}
