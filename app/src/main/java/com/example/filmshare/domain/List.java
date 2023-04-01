package com.example.filmshare.domain;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "list_table")
public class List {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private String description;
    private String language;




    public List(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }



    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }




}
