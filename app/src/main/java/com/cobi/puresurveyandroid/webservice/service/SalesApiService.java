package com.cobi.puresurveyandroid.webservice.service;

import com.cobi.puresurveyandroid.model.CommissionResponse;
import com.cobi.puresurveyandroid.model.SaveTargetRequest;
import com.cobi.puresurveyandroid.model.sales.CustomerEventsResponse;
import com.cobi.puresurveyandroid.model.sales.HelpResponse;
import com.cobi.puresurveyandroid.model.sales.LeadsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface SalesApiService {

    @GET("/za/ps/android/sales/commission")
    Call<CommissionResponse> getCommissionData();

    @PUT("/za/ps/android/sales/commission")
    Call<CommissionResponse> saveCommissionTarget(@Body SaveTargetRequest request);

    @GET("/za/ps/android/sales/events")
    Call<CustomerEventsResponse> getCustomerEvents();

    @GET("/za/ps/android/sales/leads")
    Call<LeadsResponse> getLeads();

    @GET("/za/ps/android/sales/help")
    Call<HelpResponse> getHelpInfo();
}