package com.cobi.puresurveyandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.webservice.ApiResponse.TokenResponse;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.AccessTokenServiceInterface;
import com.cobi.puresurveyandroid.webservice.controller.BaseApiController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Authenticator;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;

import static com.cobi.puresurveyandroid.webservice.controller.CSIApiController.logout;

public class TokenAuthenticator implements Authenticator {

    private Context context;
    private static final String INVALID_TOKEN_MESSAGE = "Token is invalid";

    public TokenAuthenticator(Context context) {
        this.context = context;
    }

    @Override
    public Request authenticate(Route route, final Response response) throws IOException {

        AccessTokenServiceInterface service = BaseApiController.createApiService(context, AccessTokenServiceInterface.class, false);

        if (response.code() == 401) {

            try {
                BufferedSource body = response.body().source();
                if (body != null) {
                    body.request(Long.MAX_VALUE);
                    Buffer buffer = body.buffer();
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = response.body().contentType();
                    if (contentType != null) {
                        charset = contentType.charset(Charset.forName("UTF-8"));
                    }

                    String string = buffer.clone().readString(charset);

                    ErrorResponse parsedResponse = new Gson().fromJson(string, ErrorResponse.class);
                    String message = parsedResponse.getMessage();
                    if (message.equals(INVALID_TOKEN_MESSAGE)) {

                        retrofit2.Response retrofitResponse = service.refreshToken("application/x-www-form-urlencoded", "12674d38-0a24-4e74-985d-272e15feeb3a", "T7oF6lK7dJ4jG1jH6iO3uE6aP6nI0wP7fL1jT5fB7vK7gS0xW7", "IMEDUserProfile.me IMEDUserProfile.retrieve", "refresh_token", PreferencesHelper.getRefreshoken(context)).execute();

                        if (retrofitResponse != null) {
                            if (retrofitResponse.isSuccessful()) {

                                TokenResponse refreshTokenResponse = (TokenResponse) retrofitResponse.body();

                                String newAccessToken = refreshTokenResponse.getAccessToken();

                                PreferencesHelper.setAccessToken(context, newAccessToken);
                                PreferencesHelper.setRefreshToken(context, refreshTokenResponse.getRefreshToken());

                                return response.request().newBuilder().header("Authorization", newAccessToken).build();
                            } else {
                                CSIApiController.requestInValidateAccessToken(context, PreferencesHelper.getAccessToken(context));

                                return null;
                            }
                        }
                        else {
                            CSIApiController.requestInValidateAccessToken(context,PreferencesHelper.getAccessToken(context));

                            return null;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        return null;
    }
}