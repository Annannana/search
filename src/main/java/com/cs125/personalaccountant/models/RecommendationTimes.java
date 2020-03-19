package com.cs125.personalaccountant.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.sql.Timestamp;

public class RecommendationTimes {
    @JsonProperty(value = "breackfast", required = true)
    Time breackfast;
    @JsonProperty(value = "lunch", required = true)
    Time lunch;
    @JsonProperty(value = "dinner", required = true)
    Time dinner;

    public RecommendationTimes(@JsonProperty(value = "breackfast", required = true) Time breackfast,
                               @JsonProperty(value = "lunch", required = true) Time lunch,
                               @JsonProperty(value = "dinner", required = true) Time dinner) {
        this.breackfast = breackfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
}
