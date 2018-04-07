package tk.dalpiazsolutions.weathersolution.DataBase.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import tk.dalpiazsolutions.weathersolution.DataBase.Contracts.PlaceContract;
import tk.dalpiazsolutions.weathersolution.DataBase.DBHelper;
import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;

/**
 * Created by Christoph on 05.04.2018.
 */

public class PlaceRepository implements Repository<Place> {

    private static PlaceRepository instance;

    public static PlaceRepository getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new PlaceRepository(context);
        }
        return instance;
    }

    private DBHelper dbHelper;

    private PlaceRepository(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    @Override
    public List<Place> getAll() {
        List<Place> result = new LinkedList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                PlaceContract.TABLE,
                new String[] {
                        PlaceContract.Columns._ID,
                        PlaceContract.Columns.PLACE
                },
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst())
        {
            do
            {
                result.add(fromCursor(cursor));
            }
            while(cursor.moveToNext());
        }

        return result;
    }

    @Override
    public void insert(Place entity) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PlaceContract.Columns.PLACE, entity.getPlace());

        database.insert(PlaceContract.TABLE, null, contentValues);
    }

    @Override
    public void deleteByName(String name) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Log.e("sql", "DELETE FROM " + "CONTRACT" + " WHERE " + "Place" + " LIKE " + "'%" + name + "%'");
        database.execSQL("DELETE FROM " + PlaceContract.TABLE + " WHERE " + PlaceContract.Columns.PLACE + " LIKE " + "'%" + name + "%'");
    }

    @Override
    public void dropTable() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL(PlaceContract.DROP);
        database.execSQL(PlaceContract.CREATE);
    }

    @Override
    public Place find(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                PlaceContract.TABLE,
                new String[]{
                        PlaceContract.Columns._ID,
                        PlaceContract.Columns.PLACE
                },
                PlaceContract.Columns._ID + " = ?",
                new String[]{
                        String.valueOf(id)
                },
                null, null, null
        );

        if(cursor.moveToFirst())
        {
            return fromCursor(cursor);
        }

        return null;
    }

    @Override
    public void update(Place entity) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PlaceContract.Columns.PLACE, entity.getPlace());

        database.update(PlaceContract.TABLE, contentValues, PlaceContract.Columns._ID + " = ?", new String[] {
                String.valueOf(entity.getId())
        });
    }

    private Place fromCursor(Cursor cursor)
    {
        int id = cursor.getInt(cursor.getColumnIndex(PlaceContract.Columns._ID));
        String place = cursor.getString(cursor.getColumnIndex(PlaceContract.Columns.PLACE));

        return new Place(id, place);
    }
}
