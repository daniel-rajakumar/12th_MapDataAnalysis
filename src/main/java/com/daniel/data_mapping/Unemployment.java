package com.daniel.data_mapping;

public class Unemployment {

    private final float unemployment_rate;

    public Unemployment(String[] words) {
        this.unemployment_rate = cleanUpString(words[1]);
    }

    public float getUnemployment_rate() {
        return unemployment_rate;
    }

    public float cleanUpString(String word){
        return Float.parseFloat(word.substring(0, word.length() - 1));
    }

    @Override
    public String toString() {
        return "Unemployment{" +
                "unemployment_rate='" + unemployment_rate + '\'' +
                '}';
    }
}
