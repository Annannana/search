package com.cs125.personalaccountant.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
public class TransactionRequestModel implements RequestModel{
    @JsonProperty(value = "amount", required = true)
    int amount;
    @JsonProperty(value = "details", required = false)
    String details;
    @JsonProperty(value = "categoryId", required = true)
    int categoryId;
    @JsonProperty(value = "longitude", required = false)
    double longitude;
    @JsonProperty(value = "latitude", required = false)
    double latitude;
    String email;
    Timestamp createTime;
    int expectationId;
    int transactionId;

    @JsonCreator
    public TransactionRequestModel(
            @JsonProperty(value = "amount", required = true) int amount,
            @JsonProperty(value = "details", required = false)String details,
            @JsonProperty(value = "categoryId", required = true) int categoryId,
            @JsonProperty(value = "longitude", required = false) double longitude,
            @JsonProperty(value = "latitude", required = false) double latitude) {
        this.amount = amount;
        this.details = details;
        this.categoryId = categoryId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public TransactionRequestModel(int transactionId, String email, Timestamp createTime, int amount, String details, int categoryId, double longitude, double latitude,  int expectationId) {
        this.amount = amount;
        this.details = details;
        this.categoryId = categoryId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.email = email;
        this.createTime = createTime;
        this.expectationId = expectationId;
        this.transactionId = transactionId;
    }

    @JsonProperty("amount")
    public int getAmount() {
        return amount;
    }

    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    @JsonProperty("categoryId")
    public int getCategoryId() {
        return categoryId;
    }

    @JsonProperty( "longitude")
    public double getLongitude() {
        return longitude;
    }

    @JsonProperty( "latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public int getExpectationId() {
        return expectationId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setExpectationId(int expectationId) {
        this.expectationId = expectationId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

}
