package com.example.carbonfootprint;

public class DataItem {
    private String title;
    private String description;

    public DataItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}