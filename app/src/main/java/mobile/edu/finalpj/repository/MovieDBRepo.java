package mobile.edu.finalpj.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mobile.edu.finalpj.db.MovieDBHelper;
import mobile.edu.finalpj.domain.Movie;

import static mobile.edu.finalpj.db.DBContract.MovieTable;
import static mobile.edu.finalpj.db.DBContract.MovieTable.COLUMN_NAME;

/**
 * Created by ysyh on 1/19/17.
 */

public class MovieDBRepo {

    private static final
    String[] MOVIE_PROJECTION = {
            MovieTable._ID,
            MovieTable.COLUMN_DESCRIPTION,
            MovieTable.COLUMN_NAME,
            MovieTable.COLUMN_PRODUCER,
    };

    private MovieDBHelper dbHelper;

    public MovieDBRepo(Context context) {
        this.dbHelper = new MovieDBHelper(context);
    }

    public Movie save(Movie movie) {
        if (movie.getKey() != null) {
            delete(movie.getKey());
        }

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, movie.getName());
        values.put(MovieTable.COLUMN_PRODUCER, movie.getProducer());
        values.put(MovieTable.COLUMN_DESCRIPTION, movie.getDescription());

        // Insert the new row, returning the primary key value of the new row
        int newRowId = (int) db.insert(MovieTable.TABLE_NAME, null, values);

        return new Movie(String.valueOf(newRowId), movie.getName(), movie.getProducer(), movie.getDescription());

    }

    public void close() {
        dbHelper.close();
    }

    public Movie getById(String  id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = MovieTable._ID + " = ?";
        String[] args = {String.valueOf(id)};

        Cursor cursor = db.query(
                MovieTable.TABLE_NAME,                     // The table to query
                MOVIE_PROJECTION,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                args,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (cursor.moveToNext()) {
            return readMovie(cursor);
        }
        return null;
    }

    public Movie readMovie(Cursor cursor) {
        int id = (int) cursor.getLong(cursor.getColumnIndexOrThrow(MovieTable._ID));
        String desc = cursor.getString(cursor.getColumnIndex(MovieTable.COLUMN_DESCRIPTION));
        String name = cursor.getString(cursor.getColumnIndex(MovieTable.COLUMN_NAME));
        String producer = cursor.getString(cursor.getColumnIndex(MovieTable.COLUMN_PRODUCER));

        return new Movie(String.valueOf(id), desc, name, producer);
    }

    public void delete(String key) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = MovieTable._ID + " = ?";
        String[] args = {String.valueOf(key)};

        db.delete(MovieTable.TABLE_NAME, selection, args);
    }

    public List<Movie> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                MovieTable.TABLE_NAME,                     // The table to query
                MOVIE_PROJECTION,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()) {
            movies.add(readMovie(cursor));
        }
        cursor.close();
        return movies;
    }

}
