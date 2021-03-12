package com.openclassrooms.realestatemanager.model.projo;


import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by <NIATEL Brice> on <29/09/2020>.
 */
public class Distance {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private int value;

    public Distance(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "Distance{" +
                "text='" + text + '\'' +
                ", value=" + value +
                '}';
    }
}
