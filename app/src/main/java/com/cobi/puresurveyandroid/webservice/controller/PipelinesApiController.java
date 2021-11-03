package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import android.util.Log;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.PipelineProductRequest;
import com.cobi.puresurveyandroid.model.PipelineProductResponse;
import com.cobi.puresurveyandroid.model.PipelineResponse;
import com.cobi.puresurveyandroid.util.GsonHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.HttpBuilder.SslCertConfiguration;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.PipelinesInterface;
import org.xms.f.remoteconfig.ExtensionRemoteConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cobi.puresurveyandroid.util.FBRemoteConfigHelper.getClientId;
import static com.cobi.puresurveyandroid.util.FBRemoteConfigHelper.getClientSecret;

public class PipelinesApiController extends BaseApiController {

    public static final String CUSTOMER_GET_PIPELINES = "customer_pipelines";
    public static final String CUSTOMER_GET_PIPELINE_PRODUCTS = "customer_pipelines_products";

    public static void getPipelines(Context context) {

        PipelinesInterface service = createApiService(context, PipelinesInterface.class, true);

        Call<PipelineResponse> call = service.getPipelines(PreferencesHelper.getAccessToken(context),"application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret) ,PreferencesHelper.getSalesCode(context));

        Log.e("test", "piplelines request");

        call.enqueue(new BaseCallback<PipelineResponse>(context) {
            @Override
            protected void onSuccess(PipelineResponse response) {
                EventBus.getDefault().post(response);
                Log.e("test", "getpiplelines success");
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_PIPELINES));
                Log.e("test", "getpipelines error");
            }

            @Override
            public void onFailure(Call<PipelineResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_PIPELINES));
                Log.e("test", "getpipelines failure");
            }
        });

    }


    public static void getPipelineProducts(Context context, PipelineProductRequest request) {

        PipelinesInterface service = createApiService(context, PipelinesInterface.class, true);

        Call<PipelineProductResponse> call = service.getPipelineOutstandingProducts(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), request);

        Log.e("test", "piplelines request");

        call.enqueue(new BaseCallback<PipelineProductResponse>(context) {
            @Override
            protected void onSuccess(PipelineProductResponse response) {
                EventBus.getDefault().post(response);
                Log.e("test", "getpiplelines success");
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_PIPELINE_PRODUCTS));
                Log.e("test", "getpipelines error");
            }

            @Override
            public void onFailure(Call<PipelineProductResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_PIPELINE_PRODUCTS));
                Log.e("test", "getpipelines failure");
            }
        });
    }
}
