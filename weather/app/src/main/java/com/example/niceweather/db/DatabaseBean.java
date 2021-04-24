package com.example.niceweather.db;

public class DatabaseBean {
    private int id;
    private String city;
    private String data;

    public DatabaseBean() {
    }

    public DatabaseBean(int id, String city, String data) {
        this.id = id;
        this.city = city;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
