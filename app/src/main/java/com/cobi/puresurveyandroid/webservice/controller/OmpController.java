package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.OmpClientRequest;
import com.cobi.puresurveyandroid.model.OmpORRequest;
import com.cobi.puresurveyandroid.model.OmpOutstandingRequirements;
import com.cobi.puresurveyandroid.model.OmpClientsResponse;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.OmpInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class OmpController  extends BaseApiController {

    public static final String CUSTOMER_GET_ClIENTS = "customer_client_id_ompts";
    public static final String CUSTOMER_GET_OR = "customer_or";


    public static void getClients(final Context context, OmpClientRequest request) {
        OmpInterface service = createApiService(context, OmpInterface.class, false);
        Call<List<OmpClientsResponse>> call = service.getClients(context.getString(R.string.client_id_omp), context.getString(R.string.client_secret_omp), "application/json", request);
        call.enqueue(new BaseCallback<List<OmpClientsResponse>>(context) {

            @Override
            public void onFailure(Call<List<OmpClientsResponse>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_ClIENTS));
            }

            @Override
            protected void onSuccess(List<OmpClientsResponse> response) {
//
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
//                PreferencesHelper.setAccessToken(context, null);
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_ClIENTS));
            }
        });
    }


    public static void getOutstandingRequirements(final Context context, OmpORRequest request) {
        OmpInterface service = createApiService(context, OmpInterface.class, false);
        Call<OmpOutstandingRequirements> call = service.getOutstandingRequirements(context.getString(R.string.client_id_omp), context.getString(R.string.client_secret_omp), "application/json", request);
        call.enqueue(new BaseCallback<OmpOutstandingRequirements>(context) {

            @Override
            public void onFailure(Call<OmpOutstandingRequirements> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_OR));
            }

            @Override
            protected void onSuccess(OmpOutstandingRequirements response) {
//
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
//                PreferencesHelper.setAccessToken(context, null);
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_OR));
            }
        });


    }
}
