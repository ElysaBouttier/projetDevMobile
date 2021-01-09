package com.example.todolist_bouttier_malherbe;

public class Event {
    private String name;
    private String date;
    private String id;

    public Event(String name, String date, String id) {
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) { this.date = date; }
}
