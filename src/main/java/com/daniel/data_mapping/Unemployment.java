package com.daniel.data_mapping;

public class Unemployment {

    private final String unemployment_rate;

    public Unemployment(String[] words) {
        this.unemployment_rate = words[1];
    }

    public String getUnemployment_rate() {
        return unemployment_rate;
    }

    @Override
    public String toString() {
        return "Unemployment{" +
                "unemployment_rate='" + unemployment_rate + '\'' +
                '}';
    }
}
