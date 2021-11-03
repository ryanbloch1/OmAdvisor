package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.webservice.ApiResponse.DigitalIdResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DigitalIdInterface
{
    @GET("ms_oauth/resources/imeduserprofile/me")
    Call<DigitalIdResponse> getDigitalID(@Header("Authorization") String access_token, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id, @Header("x-ibm-client-secret") String client_secret);
}
