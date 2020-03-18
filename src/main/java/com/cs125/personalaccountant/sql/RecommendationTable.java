package com.cs125.personalaccountant.sql;

import com.cs125.personalaccountant.logic.Recommendation;
import com.cs125.personalaccountant.models.ExpectationRequestModel;
import com.cs125.personalaccountant.utils.APIUtils;
import com.cs125.personalaccountant.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecommendationTable {
    public static void insertRecommendation(Recommendation recommendation) {
        String query = "insert into recommendations(categoryId, email, rtime, recommendationStatusId, budget) value ("
                + recommendation.getCategoryId() + ",\"" + recommendation.getEmail() + "\", \"" + recommendation.getTime()
                + "\"," + recommendation.getRecommendationStatusId() + "," +recommendation.getBudget() + ");";
        SqlUtils.insert(query);
    }

    public static void retrieveRecommendation(Recommendation recommendation){
        String query = "select * from recommendations where email=\"" + recommendation.getEmail() + "\" and categoryId="
                + recommendation.getCategoryId() + " and recommendationStatusId=" + recommendation.getRecommendationStatusId();
        ResultSet resultSet = SqlUtils.view(query);
        try {
            if (resultSet.next()) {
                recommendation.setBudget(resultSet.getInt("budget"));
                recommendation.setRecommendationStatusId(APIUtils.RECOMMENDATION_CLOSED);
                recommendation.setTime(resultSet.getTime("rtime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
