package com.example.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings({"unused"})
public class City implements Serializable {

    @SerializedName("name") public final String name;
    @SerializedName("id") public final int id;
    @SerializedName("country") public final String country;

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
