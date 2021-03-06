package com.cs125.personalaccountant.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
public class RecommendationResponseModel implements ResponseModel {
    @JsonProperty(required = true) // Forces this field to be required in the JSON
    private int resultCode;
    @JsonProperty(required = true) // Forces this field to be required in the JSON
    private String message;
    private RecommendationTimes recommendationTimes;
    @JsonProperty(required = false)
    private List<Store> stores;

    public RecommendationResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                                @JsonProperty(value = "message", required = true) String message,
                                @JsonProperty(value = "stores", required = false) List<Store> stores) {
        this.resultCode = resultCode;
        this.message = message;
        this.stores = stores;
    }

    @JsonCreator
    public RecommendationResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                                @JsonProperty(value = "message", required = true) String message, int i,
                                @JsonProperty(value = "recommendationTimes", required = false) RecommendationTimes recommendationTimes) {
        this.resultCode = resultCode;
        this.message = message;
        this.recommendationTimes = recommendationTimes;
    }



    @JsonProperty(value = "recommendationTime", required = false)
    public RecommendationTimes getRecommendationTimes() {
        return recommendationTimes;
    }

    @JsonProperty(value = "stores", required = false)
    public List<Store> getStores() {
        return stores;
    }

    @JsonProperty("resultCode") // Forces Jackson to name the field "resultCode" when serializing this object
    public int getResultCode() {
        return resultCode;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}

