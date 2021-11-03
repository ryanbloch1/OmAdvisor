package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.model.OMIssueLog;
import com.cobi.puresurveyandroid.model.PostStaffRequest;
import com.cobi.puresurveyandroid.model.StaffIdResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ImedAuthenticationInterface {

    @GET("api/Authorize/GetToken")
    Call<ImedTokenResponse> getImedToken(@Header("Content-Type") String contentType, @Header("AppId") String appId, @Header("AppSecret") String appSecret, @Header("ApiKey") String apiKey);

    @POST("Central/Api/Staff/PostStaff")
    Call<StaffIdResponse> PostStaff(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer, @Body PostStaffRequest request);
}
