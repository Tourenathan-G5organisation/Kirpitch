package com.example.android.kirpitch.model;

public class Task {

    private String title;
    private String companyName;
    private String location;
   private long date;
    private int status;
    private String otherInfo;
    private String link;

    public Task() {
    }

    public Task(String title, String companyName, String location, long date, int status, String otherInfo, String link) {
        this.title = title;
        this.companyName = companyName;
        this.location = location;
        this.date = date;
        this.status = status;
        this.otherInfo = otherInfo;
        this.link = link;
    }

    public Task(String title, String companyName, String location, long date, int status) {
        this.title = title;
        this.companyName = companyName;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
