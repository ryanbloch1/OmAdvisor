package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.IssueLog;
import com.cobi.puresurveyandroid.model.OMIssueLog;
import com.cobi.puresurveyandroid.model.PipelineProductRequest;
import com.cobi.puresurveyandroid.model.PipelineProductResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OmIssueInterface {

    @POST("Incident/LogOmpIssue")
    Call<ResponseBody> postIncident( @Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Content-Type") String content_type, @Header("Authorization") String bearer, @Body OMIssueLog requestBody);


    @POST("Incident/LogIssue")
    Call<ResponseBody> postAppIncident( @Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Content-Type") String content_type, @Header("Authorization") String bearer, @Body IssueLog requestBody);
}
