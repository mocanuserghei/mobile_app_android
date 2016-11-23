package mobile.edu.finalpj.repository;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile.edu.finalpj.domain.Movie;

/**
 * Created by ysyh on 23.11.2016.
 */
public class MovieRepo {

    private List<Movie> movieList = new ArrayList<>();

    public MovieRepo() {
        movieList.add(new Movie(1, "Matrix", "Watchowski", "SciFi"));
        movieList.add(new Movie(2, "Kill Bill", "Tarantino", "Drama"));
        movieList.add(new Movie(3, "Red Dragon", "Sparrow", "Action"));
    }

    public void saveMovie(Movie movie) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getId().equals(movie.getId())) {
                movieList.set(i, movie);
                break;
            }
        }
    }

    public List<Movie> getMovies() {
        return movieList;
    }

    public void remove(Movie movie) {
        movieList.remove(movie);
    }
}
