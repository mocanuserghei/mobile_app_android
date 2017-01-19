package mobile.edu.finalpj.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mobile.edu.finalpj.R;
import mobile.edu.finalpj.domain.Movie;
import mobile.edu.finalpj.repository.MovieDBRepo;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "mobile.edu.finalpj/.activities.movieId";
    public MovieDBRepo dbRepo;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        dbRepo = new MovieDBRepo(getApplicationContext());

        final ListView movieListView = (ListView) findViewById(R.id.mylist);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("movies");
//        databaseReference.keepSynced(true);

        final List<Movie> test = new ArrayList<>();
        final List<String> test1 = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.d("", "datasnapshot" + dataSnapshot);
                for (DataSnapshot child : children) {
                    String value = child.getValue(String.class);
                    test1.add(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbRepo.getAll());
        movieListView.setAdapter(adapter);

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                Movie item = (Movie) adapterView.getItemAtPosition(i);
                openMovieDetailActivity(item.getKey());
            }
        });

    }

    public void addNewMovie(View view) {
        startActivity(new Intent(this, MovieDetailActivity.class));
    }

    public void openMovieDetailActivity(String  movieId) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    public void signOut(View view) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        AccessToken.setCurrentAccessToken(null);
        Intent intent = new Intent(this, FacebookLoginExample.class);
        startActivity(intent);
    }
}


