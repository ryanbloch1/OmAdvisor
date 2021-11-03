package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.Notification;
import com.cobi.puresurveyandroid.model.NotificationStatusRequest;
import com.cobi.puresurveyandroid.model.NotificationsRequest;
import com.cobi.puresurveyandroid.model.NotificationsResponse;
import com.cobi.puresurveyandroid.model.OMIssueLog;
import com.cobi.puresurveyandroid.model.PostStaffRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NotificationsService {


    @POST("Notifications/PostOptIn")
    Call<Boolean> statusChange(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer, @Body NotificationStatusRequest request);



    @POST("Notifications/PostNotifications")
    Call<ResponseBody> postNotifications(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer, @Body NotificationsRequest request);



    @GET("Notifications/GetNotifications")
    Call<NotificationsResponse> getNotifications(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer, @Header("StaffId") String staffId);
}
