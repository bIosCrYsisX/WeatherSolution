package tk.dalpiazsolutions.weathersolution.DataBase.Contracts;

import android.provider.BaseColumns;

/**
 * Created by Christoph on 05.04.2018.
 */

public class PlaceContract {
    public static final String TABLE = "CONTRACT";
    public static final String CREATE =
            "CREATE TABLE " + TABLE + "(" +
                    Columns._ID + " INTEGER PRIMARY KEY," +
                    Columns.PLACE + " TEXT NOT NULL" +
                    ");";

    public static final String DROP ="DROP TABLE IF EXISTS " + TABLE;
    public class Columns implements BaseColumns {
        public static final String PLACE = "Place";
    }
}
