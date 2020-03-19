package com.cs125.personalaccountant.models;

import java.sql.Time;

public class Store {
    int storeId;
    String address;
    String name;
    String phone;
    Time openTime;
    Time closeTime;
    double longitude;
    double latitude;
    float rating;
    String description;
    int averageSpending;
    float distance;

    public Store(int storeId, String address, String name, String phone, Time openTime, Time closeTime, double longitude, double latitude, float rating, String description, int averageSpending, float distance) {
        this.storeId = storeId;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rating = rating;
        this.description = description;
        this.averageSpending = averageSpending;
        this.distance = distance;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public float getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public int getAverageSpending() {
        return averageSpending;
    }

    public float getDistance() {
        return distance;
    }
}
