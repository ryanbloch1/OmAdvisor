package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.Pipeline;
import com.cobi.puresurveyandroid.model.PipelineProduct;
import com.cobi.puresurveyandroid.model.PipelineProductRequest;
import com.cobi.puresurveyandroid.model.PipelineProductResponse;
import com.cobi.puresurveyandroid.model.PipelineResponse;
import com.cobi.puresurveyandroid.model.key;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PipelinesInterface {

    @GET("bpmmis-api/v1/work-item")
    Call<PipelineResponse> getPipelines(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Query("intermediary_code") String imedCode);

    @POST("bpmmis-api/v1/tracking-history")
    Call<PipelineProductResponse> getPipelineOutstandingProducts(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Body PipelineProductRequest requestBody);
}
