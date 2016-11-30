package mobile.edu.finalpj.repository;

import java.util.ArrayList;
import java.util.List;

import mobile.edu.finalpj.domain.Movie;

/**
 * Created by ysyh on 23.11.2016.
 */
public class MovieRepo {

    private List<Movie> movieList;
    private static MovieRepo movieRepo;

    private MovieRepo() {
        movieList = new ArrayList<>();
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

    public Movie findMovieById(Integer id) {
        for (Movie movie : movieList) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    public List<Movie> getMovies() {
        return movieList;
    }

    public void remove(Movie movie) {
        movieList.remove(movie);
    }

    public static MovieRepo getInstance() {
        if (movieRepo == null) {
            movieRepo = new MovieRepo();
            return movieRepo;
        } else {
            return movieRepo;
        }
    }

}
