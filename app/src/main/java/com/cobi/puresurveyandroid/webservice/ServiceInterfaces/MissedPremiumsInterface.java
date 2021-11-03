package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.model.MissedPremiumsRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MissedPremiumsInterface {

    @POST("api/MissedPremiums")
    @Headers("Content-Type: application/json")
    Call<List<MissedPremium>> getMissedPremiumsDetails(@Header("Authorization") String access_token, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret, @Body MissedPremiumsRequest body);

}
