package com.cobi.puresurveyandroid.webservice.service;

import com.cobi.puresurveyandroid.model.UserCustomerEventsRequest;
import com.cobi.puresurveyandroid.model.UserCustomerEventsResponse;
import com.cobi.puresurveyandroid.model.MatrixRequest;
import com.cobi.puresurveyandroid.model.MatrixResponse;
import com.cobi.puresurveyandroid.model.ReadReceiptRequest;
import com.cobi.puresurveyandroid.model.ReadReceiptResponse;
import com.cobi.puresurveyandroid.model.ReceiveReceiptRequest;
import com.cobi.puresurveyandroid.model.ReceiveReceiptResponse;
import com.cobi.puresurveyandroid.model.ReplyRequest;
import com.cobi.puresurveyandroid.model.ReplyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 2017/10/04.
 */

public interface EventsApiService {

    @POST("CentralApi/Android/events")
    Call<UserCustomerEventsResponse> events(@Body UserCustomerEventsRequest requestBody);

    @POST("CentralApi/Android/reply")
    Call<ReplyResponse> reply(@Body ReplyRequest requestBody);

    @POST("CentralApi/Android/receivereceipt")
    Call<ReceiveReceiptResponse> receiveReceipt(@Body ReceiveReceiptRequest requestBody);

    @POST("CentralApi/Android/readreceipt")
    Call<ReadReceiptResponse> readReceipt(@Body ReadReceiptRequest requestBody);

    @POST("CentralApi/Android/matrix_web")
    Call<MatrixResponse> retrieveMatrix(@Body MatrixRequest requestBody);
}
