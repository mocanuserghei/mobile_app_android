package mobile.edu.finalpj.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mobile.edu.finalpj.R;
import mobile.edu.finalpj.domain.Movie;
import mobile.edu.finalpj.repository.MovieRepo;

public class MovieDetailActivity extends AppCompatActivity {

    private EditText movieNameView;
    private EditText movieProducerView;
    private EditText movieDescriptionView;
    private Integer movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        movieId = Integer.parseInt(intent.getStringExtra(MovieActivity.MOVIE_ID));
        loadMovie(movieId);

    }

    private void loadMovie(Integer movieId) {
        Movie movie = MovieRepo.getInstance().findMovieById(movieId);
        movieDescriptionView = (EditText) findViewById(R.id.movieDescriptionText);
        movieProducerView = (EditText) findViewById(R.id.movieProducerText);
        movieNameView = (EditText) findViewById(R.id.movieTitleText);


        movieNameView.setText(movie.getName());
        movieProducerView.setText(movie.getProducer());
        movieDescriptionView.setText(movie.getDescription());
    }

    public void backToListView(View view) {
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

    public void persistMovie(View view) {
        String name = movieNameView.getText().toString();
        String producer = movieProducerView.getText().toString();
        String desc = movieDescriptionView.getText().toString();

        MovieRepo.getInstance().saveMovie(new Movie(movieId, name, producer, desc));
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

}
