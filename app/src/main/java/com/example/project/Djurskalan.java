package com.example.project;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

//import java.io.Serial;

public class Djurskalan {
    @SerializedName("ID")
    public String id;
    @SerializedName("name")
    public String name;
    public String type;
    public String category;
    public int size;

    public Djurskalan() {
        name = "Namn: ";
    }

    public Djurskalan(String name) {
        this.name = name;
    }

    public String getTitle() {
        return name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}