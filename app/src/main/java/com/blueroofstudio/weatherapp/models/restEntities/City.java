package com.blueroofstudio.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings({"unused"})
public class City implements Serializable {

    @SerializedName("name") public String name;
    @SerializedName("id") public int id;
    @SerializedName("country") public String country;

    public City() {}

    public City(String name, int id, String country) {
        this.name = name;
        this.id = id;
        this.country = country;
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
