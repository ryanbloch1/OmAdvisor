package com.cobi.puresurveyandroid.webservice.service;

import com.cobi.puresurveyandroid.model.Advisor;
import com.cobi.puresurveyandroid.model.CountryCode;
import com.cobi.puresurveyandroid.model.DeclineReason;
import com.cobi.puresurveyandroid.model.Lead;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotLeadsApiService {

    @GET("CsiService/api/leads/bypriority/{salesCode}/{campaignId}/{priorityId}")
    Call<List<Lead>> getLeadsByPriority(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode, @Path(value = "campaignId", encoded = true) String campaignId, @Path(value = "priorityId", encoded = true) String priorityId);

    @GET("CsiService/api/advisers/search")
    Call<List<Advisor>> searchIntermediary(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Query("name") String name, @Query("surname") String surname, @Query("salesCode") String salescode );

    @POST("CsiService/api/leads/accept/{salesCode}/{leadId}")
    Call<String> acceptLead(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode, @Path(value = "leadId", encoded = true) long leadId);

    @POST("CsiService/api/leads/decline/{salesCode}/{leadId}/{reasonId}")
    Call<String> declineLead(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode, @Path(value = "leadId", encoded = true) long leadId, @Path(value = "reasonId", encoded = true) int reasonId);

    @POST("CsiService/api/leads/refer/{salesCode}/{targetSalesCode}/{leadId}")
    Call<String> referLead(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Path(value = "salesCode", encoded = true) String salesCode, @Path(value = "targetSalesCode", encoded = true) String targetSalesCode,@Path(value = "leadId", encoded = true) long leadId);

    @GET("CsiService/api/leads/declinereasons")
    Call<List<DeclineReason>> getDeclineReasons(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret);

}
