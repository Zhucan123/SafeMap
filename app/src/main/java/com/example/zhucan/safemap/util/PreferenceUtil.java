package com.example.zhucan.safemap.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhucan on 2017/2/26.
 */

public class PreferenceUtil {

    private static String CITY_ID = "cityId";
    private static String FILE_NAME = "myData";
    private static String WEATHER_DATA = "weatherData";
    private static String CITY_NAME = "myCityName";
    private static String NOW_DATA = "myNowData";
    private static String CITY_COORD_X = "myCoord1";
    private static String CITY_COORD_Y = "myCoord2";

    private static SharedPreferences preferences = ApplicationContext.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = preferences.edit();

    public static void inCreaseCityId(String cityId) {
        editor.putString(CITY_ID, cityId);
        editor.commit();
    }

    public static String getCityId() {
        return preferences.getString(CITY_ID, null);
    }

    public static void inCreaseWeather(String data) {
        editor.putString(WEATHER_DATA, data);
        editor.commit();
    }

    public static void setCityName(String name) {
        editor.putString(CITY_NAME, name);
        editor.commit();
    }

    public static String getCityName() {
        return preferences.getString(CITY_NAME, null);
    }

    public static String getWeatherData() {
        return preferences.getString(WEATHER_DATA, null);
    }

    public static void setNowData(String data) {
        editor.putString(NOW_DATA, data);
        editor.commit();
    }

    public static String getNowData() {
        return preferences.getString(NOW_DATA, null);
    }

    public static float getCityCoordX() {
        return preferences.getFloat(CITY_COORD_X, 0);
    }

    public static void inCreaseCityCoordX(float X) {
        editor.putFloat(CITY_COORD_X, X);
        editor.commit();
    }

    public static float getCityCoordY() {
        return preferences.getFloat(CITY_COORD_Y, 0);
    }

    public static void inCreaseCityCoordY(float Y) {
        editor.putFloat(CITY_COORD_Y, Y);
        editor.commit();
    }


}


