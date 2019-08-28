package com.blueroofstudio.weatherapp.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class WeatherApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
