package com.example.urlsaver;

public class UrlEntry {

    String description;
    long id;
    String link;
    long time;

    public UrlEntry() {

    }

    public UrlEntry(String description, long id, String link, long time) {
        this.description = description;
        this.id = id;
        this.link = link;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public long getTime() {
        return time;
    }

    public void setDescription(String despcription) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
