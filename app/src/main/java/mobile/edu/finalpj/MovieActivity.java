package mobile.edu.finalpj;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mobile.edu.finalpj.domain.Movie;
import mobile.edu.finalpj.repository.MovieRepo;

public class MovieActivity extends AppCompatActivity {

    private MovieRepo movieRepo = new MovieRepo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final ListView movieListView = (ListView) findViewById(R.id.movieList);

        final ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieRepo.getMovies());
        movieListView.setAdapter(adapter);

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final Movie item = (Movie) adapterView.getItemAtPosition(i);
                view.animate().setDuration(10).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                movieRepo.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }
        });
    }
}


