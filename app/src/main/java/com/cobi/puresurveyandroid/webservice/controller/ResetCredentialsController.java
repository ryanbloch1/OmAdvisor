package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;

import com.cobi.puresurveyandroid.ErrorResponseResetCreds;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ChangePasswordRequest;
import com.cobi.puresurveyandroid.model.ChangePasswordResponse;
import com.cobi.puresurveyandroid.model.CountryCode;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.ResetPasswordRequest;
import com.cobi.puresurveyandroid.model.ResetPasswordResponse;
import com.cobi.puresurveyandroid.model.SendUsernameRequest;
import com.cobi.puresurveyandroid.model.SendUsernameResponse;
import com.cobi.puresurveyandroid.util.FBRemoteConfigHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ApiResponse.DigitalIdResponse;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.DigitalIdInterface;
import com.cobi.puresurveyandroid.webservice.service.ResetCredentialsService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ResetCredentialsController extends BaseApiController {


    public static final String SEND_USERNAME = "customer_send_username";
    public static final String RESET_PASSWORD = "customer_reset_password";
    public static final String GET_COUNTRY_CODES = "customer_country_codes";
    public static final String UPDATE_PASSWORD = "customer_update_password";

    public static void sendUsername(final Context context , String personName, String code, String localNo, String electronicType) {

        SendUsernameRequest request = new SendUsernameRequest(personName, code, localNo, electronicType);

        ResetCredentialsService service = createApiService(context, ResetCredentialsService.class, false);
        Call<SendUsernameResponse> call = service.sendUsername("application/x-www-form-urlencoded", context.getString(R.string.client_id_resetcreds), context.getString(R.string.client_secret_resetcreds), request);
        call.enqueue(new BaseCallback<SendUsernameResponse>(context) {

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponseResetCreds(errorBody, new ErrorResponseResetCreds(SEND_USERNAME));
            }

            @Override
            public void onFailure(Call<SendUsernameResponse> call, Throwable t) {
                postErrorResponseResetCreds(new ErrorResponseResetCreds(SEND_USERNAME));
            }

            @Override
            protected void onSuccess(SendUsernameResponse response) {

                EventBus.getDefault().post(response);
            }
        });
    }


    public static void resetPassword(final Context context ,String startDate, String endDate, String userId ,  String code, String localNo) {

        ResetPasswordRequest request = new ResetPasswordRequest(startDate, endDate, localNo, userId, code);

        ResetCredentialsService service = createApiService(context, ResetCredentialsService.class, false);
        Call<ResetPasswordResponse> call = service.resetPassword("application/json", context.getString(R.string.client_id_resetcreds), context.getString(R.string.client_secret_resetcreds), request);
        call.enqueue(new BaseCallback<ResetPasswordResponse>(context) {

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponseResetCreds(errorBody, new ErrorResponseResetCreds(RESET_PASSWORD));
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                postErrorResponseResetCreds(new ErrorResponseResetCreds(RESET_PASSWORD));
            }

            @Override
                protected void onSuccess(ResetPasswordResponse response) {

                EventBus.getDefault().post(response);
            }
        });
    }


    public static void getPhoneCodes(final Context context) {
        ResetCredentialsService service = createApiService(context, ResetCredentialsService.class, false);
        Call<List<CountryCode>> call = service.getPhoneCodes("application/x-www-form-urlencoded", context.getString(R.string.client_id_resetcreds), context.getString(R.string.client_secret_resetcreds));
        call.enqueue(new BaseCallback<List<CountryCode>>(context) {

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(GET_COUNTRY_CODES));
            }

            @Override
            public void onFailure(Call<List<CountryCode>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(GET_COUNTRY_CODES));
            }

            @Override
            protected void onSuccess(List<CountryCode> response) {

                EventBus.getDefault().post(response);
            }
        });
    }

    public static void updatePassword(final Context context, String token, String userId, String password) {

        ChangePasswordRequest request = new ChangePasswordRequest("no", password, userId);

        ResetCredentialsService service = createApiService(context, ResetCredentialsService.class, false);
        Call<ChangePasswordResponse> call = service.updatePassword("bearer "+token, "application/json", context.getString(R.string.client_id_resetcreds), context.getString(R.string.client_secret_resetcreds), request);
        call.enqueue(new BaseCallback<ChangePasswordResponse>(context) {

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(UPDATE_PASSWORD));
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(UPDATE_PASSWORD));
            }

            @Override
            protected void onSuccess(ChangePasswordResponse response) {

                EventBus.getDefault().post(response);
            }
        });
    }




}
