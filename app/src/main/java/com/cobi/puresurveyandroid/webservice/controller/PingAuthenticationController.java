package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.PingDigitalIdResponse;
import com.cobi.puresurveyandroid.model.RefreshTokenResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ApiResponse.DigitalIdResponse;
import com.cobi.puresurveyandroid.webservice.ApiResponse.TokenResponse;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.PingAuthenticationInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.PingAuthenticationInterface;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class PingAuthenticationController extends BaseApiController {

    public static final String CUSTOMER_GET_DIGITAL_ID = "customer_digital_id";
    public static final String CUSTOMER_GET_TOKEN = "customer_token";
    public static final String CUSTOMER_REFRESH_TOKEN = "customer_refresh_token";


    public static void getToken(final Context context, String username, String password) {
        PingAuthenticationInterface service = createApiServicePing(context, PingAuthenticationInterface.class, false);
        Call<TokenResponse> call = service.getToken("application/x-www-form-urlencoded", context.getString(R.string.ping_id), context.getString(R.string.ping_secret), "openid profile IMEDUserProfile.me IMEDUserProfile.retrieve", "password", username, password);
        call.enqueue(new BaseCallback<TokenResponse>(context) {

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_TOKEN));
            }

            @Override
            protected void onSuccess(TokenResponse response) {
                PreferencesHelper.setAccessToken(context, response.getAccessToken());
                PreferencesHelper.setRefreshToken(context, response.getRefreshToken());
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                PreferencesHelper.setAccessToken(context, null);
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_TOKEN));
            }
        });
    }

    public static void refreshToken(final Context context, String refreshToken) {
        PingAuthenticationInterface service = createApiServicePing(context, PingAuthenticationInterface.class, false);
        Call<TokenResponse> call = service.refreshToken("application/x-www-form-urlencoded", context.getString(R.string.ping_id), context.getString(R.string.ping_secret), "refresh_token", refreshToken);
        call.enqueue(new BaseCallback<TokenResponse>(context) {

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_REFRESH_TOKEN));
            }

            @Override
            protected void onSuccess(TokenResponse response) {
                PreferencesHelper.setAccessToken(context, response.getAccessToken());
                PreferencesHelper.setRefreshToken(context, response.getRefreshToken());
                EventBus.getDefault().post(new RefreshTokenResponse(response));
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                PreferencesHelper.setAccessToken(context, null);
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_REFRESH_TOKEN));
            }
        });
    }

    public static void getDigitalID(final Context context, String accessToken) {

        PingAuthenticationInterface service = createApiServicePing(context, PingAuthenticationInterface.class, false);
        Call<PingDigitalIdResponse> call = service.getDigitalID("Bearer "+accessToken, "application/x-www-form-urlencoded");
        call.enqueue(new BaseCallback<PingDigitalIdResponse>(context) {

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_DIGITAL_ID));
            }

            @Override
            public void onFailure(Call<PingDigitalIdResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_DIGITAL_ID));
            }


            @Override
            protected void onSuccess(PingDigitalIdResponse response) {
                PreferencesHelper.setCommonName(context, response.getDigitalID());

                EventBus.getDefault().post(response);
            }
        });
    }

}
