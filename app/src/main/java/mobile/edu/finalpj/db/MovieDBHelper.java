package mobile.edu.finalpj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static mobile.edu.finalpj.db.DBContract.*;

/**
 * Created by ysyh on 30.11.2016.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "movie_db";
    private static final int DATABASE_VERSION = 1;

    //create db query
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieTable.TABLE_NAME + " (" +
                    MovieTable._ID + " INTEGER PRIMARY KEY," +
                    MovieTable.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    MovieTable.COLUMN_PRODUCER + TEXT_TYPE + COMMA_SEP +
                    MovieTable.COLUMN_NAME + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieTable.TABLE_NAME;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
