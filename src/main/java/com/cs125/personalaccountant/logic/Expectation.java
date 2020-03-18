package com.cs125.personalaccountant.logic;

import com.cs125.personalaccountant.models.ExpectationRequestModel;
import com.cs125.personalaccountant.resources.API;
import com.cs125.personalaccountant.sql.ExpectationTable;
import com.cs125.personalaccountant.utils.APIUtils;

import java.sql.Timestamp;
import java.util.Calendar;

public class Expectation {

    ExpectationRequestModel requestModel;

    public Expectation(ExpectationRequestModel requestModel) {
        this.requestModel = requestModel;
    }

    public Expectation() {
    }

    public int changeLogic(String email){
        // set other active expectation to expired
        ExpectationTable.updateExpectationStatus(email);

        // add the new one to the expectation table
        Timestamp start = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.DAY_OF_WEEK, 30);
        Timestamp end = new Timestamp(cal.getTime().getTime());

        requestModel.setStartTime(start);
        requestModel.setEndTime(end);
        ExpectationTable.insertExpectation(requestModel, email);
        return APIUtils.expectatoinChangeSuccess;
    }

    public int addLogic(String email, int add){
        // get the active expectation
        ExpectationRequestModel expectation = ExpectationTable.viewExpectation(email);
        expectation.setExpected(expectation.getExpected()+add);
        ExpectationTable.updateExpectation(expectation);
        return APIUtils.expectationGetSuccess;
    }

    public ExpectationRequestModel getLogic(String email){
        // get the active expectation
        return ExpectationTable.viewExpectation(email);
    }
}
