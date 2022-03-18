package com.daniel.data_mapping;

public class Unemployment {

    private final float unemployment_rate;

    public Unemployment(String[] words) {
        this.unemployment_rate = cleanUpString(words[1]);
    }

    public float getUnemployment_rate() {
        return unemployment_rate;
    }

    // remove percentage symbol from the string
    public float cleanUpString(String word){
        return Float.parseFloat(word.substring(0, word.length() - 1));
    }

}
