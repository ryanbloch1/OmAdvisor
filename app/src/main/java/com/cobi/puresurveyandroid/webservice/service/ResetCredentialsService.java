package com.cobi.puresurveyandroid.webservice.service;

import com.cobi.puresurveyandroid.model.ChangePasswordRequest;
import com.cobi.puresurveyandroid.model.ChangePasswordResponse;
import com.cobi.puresurveyandroid.model.CommissionResponse;
import com.cobi.puresurveyandroid.model.CountryCode;
import com.cobi.puresurveyandroid.model.ResetPasswordRequest;
import com.cobi.puresurveyandroid.model.ResetPasswordResponse;
import com.cobi.puresurveyandroid.model.SendUsernameRequest;
import com.cobi.puresurveyandroid.model.SendUsernameResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ResetCredentialsService {

    @POST("IAMApplicationAPI/v2/IAccessRegistrationManager/sendUserName")
    Call<SendUsernameResponse> sendUsername(@Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Body SendUsernameRequest request);

    @POST("IAMApplicationAPI/v2/IAccessRegistrationManager/resetPassword")
    Call<ResetPasswordResponse> resetPassword(@Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Body ResetPasswordRequest request);

    @GET("insurance/referencedata/v1/models/SOA/domains/CountryCode/relateddomains/CountryPhoneCode/values")
    Call<List<CountryCode>> getPhoneCodes(@Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret);

    @POST("user/omuser/changePassword")
    Call<ChangePasswordResponse> updatePassword(@Header("Authorization") String accessToken, @Header("Content-Type") String content_type, @Header("x-ibm-client-id") String client_id , @Header("x-ibm-client-secret") String client_secret, @Body ChangePasswordRequest request);
}
