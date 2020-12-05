package com.example.todolist_bouttier_malherbe;

public class Event {
    private String name;
    private String date;
    private String path;

    public Event(String name, String date, String path) {
        this.name = name;
        this.date = date;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) { this.date = date; }
}
