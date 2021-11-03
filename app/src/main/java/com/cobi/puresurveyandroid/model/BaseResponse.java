package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/10/04.
 */

public class BaseResponse {
    private String httpCode;
    private long status;
    private String message;
    @SerializedName("httpMessage")
    private String httpMessage;
    @SerializedName("moreInformation")
    private String moreInformation;

    @SerializedName("error_description")
    private String errorDescription;

    @SerializedName("Message")
    private String capitalizedMessage;

    public long getStatus() {
        if(httpCode!=null && !httpCode.isEmpty()) {
            return Long.parseLong(httpCode);
        }
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getMessage() {

        if (capitalizedMessage != null && !capitalizedMessage.isEmpty()) {
            return capitalizedMessage;
        }

        if(moreInformation != null && !moreInformation.isEmpty()){
            return moreInformation;
        }

        if(httpMessage!=null && !httpMessage.isEmpty() ){
            return httpMessage;
        }

        if(errorDescription!=null && !errorDescription.isEmpty()){
            return errorDescription;
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
