package mobile.edu.finalpj.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mobile.edu.finalpj.R;
import mobile.edu.finalpj.domain.Movie;
import mobile.edu.finalpj.repository.MovieDBRepo;

import static mobile.edu.finalpj.domain.Movie.counter;

public class MovieDetailActivity extends AppCompatActivity {

    private EditText movieNameView;
    private EditText movieProducerView;
    private EditText movieDescriptionView;
    private String movieId;

    private MovieDBRepo dbRepo;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("movies");
        databaseReference.keepSynced(true);

        dbRepo = new MovieDBRepo(getApplicationContext());
        movieDescriptionView = (EditText) findViewById(R.id.movieDescriptionText);
        movieProducerView = (EditText) findViewById(R.id.movieProducerText);
        movieNameView = (EditText) findViewById(R.id.movieTitleText);

        if (intent.getStringExtra(MovieActivity.MOVIE_ID) != null) {
            movieId = String.valueOf(Integer.parseInt(intent.getStringExtra(MovieActivity.MOVIE_ID)));
            loadMovie(movieId);
            return;
        }
        findViewById(R.id.deleteBtn).setVisibility(View.GONE);
    }

    private void loadMovie(String  movieId) {
        Movie movie = dbRepo.getById(movieId);

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

        String key = databaseReference.push().getKey();

        if (movieId != null) {
            Movie movie = dbRepo.getById(movieId);
            movie.setName(name);
            movie.setDescription(desc);
            movie.setProducer(producer);
            dbRepo.save(movie);

            databaseReference.child(key).setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Saved to DB", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            databaseReference.child(key).setValue(new Movie(key, name, producer, desc))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Saved to DB", Toast.LENGTH_SHORT).show();
                        }
                    });
            dbRepo.save(new Movie(String.valueOf(counter++), name, producer, desc));
        }

        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

    public void signOut(View view) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        AccessToken.setCurrentAccessToken(null);
        Intent intent = new Intent(this, FacebookLoginExample.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void delete(View view) {
        if (movieId != null) {
            dbRepo.delete(movieId);
            Intent intent = new Intent(this, MovieActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Can not delete", Toast.LENGTH_LONG).show();
        }
    }

}
