package com.example.todolist_bouttier_malherbe;

public class EventDetails {
    private String firstname;
    private String information;
    private String id;
    private String path;
    private String checked;

    public EventDetails(String firstname, String information, String id, String checked, String path) {
        this.firstname = firstname;
        this.information = information;
        this.id = id;
        this.checked = checked;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getId() {
        return id;
    }

    public String getInformation() {
        return information;
    }

    public String getChecked() {
        return checked;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
