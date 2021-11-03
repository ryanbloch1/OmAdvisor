




















































package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.DeviceRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PostStaffDeviceRequest;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.model.PostStaffRequest;
import com.cobi.puresurveyandroid.model.StaffIdResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.AnalyticsInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.ImedAuthenticationInterface;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.greenrobot.eventbus.EventBus;
import org.xms.g.utils.GlobalEnvSetting;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;
import static com.cobi.puresurveyandroid.PureSurveyApplication.MANUFACTURER;

public class ImedAuthenticationController extends BaseApiController {

    public static final String POST_STAFF = "post_staff";




    public static void register(final Context context, final String salesCode, final String digitalId, final String email, final String channel, final String Region, final String team, final String role, final String segment, final String firstName, final String lastName, final String imedToken, final String area) {
        String pushToken = PreferencesHelper.getCloudMessagingRegistrationId(context);

        if (pushToken == null) {

            if (!GlobalEnvSetting.isHms()) {
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                String token = null;
                                if (task.isSuccessful() && task.getResult() != null) {
                                    // Get new Instance ID token


                                    token = task.getResult().getToken();




                                }

                                PostStaff(context, salesCode, token, digitalId, email, channel, Region, team, role, segment, firstName, lastName, imedToken, area);
                            }
                        });

            }
            else{
                PostStaff(context, salesCode, "", digitalId, email, channel, Region, team, role, segment, firstName, lastName, imedToken, area);

            }

        } else {
            PostStaff(context, salesCode, pushToken, digitalId, email, channel, Region, team, role, segment, firstName, lastName, imedToken, area);
        }
    }

    public static void getToken(final Context context, String appId, String apiKey, boolean postDevice) {

        ImedAuthenticationInterface service = createApiServiceImedSystem(context, ImedAuthenticationInterface.class, false);

        Call<ImedTokenResponse> call = service.getImedToken("text/plain", appId, context.getString(R.string.app_secret), apiKey);

        call.enqueue(new BaseCallback<ImedTokenResponse>(context) {
            @Override
            protected void onSuccess(ImedTokenResponse response) {
                PreferencesHelper.setImedToken(context, response.getToken());


                EventBus.getDefault().post(response);

                if(postDevice){
                    String android_id = Settings.Secure.getString(context.getContentResolver(),
                            Settings.Secure.ANDROID_ID);


                    AnalyticsController.PostDevice(context, new DeviceRequest(android_id, MANUFACTURER, PureSurveyApplication.MODEL), response.getToken());
                }



            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse("ff"));
            }

            @Override
            public void onFailure(Call<ImedTokenResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse("df"));

            }
        });
    }

    public static void PostStaff(final Context context, String salesCode, String registrationToken, String digitalId, String email, String channel, final String region, String team, String role, String segment, String firstName, String lastName, String imedToken, String area) {

        ImedAuthenticationInterface service = createApiServiceImedSystem(context, ImedAuthenticationInterface.class, false);

        PostStaffRequest request = new PostStaffRequest(registrationToken, digitalId, salesCode, email, area, team, region, role, channel, segment, firstName, lastName);

        Call<StaffIdResponse> call = service.PostStaff(IMED_API_KEY, IMED_APP_ID, "Bearer " + imedToken, request);

        call.enqueue(new BaseCallback<StaffIdResponse>(context) {
            @Override
            protected void onSuccess(StaffIdResponse response) {
                PreferencesHelper.setStaffId(context, response.getStaffId());

                PostStaffDevice(context);

            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(POST_STAFF));

            }

            @Override
            public void onFailure(Call<StaffIdResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(POST_STAFF));

            }
        });
    }

    public static void PostStaffDevice(Context context) {

        AnalyticsInterface service = createApiServiceImedSystemCentral(context, AnalyticsInterface.class, false);

        Call<ResponseBody> call = service.PostStaffDevice(new PostStaffDeviceRequest(PreferencesHelper.getStaffId(context), PreferencesHelper.getDeviceId(context)), "application/json", IMED_API_KEY, IMED_APP_ID, "Bearer " + PreferencesHelper.getImedToken(context));

        call.enqueue(new BaseCallback<ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody response) {
                Log.d("POST_STAFF_DEVICE", "success");
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                Log.d("POST_STAFF_DEVICE", "error");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
