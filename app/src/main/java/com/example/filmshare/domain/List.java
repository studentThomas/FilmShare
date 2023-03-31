package com.example.filmshare.domain;

import androidx.room.PrimaryKey;

import androidx.room.Entity;

@Entity(tableName = "list_table")
public class List {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private String description;



    public List(String name, String description) {
        this.name = name;
        this.description = description;
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



}
