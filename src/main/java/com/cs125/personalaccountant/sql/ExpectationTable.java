package com.cs125.personalaccountant.sql;

import com.cs125.personalaccountant.models.ExpectationRequestModel;
import com.cs125.personalaccountant.utils.APIUtils;
import com.cs125.personalaccountant.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpectationTable {
    public static ExpectationRequestModel viewExpectation(String email) {
        String query = "select * from expectations where email=\"" + email+ "\" and expectationStatusId=" + APIUtils.EXPECTATION_ACTIVE;
        ResultSet resultSet = SqlUtils.view(query);
        ExpectationRequestModel expectationRequestModel = null;
        try {
            if (resultSet.next()) {
                expectationRequestModel = new ExpectationRequestModel(
                        resultSet.getInt("expectationId"),
                        resultSet.getInt("expected"),
                        resultSet.getInt("spent"),
                        resultSet.getString("email"),
                        resultSet.getTimestamp("startTime"),
                        resultSet.getTimestamp("endTime"),
                        resultSet.getInt("expectationStatusId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expectationRequestModel;
    }

    public static void updateExpectation(ExpectationRequestModel expectationRequestModel) {
        String query = "update expectations set spent=" + expectationRequestModel.getSpent() + ", expected=" + expectationRequestModel.getExpected() + " where expectationId = " + expectationRequestModel.getExpectationId();
        SqlUtils.update(query);
    }

    public static void updateExpectationStatus(String email) {
        String query = "update expectations set expectationStatusId=" + APIUtils.EXPECTATION_EXPIRED + " where email = \"" + email + "\" and expectationStatusId=" + APIUtils.EXPECTATION_ACTIVE;
        SqlUtils.update(query);
    }

    public static void insertExpectation(ExpectationRequestModel requestModel, String email) {
        String query = "insert into expectations(expected,spent,email,startTime,endTime,expectationStatusId) value\n" +
                "( " + requestModel.getExpected()+ ", 0, \"" + email + "\",\"" + requestModel.getStartTime() + "\", \"" + requestModel.getEndTime() + "\", " + APIUtils.EXPECTATION_ACTIVE +")";
        SqlUtils.insert(query);
    }
}
