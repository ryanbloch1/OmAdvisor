package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.Birthday;
import com.cobi.puresurveyandroid.model.BirthdayResponse;
import com.cobi.puresurveyandroid.model.CSIContactDetails;
import com.cobi.puresurveyandroid.model.Campaign;
import com.cobi.puresurveyandroid.model.MyCustomerCounts;
import com.cobi.puresurveyandroid.model.Lead;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CSIInterface {

    @GET("CsiService/api/clients/birthdaysThisMonth/{salesCode}")
    Call<List<Birthday>> getClientBirthdays(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode);

    @GET("CsiService/api/campaigns/{salesCode}")
    Call<List<Campaign>> getCampaigns(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id ,@Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode);

    @GET("CsiService/api/leads/{salesCode}/{campaignId}")
    Call<List<Lead>> getLeads(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id ,@Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode, @Path(value = "campaignId", encoded = true) String campaignId);

    @GET("CsiService/api/clients/contactDetails2/{salesCode}/{gcsId}")
    Call<CSIContactDetails> getContactDetails(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id ,@Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode, @Path(value = "gcsId", encoded = true) String gcsId);

    @GET("CsiService/api/clients/contactDetails/{leadId}")
    Call<CSIContactDetails> getContactDetailsLeads(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id ,@Header("x-ibm-client-secret") String client_secret,  @Path(value = "leadId", encoded = true) long leadId);

    @GET("AdviserMobileLandingAPI/v1/Count")
    Call<MyCustomerCounts> getCountsForLandingPage(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Query("IntermediaryCode") String imedCode);
}
