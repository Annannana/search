package com.cs125.personalaccountant.utils;

import com.cs125.personalaccountant.logger.ServiceLogger;
import com.cs125.personalaccountant.models.PojoHolder;
import com.cs125.personalaccountant.models.RequestModel;
import com.cs125.personalaccountant.models.ResponseModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.ws.rs.core.Response;
import java.io.IOException;

public class APIUtils {

    public static final int PAGE_SIZE = 3;
    public static final int ALERT_SIGN = -1;
    public static final int ALERT_EXPECTED = 20;

    public static final int RECOMMENDATION_WAITING = 1;
    public static final int RECOMMENDATION_CLOSED = 2;

    public static final int CATEGORY_BREAKFAST = 1;
    public static final int CATEGORY_LUNCH = 2;
    public static final int CATEGORY_DINNER = 3;

    public static final int EXPECTATION_ACTIVE = 1;
    public static final int EXPECTATION_EXPIRED = 2;

    public static final int transactionAddSuccess = 14;
    public static final int transactionViewSuccess = 15;
    public static final int expectatoinChangeSuccess = 16;
    public static final int expectationAddSuccess = 17;
    public static final int expectationGetSuccess = 18;
    public static final int recommendationSetSuccess = 19;

    public static final int jsonParseException = -3;
    public static final int jsonMappingException = -2;
    public static final int internalServerError = -1;
    public static final int sucess = 0;

    public static PojoHolder convertToPOJO(String jsonText, Class<?> cls) {
        ObjectMapper mapper = new ObjectMapper();
        RequestModel requestModel;
        PojoHolder pojoHolder = new PojoHolder();
        try {
            requestModel = (RequestModel) mapper.readValue(jsonText, cls);
            pojoHolder.setRequestModel(requestModel);
            pojoHolder.setResultCode(sucess);
            ServiceLogger.LOGGER.config("Finished converting to POJO.");
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof JsonParseException) {
                ServiceLogger.LOGGER.warning("JsonParseException");
                pojoHolder.setResultCode(jsonParseException);
            }
            if (e instanceof JsonMappingException) {
                ServiceLogger.LOGGER.warning("JsonMappingException");
                pojoHolder.setResultCode(jsonMappingException);
            }
            if (e instanceof IOException) {
                ServiceLogger.LOGGER.warning("IOException");
                pojoHolder.setResultCode(internalServerError);
            }
        }
        return pojoHolder;
    }
    public static Response response(ResponseModel responseModel) {
        switch (responseModel.getResultCode()) {
            case jsonParseException:
            case jsonMappingException:
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();
            case internalServerError:
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            case transactionAddSuccess:
            case transactionViewSuccess:
            case expectatoinChangeSuccess:
            case expectationAddSuccess:
            case expectationGetSuccess:
            case recommendationSetSuccess:
                return Response.status(Response.Status.OK).entity(responseModel).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    public static String convertToMessage(int num) {
        String message = "";
        switch (num) {
            case recommendationSetSuccess:
                message = "Recommondation set successfully.";
                break;
            case expectationGetSuccess:
                message = "Expectation got successfully.";
                break;
            case expectatoinChangeSuccess:
                message = "Expectation changed successfully";
                break;
            case expectationAddSuccess:
                message = "Expectation added successfully.";
                break;
            case transactionViewSuccess:
                message = "Transaction retrieved successfully.";
                break;
            case transactionAddSuccess:
                message = " Transaction added successfully.";
                break;
            case jsonParseException:
                message = "JSON Parse Exception.";
                break;
            case jsonMappingException:
                message = "JSON Mapping Exception.";
                break;
            case internalServerError:
                message = "Internal Server Error.";
                break;
        }
        return message;
    }
}
