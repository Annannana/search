package com.cs125.personalaccountant.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
public class GeneralResponseModel implements ResponseModel {
    @JsonProperty(required = true) // Forces this field to be required in the JSON
    private int resultCode;
    @JsonProperty(required = true) // Forces this field to be required in the JSON
    private String message;
    private String sessionID;
    @JsonProperty(required = false)
    private ArrayList<TransactionRequestModel> transactions;
    @JsonProperty(required = false)
    private ExpectationRequestModel expectation;
    @JsonProperty(required = false)
    private RecommendationTimes recommendationTimes;
    @JsonProperty(required = false)
    private List<Store> stores;

    public GeneralResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                                @JsonProperty(value = "message", required = true) String message, String sessionID,
                                @JsonProperty(value = "stores", required = false) List<Store> stores) {
        this.resultCode = resultCode;
        this.message = message;
        this.sessionID = sessionID;
        this.stores = stores;
    }

    public GeneralResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                                @JsonProperty(value = "message", required = true) String message, String sessionID,
                                @JsonProperty(value = "recommendationTimes", required = false) RecommendationTimes recommendationTimes) {
        this.resultCode = resultCode;
        this.message = message;
        this.sessionID = sessionID;
        this.recommendationTimes = recommendationTimes;
    }

    @JsonCreator
    public GeneralResponseModel(
            @JsonProperty(value = "resultCode", required = true) int resultCode,
            @JsonProperty(value = "message", required = true) String message, String sessionID) {
        this.resultCode = resultCode;
        this.message = message;
        this.sessionID = sessionID;
    }

    public GeneralResponseModel(
            @JsonProperty(value = "resultCode", required = true) int resultCode,
            @JsonProperty(value = "message", required = true) String message, String sessionID,
            @JsonProperty(value = "transactions", required = false) ArrayList<TransactionRequestModel> transactions) {
        this.resultCode = resultCode;
        this.message = message;
        this.sessionID = sessionID;
        this.transactions = transactions;
    }

    public GeneralResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                                @JsonProperty(value = "message", required = true) String message, String sessionID,
                                @JsonProperty(value = "expectation", required = false) ExpectationRequestModel expectation) {
        this.resultCode = resultCode;
        this.message = message;
        this.sessionID = sessionID;
        this.expectation = expectation;
    }

    @JsonProperty(value = "recommendationTime", required = false)
    public RecommendationTimes getRecommendationTimes() {
        return recommendationTimes;
    }

    @JsonProperty(value = "stores", required = false)
    public List<Store> getStores() {
        return stores;
    }

    @JsonProperty(value = "transactions", required = false)
    public ArrayList<TransactionRequestModel> getTransactions() {
        return transactions;
    }

    @JsonProperty(value = "expectation", required = false)
    public ExpectationRequestModel getExpectation() {
        return expectation;
    }

    @JsonProperty("resultCode") // Forces Jackson to name the field "resultCode" when serializing this object
    public int getResultCode() {
        return resultCode;
    }

    @JsonProperty(value = "sessionId")
    public String getSessionID() {
        return sessionID;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("transactions")
    public void setTransactions(ArrayList<TransactionRequestModel> transactions) {
        this.transactions = transactions;
    }
}

