package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.DeviceRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PostStaffDeviceRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.RatingRequestAnalytics;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.StaffRequest;
import com.cobi.puresurveyandroid.model.RatingRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AnalyticsInterface {

    @POST("Analytics/PostRating")
    Call<ResponseBody> PostRating(@Body RatingRequestAnalytics ratingRequest, @Header("Content-Type") String content_type, @Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer);
    @POST("Analytics/PostUser")
    Call<ResponseBody> PostUser(@Body StaffRequest staffRequest, @Header("Content-Type") String content_type, @Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer);
    @POST("Analytics/PostDevice")
    Call<String> PostDevice(@Body DeviceRequest deviceRequest, @Header("Content-Type") String content_type, @Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer);
    @POST("Analytics/PostAction")
    Call<ResponseBody> PostAction(@Body ActionRequest actionRequest, @Header("Content-Type") String content_type, @Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer);
    @POST("Analytics/PostStaffDevice")
    Call<ResponseBody> PostStaffDevice(@Body PostStaffDeviceRequest deviceRequest, @Header("Content-Type") String content_type, @Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer);
}
