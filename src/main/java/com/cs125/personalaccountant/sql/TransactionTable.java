package com.cs125.personalaccountant.sql;

import com.cs125.personalaccountant.logic.Recommendation;
import com.cs125.personalaccountant.models.TransactionRequestModel;
import com.cs125.personalaccountant.utils.APIUtils;
import com.cs125.personalaccountant.utils.SqlUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionTable {
    public static void insertTransaction(TransactionRequestModel transaction) {
        String query = "insert into transactions(email, createTime, amount, details, categoryId, longitude, latitude, expectationId) value (\""
                + transaction.getEmail() + "\", \"" + transaction.getCreateTime().toString() + "\", " + transaction.getAmount()
                +  ", \" " + transaction.getDetails() + "\", " + transaction.getCategoryId() + ", " + transaction.getLongitude()
                + "," + transaction.getLatitude() + ", " + transaction.getExpectationId() + ")";
        SqlUtils.insert(query);
    }

    public static void viewTransactions(String email, int page, ArrayList<TransactionRequestModel> transactions){
        String query = "select * from transactions where email=\"" + email + "\" group by createTime desc limit " + APIUtils.PAGE_SIZE*page + " , " + APIUtils.PAGE_SIZE;
        ResultSet resultSet = SqlUtils.view(query);
        try {
            while (resultSet.next()) {
                TransactionRequestModel transaction = new TransactionRequestModel(
                        resultSet.getInt("transactionId"),
                        resultSet.getString("email"),
                        resultSet.getTimestamp("createTime"),
                        resultSet.getInt("amount"),
                        resultSet.getString("details"),
                        resultSet.getInt("categoryId"),
                        resultSet.getDouble("longitude"),
                        resultSet.getDouble("latitude"),
                        resultSet.getInt("expectationId")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int viewCategory(Recommendation recommendation){
        String query = "select amount,createTime from transactions where email=\"" + recommendation.getEmail() + "\" and categoryId=" + recommendation.getCategoryId();
        ResultSet resultSet = SqlUtils.view(query);
        try {
            long totalMoney = 0;
            long totalTime = 0;
            int counter = 0;
            while (resultSet.next()) {
                totalMoney += resultSet.getInt("amount");
                Timestamp timestamp = resultSet.getTimestamp("createTime");
                String S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                String[] parts = S.split(" ");
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                Date d = dateFormat.parse(parts[1]);
                if(counter==0){
                    totalTime = d.getTime();
                }else{
                    totalTime = (totalTime+d.getTime())/2;
                }
                counter++;
            }
            Time t = new Time(totalTime-20*60000);
            recommendation.setTime(t);
            int budget = (int)(totalMoney/counter);
            return budget;
        } catch (SQLException| ParseException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
