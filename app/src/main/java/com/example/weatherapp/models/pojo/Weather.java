package com.example.weatherapp.models.pojo;

@SuppressWarnings("unused")
public class Weather {
    private String main;
    private String description;
    private int id;
    private String icon;

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }
}
