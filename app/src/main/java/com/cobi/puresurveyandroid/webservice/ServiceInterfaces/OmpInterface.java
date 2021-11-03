package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.OmpClientRequest;
import com.cobi.puresurveyandroid.model.OmpORRequest;
import com.cobi.puresurveyandroid.model.OmpOutstandingRequirements;
import com.cobi.puresurveyandroid.model.OmpClientsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OmpInterface {

    @POST("api/OutstandingRequirement/GetClients")
    Call<List<OmpClientsResponse>> getClients(@Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("Content-Type") String content_type, @Body OmpClientRequest requestBody);

    @POST("api/OutstandingRequirement/GetOutstandingRequirements")
    Call<OmpOutstandingRequirements> getOutstandingRequirements(@Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("Content-Type") String content_type, @Body OmpORRequest requestBody);



}
