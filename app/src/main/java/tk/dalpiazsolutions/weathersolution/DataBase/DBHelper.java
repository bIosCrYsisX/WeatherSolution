package tk.dalpiazsolutions.weathersolution.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tk.dalpiazsolutions.weathersolution.DataBase.Contracts.DBContract;
import tk.dalpiazsolutions.weathersolution.DataBase.Contracts.PlaceContract;

/**
 * Created by Christoph on 05.04.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PlaceContract.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
