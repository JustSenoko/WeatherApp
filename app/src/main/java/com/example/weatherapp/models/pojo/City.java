package com.example.weatherapp.models.pojo;

import java.util.Objects;

public class City {

    private final String name;
    private final int id;
    private final String country;

    public City(String name, int id, String country) {
        this.name = name;
        this.id = id;
        this.country = country;
    }

    public City(String name, int id) {
        this.name = name;
        this.id = id;
        this.country = null;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
                name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
