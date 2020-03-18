package com.cs125.personalaccountant.sql;

import com.cs125.personalaccountant.models.Store;
import com.cs125.personalaccountant.models.TransactionRequestModel;
import com.cs125.personalaccountant.utils.APIUtils;
import com.cs125.personalaccountant.utils.SqlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StoreTable {
    public static List<Store> recommendStores(int budget, Timestamp time){
        String query = "select * from stores where \"" + time.toString() + "\">openTime and \"" + time.toString() + "\"<closeTime and averageSpend<" + budget + " ORDER BY distance asc, rating DESC, averageSpend asc; ";
        List<Store> res = new ArrayList<>();
        ResultSet resultSet = SqlUtils.view(query);
        try {
            while (resultSet.next()) {
                Store s = new Store(resultSet.getInt("storeId"),
                        resultSet.getString("address"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getTime("openTime"),
                        resultSet.getTime("closeTime"),
                        resultSet.getDouble("longitude"),
                        resultSet.getDouble("latitude"),
                        resultSet.getFloat("rating"),
                        resultSet.getString("description"),
                        resultSet.getInt("averageSpending"),
                        resultSet.getFloat("distance"));
                res.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
