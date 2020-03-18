package com.cs125.personalaccountant.models;

public class PojoHolder {

    private RequestModel requestModel;
    private Integer resultCode;

    public PojoHolder(){
        requestModel = null;
        resultCode = null;
    }

    public RequestModel getRequestModel() {
        return requestModel;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setRequestModel(RequestModel requestModel) {
        this.requestModel = requestModel;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }
}
