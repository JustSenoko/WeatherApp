package com.blueroofstudio.weatherapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blueroofstudio.weatherapp.models.WeatherItem;
import com.blueroofstudio.weatherapp.models.restEntities.City;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherTable {
    private final static String TABLE_NAME = "weather";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_DATE = "date_txt";
    private final static String COLUMN_CITY_ID = "city_id";
    private final static String COLUMN_TEMPERATURE = "temperature";
    private final static String COLUMN_PRESSURE = "pressure";
    private final static String COLUMN_HUMIDITY = "humidity";
    private final static String COLUMN_WIND = "wind";
    private final static String COLUMN_ICON = "icon";

    private final static String DATE_FORMAT_SQL = "yyyy-MM-dd hh:mm:ss.sss";
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    static void createTable(SQLiteDatabase database) {
        /*CREATE TABLE weather (
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                city_id     INTEGER NOT NULL,
                temperature INTEGER NOT NULL,
                date        TEXT    NOT NULL
        );*/
        String command = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, %s INTEGER NOT NULL, %s TEXT);",
                TABLE_NAME, COLUMN_ID, COLUMN_DATE,
                COLUMN_CITY_ID, COLUMN_TEMPERATURE, COLUMN_PRESSURE,
                COLUMN_HUMIDITY, COLUMN_WIND, COLUMN_ICON);
        database.execSQL(command);
    }

    public static void addWeather(int cityId, List<WeatherItem> items, SQLiteDatabase database) {
        deleteCityWeather(cityId, database);
        for (WeatherItem item : items) {
            ContentValues values = weatherContentValues(item);
            database.insert(TABLE_NAME, null, values);
        }
    }

    private static ContentValues weatherContentValues(WeatherItem weather) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, getLocalDateFromUTC(weather.getDate()));
        values.put(COLUMN_CITY_ID, weather.getCity().id);
        values.put(COLUMN_TEMPERATURE, weather.getTemperature());
        values.put(COLUMN_PRESSURE, weather.getPressure());
        values.put(COLUMN_HUMIDITY, weather.getHumidity());
        values.put(COLUMN_WIND, weather.getWind());
        values.put(COLUMN_ICON, weather.getWeatherIcon());

        return values;
    }

    private static String getLocalDateFromUTC(Date dateUTC) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_SQL, Locale.getDefault());
        dateFormatter.setTimeZone(timeZone);
        return dateFormatter.format(dateUTC);
    }

    private static void deleteCityWeather(int cityId, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_CITY_ID + " = " + cityId, null);
    }

    public static List<WeatherItem> getDailyTemperatureForecast(int cityId, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("select\n" +
                "date(" + TABLE_NAME + "." + COLUMN_DATE + ") AS " + COLUMN_DATE + ",\n" +
                " AVG(" + TABLE_NAME + "." + COLUMN_TEMPERATURE + ") AS " + COLUMN_TEMPERATURE + ",\n" +
                " AVG(" + TABLE_NAME + "." + COLUMN_PRESSURE + ") AS " + COLUMN_PRESSURE + ",\n" +
                " AVG(" + TABLE_NAME + "." + COLUMN_HUMIDITY + ") AS " + COLUMN_HUMIDITY + ",\n" +
                " AVG(" + TABLE_NAME + "." + COLUMN_WIND + ") AS " + COLUMN_WIND + ",\n" +
                " MAX(" + TABLE_NAME + "." + COLUMN_ICON + ") AS " + COLUMN_ICON + ",\n" +
                CitiesTable.TABLE_NAME + "." + CitiesTable.COLUMN_ID + " AS " + CitiesTable.COLUMN_ID + ",\n" +
                CitiesTable.TABLE_NAME + "." + CitiesTable.COLUMN_NAME + " AS " + CitiesTable.COLUMN_NAME + ",\n" +
                CitiesTable.TABLE_NAME + "." + CitiesTable.COLUMN_COUNTRY + " AS " + CitiesTable.COLUMN_COUNTRY + "\n" +
                " FROM " + TABLE_NAME + " left join " + CitiesTable.TABLE_NAME + "\n" +
                " ON " + TABLE_NAME + "." + COLUMN_CITY_ID + " = " + CitiesTable.TABLE_NAME + "." + CitiesTable.COLUMN_ID + "\n" +
                " WHERE " + TABLE_NAME + "." + COLUMN_CITY_ID + " = ?\n" +
                " AND date(" + TABLE_NAME + "." + COLUMN_DATE + ") > date('now')\n" +
                " GROUP BY date(" + TABLE_NAME + "." + COLUMN_DATE + ")", new String[]{String.valueOf(cityId)});
        return getResultFromCursor(cursor);
    }

    private static List<WeatherItem> getResultFromCursor(Cursor cursor) {
        List<WeatherItem> result = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
                result = new ArrayList<>(cursor.getCount());

                int columnDate = cursor.getColumnIndex(COLUMN_DATE);
                int columnTemperature = cursor.getColumnIndex(COLUMN_TEMPERATURE);
                int columnPressure = cursor.getColumnIndex(COLUMN_PRESSURE);
                int columnHumidity = cursor.getColumnIndex(COLUMN_HUMIDITY);
                int columnWind = cursor.getColumnIndex(COLUMN_WIND);
                int columnIcon = cursor.getColumnIndex(COLUMN_ICON);
                int columnId = cursor.getColumnIndex(CitiesTable.COLUMN_ID);
                int columnName = cursor.getColumnIndex(CitiesTable.COLUMN_NAME);
                int columnCountry = cursor.getColumnIndex(CitiesTable.COLUMN_COUNTRY);

                City city = null;
                do {
                    if (city == null) {
                        city = new City(cursor.getString(columnName), cursor.getInt(columnId),
                                cursor.getString(columnCountry));
                    }
                    WeatherItem weatherItem = new WeatherItem(parseDate(cursor.getString(columnDate)),
                            city,
                            cursor.getInt(columnTemperature),
                            cursor.getInt(columnPressure),
                            cursor.getInt(columnHumidity),
                            cursor.getInt(columnWind),
                            cursor.getString(columnIcon));
                    result.add(weatherItem);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return result == null ? new ArrayList<WeatherItem>(0) : result;
    }

    private static Date parseDate(String dateTxt) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return formatter.parse(dateTxt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    static void onUpgrade(int oldVersion, SQLiteDatabase database) {
        if (oldVersion == 2) {
            database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_ICON
                    + " TEXT");
        }
    }
}

