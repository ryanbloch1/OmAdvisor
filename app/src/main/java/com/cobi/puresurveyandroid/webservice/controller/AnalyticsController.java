package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.DeviceRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PostStaffDeviceRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.RatingRequestAnalytics;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.StaffRequest;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.RatingRequest;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.AnalyticsInterface;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;
import static com.cobi.puresurveyandroid.PureSurveyApplication.MANUFACTURER;
import static com.cobi.puresurveyandroid.PureSurveyApplication.MODEL;

public class AnalyticsController extends BaseApiController {

    public static final String POST_RATING = "post_rating";


    public static void PostRating(Context context, RatingRequestAnalytics ratingRequest) {

        AnalyticsInterface service = createApiServiceImedSystemCentral(context, AnalyticsInterface.class, false);

        Call<ResponseBody> call = service.PostRating(ratingRequest, "application/json", IMED_API_KEY, IMED_APP_ID, "Bearer "+PreferencesHelper.getImedToken(context));

        call.enqueue(new BaseCallback<ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody response) {
               String s;
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(POST_RATING));

            }
        });

    }

    public static void PostUser(Context context, StaffRequest staffRequest) {

        AnalyticsInterface service = createApiServiceImedSystemCentral(context, AnalyticsInterface.class, false);

        Call<ResponseBody> call = service.PostUser(staffRequest, "application/json", IMED_API_KEY, IMED_APP_ID, "Bearer "+PreferencesHelper.getImedToken(context));

        call.enqueue(new BaseCallback<ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody response) {
                String s;
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                String s;
            }
        });


    }

    public static void PostDevice(final Context context, DeviceRequest deviceRequest, String imedToken) {

        AnalyticsInterface service = createApiServiceImedSystemCentral(context, AnalyticsInterface.class, false);

        Call<String> call = service.PostDevice(deviceRequest, "application/json", IMED_API_KEY, IMED_APP_ID, "Bearer "+imedToken);

        call.enqueue(new BaseCallback<String>(context) {
            @Override
            protected void onSuccess(String response) {

                PreferencesHelper.setDeviceId(context, response);

                Log.e("POSTDEVICE", "post_device_success");

            }

            @Override
            protected void onError(ResponseBody errorBody) {
                Log.e("POSTDEVICE", errorBody.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public static void PostAction(Context context, ActionRequest actionRequest) {

        AnalyticsInterface service = createApiServiceImedSystemCentral(context, AnalyticsInterface.class, false);

        Call<ResponseBody> call = service.PostAction(actionRequest, "application/json", IMED_API_KEY, IMED_APP_ID, "Bearer "+PreferencesHelper.getImedToken(context));

        call.enqueue(new BaseCallback<ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody response) {

            }

            @Override
            protected void onError(ResponseBody errorBody) {

            }
        });
    }



}
