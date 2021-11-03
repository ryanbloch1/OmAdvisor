package com.cobi.puresurveyandroid.webservice.ApiResponse;

import com.cobi.puresurveyandroid.model.BaseResponse;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DigitalIdResponse extends BaseResponse
{

    @SerializedName("commonname")
    @Expose
    private String commonName;

    public String getCommonName() {
        return commonName;
    }

    @SerializedName("obpasswordchangeflag")
    @Expose
    private String obpasswordchangeflag;

    public String getObpasswordchangeflag() {
        return obpasswordchangeflag;
    }
}
