package mobile.edu.finalpj.repository;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

import mobile.edu.finalpj.db.MovieDBHelper;

import static mobile.edu.finalpj.db.DBContract.MovieTable;


/**
 * Created by ysyh on 30.11.2016.
 */

public class MovieContentProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "movie_provider";
    private static final String URL = "content://" + PROVIDER_NAME + "movies";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;
    private static final UriMatcher uriMatcher;
    private SQLiteDatabase db;
    private static HashMap<String, String> MOVIES_PROJECTION_MAP;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIES_ID);
    }

    @Override
    public boolean onCreate() {
        MovieDBHelper dbHelper = new MovieDBHelper(getContext());
        //Create a writable db which will trigger
        // its creation if it doesn't exist
        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MovieTable.TABLE_NAME);

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.example.movies";
            case MOVIES_ID:
                return "vnd.android.cursor.dir/vnd.example.movie";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri _uri = null;

        //add new record
        long rowId = db.insert(MovieTable.TABLE_NAME, "", contentValues);

        //notify for changes
        if (rowId > 0){
            _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
        }
        return _uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
