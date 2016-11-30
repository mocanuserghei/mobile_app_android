package mobile.edu.finalpj.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import mobile.edu.finalpj.R;
import mobile.edu.finalpj.domain.Movie;
import mobile.edu.finalpj.repository.MovieRepo;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "mobile.edu.finalpj/.activities.movieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView movieListView = (ListView) findViewById(R.id.movieList);

        final ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MovieRepo.getInstance().getMovies());
        movieListView.setAdapter(adapter);

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                Movie item = (Movie) adapterView.getItemAtPosition(i);
                openMovieDetailActivity(item.getId());
            }
        });
    }

    public void openMovieDetailActivity(Integer movieId) {
        Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID, movieId.toString());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

}


