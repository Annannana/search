package com.cs125.personalaccountant.logic;

import com.cs125.personalaccountant.models.ExpectationRequestModel;
import com.cs125.personalaccountant.models.TransactionRequestModel;
import com.cs125.personalaccountant.sql.ExpectationTable;
import com.cs125.personalaccountant.sql.TransactionTable;
import com.cs125.personalaccountant.utils.APIUtils;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Transaction {
    private TransactionRequestModel requestModel;

    public Transaction(TransactionRequestModel requestModel){
        this.requestModel = requestModel;
    }

    public Transaction() {
    }

    public int addLogic() {
        // get current time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // get current location
        Point currentLocation = new Point();
        requestModel.setCreateTime(currentTime);

        // get current expectationId and update expectation table
        int expectationId = updateExpectation();
        requestModel.setExpectationId(expectationId);

        // insert to transaction table
        return insertTransaction();
    }

    public int viewLogic(String email, int page, ArrayList<TransactionRequestModel> transactions){
        TransactionTable.viewTransactions(email, page, transactions);
        return APIUtils.transactionViewSuccess;
    }

    private int updateExpectation(){
        ExpectationRequestModel expectationRequestModel = ExpectationTable.viewExpectation(requestModel.getEmail());
        expectationRequestModel.setSpent(expectationRequestModel.getSpent()+requestModel.getAmount());
        ExpectationTable.updateExpectation(expectationRequestModel);
        return expectationRequestModel.getExpectationId();
    }

    private int insertTransaction(){
        TransactionTable.insertTransaction(requestModel);
        return APIUtils.transactionAddSuccess;
    }
}
