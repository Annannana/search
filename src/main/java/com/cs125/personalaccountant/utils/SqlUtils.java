package com.cs125.personalaccountant.utils;

import com.cs125.personalaccountant.BasicService;
import com.cs125.personalaccountant.logger.ServiceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUtils {
    public static void update(String query){
        try {
            PreparedStatement ps = BasicService.getConnection().prepareStatement(query);
            ServiceLogger.LOGGER.info("Trying query: " + ps.toString());
            ps.executeUpdate();
            ServiceLogger.LOGGER.info("Query succeeded.");
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Query failed: Unable to update records.");
            e.printStackTrace();
        }
    }
    public static ResultSet view(String query){
        ResultSet resultSet = null;
        try {
            PreparedStatement ps = BasicService.getConnection().prepareStatement(query);
            ServiceLogger.LOGGER.info("Trying query: " + ps.toString());
            resultSet = ps.executeQuery();
            ServiceLogger.LOGGER.info("Query succeeded.");
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Query failed: Unable to retrieve records.");
            e.printStackTrace();
            return resultSet;
        }
        return resultSet;
    }

    public static boolean insert(String query) {
        try {
            PreparedStatement ps = BasicService.getConnection().prepareStatement(query);
            ServiceLogger.LOGGER.info("Trying query: " + ps.toString());
            ps.execute();
            ServiceLogger.LOGGER.info("Query succeeded.");
            return true;
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Query failed: Unable to insert.");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isExist(String query) {
        try {
            PreparedStatement ps = BasicService.getConnection().prepareStatement(query);
            ServiceLogger.LOGGER.info("Trying query: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            ServiceLogger.LOGGER.info("Query succeeded.");
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Query failed: Unable to retrieve records.");
            e.printStackTrace();
            return false;
        }
    }

}
