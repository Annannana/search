package com.cs125.personalaccountant.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
public class RecommendationResponseModel implements ResponseModel {
    @JsonProperty(required = true) // Forces this field to be required in the JSON
    private int resultCode;
    @JsonProperty(required = true) // Forces this field to be required in the JSON
    private String message;
    @JsonProperty(required = false)
    private String sessionID;
    @JsonProperty(required = false)
    private RecommendationTimes recommendationTimes;

    @JsonCreator
    public RecommendationResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                                @JsonProperty(value = "message", required = true) String message, String sessionID,
                                @JsonProperty(value = "recommendationTimes", required = false) RecommendationTimes recommendationTimes) {
        this.resultCode = resultCode;
        this.message = message;
        this.sessionID = sessionID;
        this.recommendationTimes = recommendationTimes;
    }

    @JsonProperty(value = "recommendationTime", required = false)
    public RecommendationTimes getRecommendationTimes() {
        return recommendationTimes;
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

}

