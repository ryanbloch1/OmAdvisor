package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.Commission;
import com.cobi.puresurveyandroid.model.CommissionResponseAPI;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SalesEarningsInterface {


    @GET("AdvisorCommission/v1/allCommissions")
    Call<CommissionResponseAPI> getSalesEarnings(@Header("Authorization") String access_token, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Query("IntermediaryCode") String salesCode);

}
