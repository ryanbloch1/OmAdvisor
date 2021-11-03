package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.IntermediaryDetail;
import com.cobi.puresurveyandroid.model.IntermediaryDetailsRequest;
import com.cobi.puresurveyandroid.model.IntermediarySearchCriteria;
import com.cobi.puresurveyandroid.model.PartyPerson;
import com.cobi.puresurveyandroid.model.PartySearchRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IntermediaryDetailsInterface {
    @Headers("Content-Type: application/json")
    @POST("insurance/life/intermediary/v3/intermediarydetails")
    Call<IMEDDetails> getImedDetails(@Header("Authorization") String access_token, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("applicationID") String appId, @Header("userID") String userId, @Body IntermediaryDetailsRequest body);

    @POST("insurance/life/party/v10/persons")
    Call<PartyPerson> getPartyDetails(@Header("Authorization") String access_token, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Header("componentID") String componentID,  @Header("systemDate") String systemDate, @Header("userID") String userID, @Header("businessTransactionID") String businessTransactionID,@Header("businessProcessID") String businessProcessID,@Header("applicationID") String applicationID,@Header("applicationVersion") String applicationVersion,@Header("systemCreationDateTime") String systemCreationDateTime,   @Body PartySearchRequest body);

}
