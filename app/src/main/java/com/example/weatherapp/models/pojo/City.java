package com.example.weatherapp.models.pojo;

import java.io.Serializable;
import java.util.Objects;

public class City implements Serializable {

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

//    protected City(Parcel in) {
//        name = in.readString();
//        id = in.readInt();
//        country = in.readString();
//    }

//    public static final Creator<City> CREATOR = new Creator<City>() {
//        @Override
//        public City createFromParcel(Parcel in) {
//            return new City(in);
//        }
//
//        @Override
//        public City[] newArray(int size) {
//            return new City[size];
//        }
//    };

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeInt(id);
//        dest.writeString(country);
//    }
}
