package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import androidx.annotation.NonNull;

import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.RegisterRequest;
import com.cobi.puresurveyandroid.model.RegisterResponse;
import com.cobi.puresurveyandroid.model.UpdateDeviceRequest;
import com.cobi.puresurveyandroid.model.UpdateDeviceResponse;
import com.cobi.puresurveyandroid.model.ValidateRequest;
import com.cobi.puresurveyandroid.model.ValidateResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.service.AuthenticationApiService;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.greenrobot.eventbus.EventBus;
import org.xms.g.utils.GlobalEnvSetting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.huawei.hms.push.HmsMessaging.DEFAULT_TOKEN_SCOPE;

/**
 * Created by admin on 2017/10/05.
 */

public class SnapappAuthenticationApiController extends BaseApiController {
    public static final String REGISTER = "register";
    public static final String UPDATE_DEVICE = "update_device";
    public static final String VALIDATE = "validate";

    public static void register(final Context context, final String staffNumber) {
        String pushToken = PreferencesHelper.getCloudMessagingRegistrationId(context);
        if (pushToken == null) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task)  {
                            String token = null;
                            if (task.isSuccessful() && task.getResult() != null) {
                                // Get new Instance ID token
                                    token = task.getResult().getToken();

                            }
                            registerWithToken(context, staffNumber, token);
                        }
                    });
        } else {
            registerWithToken(context, staffNumber, pushToken);
        }
    }

    private static void registerWithToken(final Context context, String staffNumber, String token) {
        AuthenticationApiService service = createApiServiceImedSystem(context, AuthenticationApiService.class,false);
        final RegisterRequest request = new RegisterRequest();
        request.setPushToken(token);
        request.setStaffNumber(staffNumber);

        Call<RegisterResponse> call = service.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        PreferencesHelper.setUUID(context, response.body().getData().getGuid());

                        String g = response.body().getData().getGuid();
                        PreferencesHelper.setStaffNumber(context, request.getStaffNumber());
                        EventBus.getDefault().post(response.body());
                    } else {
                        postErrorResponse(response.errorBody(), new ErrorResponse(REGISTER));
                    }
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(REGISTER));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                postErrorResponse(new ErrorResponse(REGISTER));
            }
        });
    }

    public static void updateDevice(final Context context) {
        String pushToken = PreferencesHelper.getCloudMessagingRegistrationId(context);
        if (pushToken == null) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            String token = null;
                            if (task.isSuccessful() && task.getResult() != null) {
                                // Get new Instance ID token
                                token = task.getResult().getToken();


                                    token = task.getResult().getToken();


                            }

                            updateDeviceWithToken(context, token);
                        }
                    });
        } else {
            updateDeviceWithToken(context, pushToken);
        }
    }

    private static void updateDeviceWithToken(final Context context, String token) {
        AuthenticationApiService service = createApiServiceImedSystem(context, AuthenticationApiService.class,false);
        final UpdateDeviceRequest request = new UpdateDeviceRequest();
        request.setGuid(PreferencesHelper.getUUUID(context));
        request.setPushToken(token);

        Call<UpdateDeviceResponse> call = service.updateDevice(request);
        call.enqueue(new Callback<UpdateDeviceResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateDeviceResponse> call, @NonNull Response<UpdateDeviceResponse> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(UPDATE_DEVICE));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateDeviceResponse> call, @NonNull Throwable t) {
                postErrorResponse(new ErrorResponse(UPDATE_DEVICE));
            }
        });
    }

    public static void validate(final Context context, String salesCode) {
        AuthenticationApiService service = createApiServiceImedSystem(context, AuthenticationApiService.class,false);
        final ValidateRequest request = new ValidateRequest();
        request.setStaffNumber(salesCode);

        Call<ValidateResponse> call = service.validate(request);
        call.enqueue(new Callback<ValidateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ValidateResponse> call, @NonNull Response<ValidateResponse> response) {
                if (response.isSuccessful()) {
                    PreferencesHelper.setFullName(context, response.body().getData().getFirstName() + " " + response.body().getData().getSurname());
                    EventBus.getDefault().post(response.body());
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(VALIDATE));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ValidateResponse> call, @NonNull Throwable t) {
                postErrorResponse(new ErrorResponse(VALIDATE));
            }
        });
    }
}
