package com.example.homework_three;

import java.io.Serializable;

public class NotesModel implements Serializable {
    private String title, description, dateTime;

    public NotesModel(String title, String description, String dateTime){
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public String getDate(){ return dateTime; }
}
