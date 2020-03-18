package com.cs125.personalaccountant.logic;

import com.cs125.personalaccountant.utils.APIUtils;

import java.sql.Time;
import java.sql.Timestamp;

public class Recommendation {
    int categoryId;
    String email;
    Time time;
    int recommendationStatusId;
    int budget;

    public Recommendation(String email, int categoryId) {
        this.categoryId = categoryId;
        this.email = email;
        this.recommendationStatusId = APIUtils.RECOMMENDATION_WAITING;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getEmail() {
        return email;
    }

    public Time getTime() {
        return time;
    }

    public int getRecommendationStatusId() {
        return recommendationStatusId;
    }

    public int getBudget() {
        return budget;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setRecommendationStatusId(int recommendationStatusId) {
        this.recommendationStatusId = recommendationStatusId;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
