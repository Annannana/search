package com.cs125.personalaccountant.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL) // Tells Jackson to ignore all fields with value of NULL
public class ExpectationRequestModel implements RequestModel{
    int expectationId;
    @JsonProperty(value = "expected", required = true)
    int expected;
    int spent;
    String email;
    Timestamp startTime;
    Timestamp endTime;
    int expectationStatusId;

    public ExpectationRequestModel(@JsonProperty(value = "expected", required = true)int expected) {
        this.expected = expected;
    }



    public ExpectationRequestModel(int expectationId, int expected, int spent, String email, Timestamp startTime, Timestamp endTime, int expectationStatusId) {
        this.expectationId = expectationId;
        this.expected = expected;
        this.spent = spent;
        this.email = email;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expectationStatusId = expectationStatusId;
    }

    public int getExpectationId() {
        return expectationId;
    }

    @JsonProperty(value = "expected", required = true)
    public int getExpected() {
        return expected;
    }

    public int getSpent() {
        return spent;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getExpectationStatusId() {
        return expectationStatusId;
    }

    public void setExpectationId(int expectationId) {
        this.expectationId = expectationId;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public void setSpent(int spent) {
        this.spent = spent;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setExpectationStatusId(int expectationStatusId) {
        this.expectationStatusId = expectationStatusId;
    }
}
