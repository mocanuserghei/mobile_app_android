package mobile.edu.finalpj.db;

import android.provider.BaseColumns;

/**
 * Created by ysyh on 30.11.2016.
 */

public final class DBContract {

    private DBContract(){}

    public static class MovieTable implements BaseColumns{
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRODUCER = "producer";
        public static final String COLUMN_DESCRIPTION = "description";
    }

}
