package com.example.project;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

//import java.io.Serial;

public class TestClass {
    @SerializedName("ID")
    public String id;
    @SerializedName("name")
    public String name;
    public String type;
    public String company;
    public String location;
    public String category;
    @SerializedName("size")
    public int meters;
    @SerializedName("cost")
    public int feet;

    public TestClass(){
        name="Namn: ";
    }

    public TestClass(String name){
        this.name=name;
    }

    public String getTitle(){
        return name;
    }

    public String getName(){
        return name;
    }
    @NonNull
    @Override
    public String toString(){
        return name;
    }



    /*{
    "ID": "mobilprog_kinnekulle",
    "name": "Kinnekulle",
    "type": "brom",
    "company": "",
    "location": "Skaraborg",
    "category": "",
    "size": 306,
    "cost": 1004,
    }*/
}