package com.example.weatherapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.weatherapp.models.restEntities.City;

import java.util.ArrayList;
import java.util.List;

public class CitiesTable {
    final static String TABLE_NAME = "cities";
    final static String COLUMN_ID = "_id";
    final static String COLUMN_NAME = "name";
    final static String COLUMN_COUNTRY = "country";
    private final static String COLUMN_SELECTED = "selected";

    static void createTable(SQLiteDatabase database) {
        String command = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, " +
                        "%s TEXT NOT NULL, %s TEXT, %s INTEGER);",
                TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_COUNTRY, COLUMN_SELECTED);
        database.execSQL(command);
    }

    public static void addCity(City city, SQLiteDatabase database) {
        if (findCity(city.id, database)) {
            editCity(city.id, city, database);
        } else {
            ContentValues values = cityContentValues(city);
            database.insert(TABLE_NAME, null, values);
        }
    }

    private static boolean findCity(int cityId, SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, new String[] {COLUMN_ID}, COLUMN_ID + " = ?" ,
                new String[] {String.valueOf(cityId)},
                null, null, null);
        return isResultInCursor(cursor);
    }

    private static boolean isResultInCursor(Cursor cursor) {
        boolean result = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
                result = true;
            }
            cursor.close();
        }
        return result;
    }

    private static void editCity(int cityId, City newCity, SQLiteDatabase database) {
        ContentValues values = cityContentValues(newCity);
        database.update(TABLE_NAME, values, COLUMN_ID + "=" + cityId, null);
    }

    public static void deselectCity(City city, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SELECTED, 0);
        database.update(TABLE_NAME, values, COLUMN_ID + "=" + city.id, null);
    }

    private static ContentValues cityContentValues(City city) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, city.id);
        values.put(COLUMN_NAME, city.name);
        values.put(COLUMN_COUNTRY, city.country);
        values.put(COLUMN_SELECTED, 1);

        return values;
    }

    public static void deleteCity(int cityId, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_ID + " = " + cityId, null);
    }

    public static List<City> getSelectedCities(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_SELECTED + " = 1",
                null, null, null, null);

        return getResultFromCursor(cursor);
    }

    private static List<City> getResultFromCursor(Cursor cursor) {
        List<City> result = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
                result = new ArrayList<>(cursor.getCount());

                int columnId = cursor.getColumnIndex(COLUMN_ID);
                int columnName = cursor.getColumnIndex(COLUMN_NAME);
                int columnCountry = cursor.getColumnIndex(COLUMN_COUNTRY);
                do {
                    City city = new City(cursor.getString(columnName), cursor.getInt(columnId),
                            cursor.getString(columnCountry));
                    result.add(city);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return result == null ? new ArrayList<City>(0) : result;
    }
}

