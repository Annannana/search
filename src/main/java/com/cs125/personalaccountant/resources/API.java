package com.cs125.personalaccountant.resources;

import com.cs125.personalaccountant.logger.ServiceLogger;
import com.cs125.personalaccountant.logic.Expectation;
import com.cs125.personalaccountant.logic.Recommendation;
import com.cs125.personalaccountant.logic.Transaction;
import com.cs125.personalaccountant.models.*;
import com.cs125.personalaccountant.sql.ExpectationTable;
import com.cs125.personalaccountant.sql.RecommendationTable;
import com.cs125.personalaccountant.sql.StoreTable;
import com.cs125.personalaccountant.sql.TransactionTable;
import com.cs125.personalaccountant.utils.APIUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Path("search")
public class API {
    @Path("transaction/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transactionAdd(String jsonText, @Context HttpHeaders headers) {
        String email = headers.getHeaderString("email");
        ServiceLogger.LOGGER.config("Connected to transaction add.");
        // mapping Jsontext to POJO
        PojoHolder pojoHolder = APIUtils.convertToPOJO(jsonText, TransactionRequestModel.class);
        int resultCode = pojoHolder.getResultCode();
        TransactionRequestModel requestModel = (TransactionRequestModel) pojoHolder.getRequestModel();
        // logic check if there is no mapping error
        if (resultCode == APIUtils.sucess) {
            requestModel.setEmail(email);
            Transaction insert = new Transaction(requestModel);
            resultCode = insert.addLogic();
        }
        // response based on resultCode
        String message = APIUtils.convertToMessage(resultCode);
        GeneralResponseModel generalResponseModel = new GeneralResponseModel(resultCode, message, null);
        ServiceLogger.LOGGER.config("Finished add.");
        return APIUtils.response(generalResponseModel);
    }

    @Path("transaction/view/{page}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transactionView(@Context HttpHeaders headers, @PathParam("page") int page) {
        String email = headers.getHeaderString("email");
        ServiceLogger.LOGGER.config("Connected to transaction view.");

        Transaction view = new Transaction();
        ArrayList<TransactionRequestModel> transactions = new ArrayList<>();
        int resultCode = view.viewLogic(email, page, transactions);

        // response based on resultCode
        String message = APIUtils.convertToMessage(resultCode);
        GeneralResponseModel generalResponseModel = new GeneralResponseModel(resultCode, message, null, transactions);
        ServiceLogger.LOGGER.config("Finished view.");
        return APIUtils.response(generalResponseModel);
    }

    @Path("expectation/change")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response expectationChange(String jsonText, @Context HttpHeaders headers) {
        String email = headers.getHeaderString("email");
        ServiceLogger.LOGGER.config("Connected to expectation change.");
        // mapping Jsontext to POJO
        PojoHolder pojoHolder = APIUtils.convertToPOJO(jsonText, ExpectationRequestModel.class);
        int resultCode = pojoHolder.getResultCode();
        ExpectationRequestModel requestModel = (ExpectationRequestModel) pojoHolder.getRequestModel();
        // logic check if there is no mapping error
        if (resultCode == APIUtils.sucess) {
            Expectation expectation = new Expectation(requestModel);
            resultCode = expectation.changeLogic(email);
        }
        // response based on resultCode
        String message = APIUtils.convertToMessage(resultCode);
        GeneralResponseModel generalResponseModel = new GeneralResponseModel(resultCode, message, null);
        ServiceLogger.LOGGER.config("Finished change.");
        return APIUtils.response(generalResponseModel);
    }

    @Path("expectation/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response expectationAdd(@Context HttpHeaders headers) {
        int add = Integer.parseInt(headers.getHeaderString("add"));
        String email = headers.getHeaderString("email");
        ServiceLogger.LOGGER.config("Connected to expectation add.");

        Expectation expectation= new Expectation();
        int resultCode = expectation.addLogic(email, add);

        // response based on resultCode
        String message = APIUtils.convertToMessage(resultCode);
        GeneralResponseModel generalResponseModel = new GeneralResponseModel(resultCode, message, null);
        ServiceLogger.LOGGER.config("Finished add.");
        return APIUtils.response(generalResponseModel);
    }

    @Path("expectation/get")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response expectationGet( @Context HttpHeaders headers) {
        String email = headers.getHeaderString("email");
        ServiceLogger.LOGGER.config("Connected to expectation get.");

        Expectation expectation= new Expectation();
        ExpectationRequestModel activeExpectation = expectation.getLogic(email);

        // response based on resultCode
        String message = APIUtils.convertToMessage(APIUtils.expectationGetSuccess);
        GeneralResponseModel generalResponseModel = new GeneralResponseModel(APIUtils.expectationGetSuccess, message, null, activeExpectation);
        ServiceLogger.LOGGER.config("Finished get.");
        return APIUtils.response(generalResponseModel);
    }

    @Path("init")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response recommendation(@Context HttpHeaders headers) {
        String email = headers.getHeaderString("email");
        ServiceLogger.LOGGER.config("Connected to recommendation.");

        // get the average time for recommendation, breakfast, lunch, dinner-> transactions
        Recommendation breakfast = new Recommendation(email, APIUtils.CATEGORY_BREAKFAST);
        Recommendation lunch = new Recommendation(email, APIUtils.CATEGORY_LUNCH);
        Recommendation dinner = new Recommendation(email, APIUtils.CATEGORY_DINNER);
        int breakfastBudget = TransactionTable.viewCategory(breakfast);
        int lunchBudget = TransactionTable.viewCategory(lunch);
        int dinnerBudget = TransactionTable.viewCategory(dinner);
        double sum = breakfastBudget+lunchBudget+dinnerBudget;
        double breakfastPerc = breakfastBudget/sum;
        double lunchPerc = lunchBudget/sum;
        double dinnerPerc = dinnerBudget/sum;
        // init for new users
        if(lunchBudget<=0||breakfastBudget<=0||dinnerBudget<=0){
            breakfastPerc = lunchPerc = dinnerPerc = 1/3;
        }

        // expected daily spending -> expectation
        ExpectationRequestModel expectationRequestModel = ExpectationTable.viewExpectation(email);

        // time -> if exceed budget, alert user
        if(expectationRequestModel.getExpected()-expectationRequestModel.getSpent()<=APIUtils.ALERT_EXPECTED){
            breakfast.setBudget(APIUtils.ALERT_SIGN);
            lunch.setBudget(APIUtils.ALERT_SIGN);
            dinner.setBudget(APIUtils.ALERT_SIGN);
        } else{
            long diff = expectationRequestModel.getEndTime().getTime()- currentTimeMillis();
            double dayBudget = (expectationRequestModel.getExpected()-expectationRequestModel.getSpent())/(diff / (86400000 ));
            breakfast.setBudget((int)(dayBudget*breakfastPerc));
            lunch.setBudget((int)(dayBudget*lunchPerc));
            dinner.setBudget((int)(dayBudget*dinnerPerc));
        }
        ServiceLogger.LOGGER.info("Finish init recommendation time. Start insert recommendations to table.");

        // insert to recommendation table
        RecommendationTable.insertRecommendation(breakfast);
        RecommendationTable.insertRecommendation(lunch);
        RecommendationTable.insertRecommendation(dinner);
        ServiceLogger.LOGGER.info("Finish adding to table.");


        RecommendationTimes recommendationTimes =new RecommendationTimes(breakfast.getTime(), lunch.getTime(), dinner.getTime());
        // response based on resultCode
        String message = APIUtils.convertToMessage(APIUtils.recommendationSetSuccess);
        RecommendationResponseModel generalResponseModel = new RecommendationResponseModel(APIUtils.recommendationSetSuccess, message, 1, recommendationTimes);
        ServiceLogger.LOGGER.config("Finished recommendation init.");
        return APIUtils.response(generalResponseModel);
    }

    @Path("recommendation/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response recommendationProcess( @Context HttpHeaders headers) {
        // get time and budget
        int meal = Integer.parseInt(headers.getHeaderString("meal"));
        Recommendation recommendation = new Recommendation(headers.getHeaderString("email"), meal);
        RecommendationTable.retrieveRecommendation(recommendation);

        // user budget to get Stores information
        // rank by similar budget, ranting and distance, filter open hour.
        List<Store> stores = StoreTable.recommendStores(recommendation.getBudget(), new Timestamp(System.currentTimeMillis()));

        // response based on resultCode
        String message = APIUtils.convertToMessage(APIUtils.expectationGetSuccess);
        RecommendationResponseModel generalResponseModel = new RecommendationResponseModel(APIUtils.expectationGetSuccess, message, stores);
        ServiceLogger.LOGGER.config("Finished recommendation stores.");
        return APIUtils.response(generalResponseModel);
    }
}
