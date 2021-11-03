package com.cobi.puresurveyandroid.webservice.service;

import com.cobi.puresurveyandroid.model.RegisterRequest;
import com.cobi.puresurveyandroid.model.RegisterResponse;
import com.cobi.puresurveyandroid.model.UpdateDeviceRequest;
import com.cobi.puresurveyandroid.model.UpdateDeviceResponse;
import com.cobi.puresurveyandroid.model.ValidateRequest;
import com.cobi.puresurveyandroid.model.ValidateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 2017/10/04.
 */

public interface AuthenticationApiService {

    @POST("CentralApi/Android/register")
    Call<RegisterResponse> register(@Body RegisterRequest requestBody);

    @POST("CentralApi/Android/update_device")
    Call<UpdateDeviceResponse> updateDevice(@Body UpdateDeviceRequest requestBody);

    @POST("CentralApi/Android/validate")
    Call<ValidateResponse> validate(@Body ValidateRequest requestBody);
}
