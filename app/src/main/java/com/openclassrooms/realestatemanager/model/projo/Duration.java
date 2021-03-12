package com.openclassrooms.realestatemanager.model.projo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by <NIATEL Brice> on <29/09/2020>.
 */
public class Duration {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private int value;

    public Duration(String text, int value) {
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
}
