package com.cs125.personalaccountant.models;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public RecommendationTimes(@JsonProperty(value = "breackfast", required = true) Time breackfast,
                               @JsonProperty(value = "lunch", required = true) Time lunch,
                               @JsonProperty(value = "dinner", required = true) Time dinner) {
        this.breackfast = breackfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    @JsonProperty(value = "breackfast", required = true)
    public Time getBreackfast() {
        return breackfast;
    }

    @JsonProperty(value = "lunch", required = true)
    public Time getLunch() {
        return lunch;
    }

    @JsonProperty(value = "dinner", required = true)
    public Time getDinner() {
        return dinner;
    }
}
