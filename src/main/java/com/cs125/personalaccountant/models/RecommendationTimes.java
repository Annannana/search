package com.cs125.personalaccountant.models;

import java.sql.Timestamp;

public class RecommendationTimes {
    Timestamp breackfast;
    Timestamp lunch;
    Timestamp dinner;

    public RecommendationTimes(Timestamp breackfast, Timestamp lunch, Timestamp dinner) {
        this.breackfast = breackfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
}
