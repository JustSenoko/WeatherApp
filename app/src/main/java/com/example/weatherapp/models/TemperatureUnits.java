package com.example.weatherapp.models;

public enum TemperatureUnits {
    C,
    F;

    public Double convert(Double value, TemperatureUnits unitFrom, TemperatureUnits unitTo) {
        if (unitFrom == unitTo) {
            return value;
        }
        if (unitTo == C && unitFrom == F) {
            return (value - 32) / 1.8;
        }
        if (unitFrom == C && unitTo == F) {
            return value * 1.8 + 32;
        }
        return value;
    }
}
