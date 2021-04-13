package com.pascal.notes_java.model;

public class CardData{
    String title, description, date, id;

    public CardData(String title, String description, String date, String id) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}

