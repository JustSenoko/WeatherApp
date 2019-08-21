package com.example.weatherapp.database;

public class WeatherTable {
    /*private final static String TABLE_NAME = "weather";
    private final static String COLUMN_ID = "_date";
    private final static String COLUMN_DATE = "_date";
    private final static String COLUMN_CITY_ID = "city_id";
    private final static String COLUMN_TEMPERATURE = "temperature";

    static void createTable(SQLiteDatabase database) {
        String command = String.format("CREATE TABLE IF NOT EXISTS %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL);",
                TABLE_NAME, COLUMN_ID, COLUMN_DATE, COLUMN_CITY_ID, COLUMN_TEMPERATURE);
        database.execSQL(command);
    }

    public static void addWeather(WeatherItem city, SQLiteDatabase database) {
        ContentValues values = cityContentValues(city);
        database.insert(TABLE_NAME, null, values);
    }

    public static void editCity(int cityId, WeatherItem newCity, SQLiteDatabase database) {
        ContentValues values = cityContentValues(newCity);

        database.update(TABLE_NAME, values, COLUMN_ID + "=" + cityId, null);
        //database.update(TABLE_NAME, values, "%s = %s", new String[] {COLUMN_NOTE, String.valueOf(noteToEdit)});
//        database.execSQL("UPDATE " + TABLE_NAME + " set " + COLUMN_NOTE + " = " + newNote + "WHERE "
//                + COLUMN_NOTE + " = " + noteToEdit + ";");
    }

    private static ContentValues cityContentValues(WeatherItem city) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, city.id);
        values.put(COLUMN_NAME, city.name);
        values.put(COLUMN_COUNTRY, city.country);

        return values;
    }

    public static void deleteCity(int cityId, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_ID + " = " + cityId, null);
    }

    public static List<WeatherItem> getAllCities(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null, null, null,
                null, null, null);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResultFromCursor(cursor);
    }

    private static List<City> getResultFromCursor(Cursor cursor) {
        List<City> result = null;

        if(cursor != null && cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
            result = new ArrayList<>(cursor.getCount());

            int columnId = cursor.getColumnIndex(COLUMN_ID);
            int columnName = cursor.getColumnIndex(COLUMN_NAME);
            int columnCountry = cursor.getColumnIndex(COLUMN_COUNTRY);
            do {
                City city = new City(cursor.getString(columnName), cursor.getInt(columnName), cursor.getString(columnCountry));
                result.add(city);
            } while (cursor.moveToNext());
        }

        try { cursor.close(); } catch (Exception ignored) {}
        return result == null ? new ArrayList<City>(0) : result;
    }


}
}*/
}
