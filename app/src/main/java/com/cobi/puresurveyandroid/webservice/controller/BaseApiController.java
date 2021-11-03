package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import android.content.Intent;

import com.cobi.puresurveyandroid.ErrorResponseResetCreds;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.activity.LoginActivity;
import com.cobi.puresurveyandroid.model.BaseErrorResponse;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.StringErrorResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEvent;
import com.cobi.puresurveyandroid.util.GsonHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.util.TokenAuthenticator;
import com.cobi.puresurveyandroid.webservice.HttpBuilder.SslCertConfiguration;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.AccessTokenServiceInterface;
import org.xms.f.remoteconfig.ExtensionRemoteConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApiController {
    public static final int UNKNOWN_STATUS_CODE = -1;
    public static final int UNAUTHORIZED_STATUS_CODE = 401;
    private static final String ISO8601_WEBSERVICE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static Retrofit sRetrofit;
    private static Retrofit eRetrofit;
    private static Retrofit imedSystemRetrofit;
    private static Retrofit imedSystemCentralRetrofit;
    private static Retrofit snapAppRetrofit;
    private static Retrofit plumlineRetrofit;
    private static Retrofit pingAuthenticationRetrofit;



    public static Retrofit getRetrofitInstance(Context webserviceContext, boolean sslPinned) {
        if (sRetrofit == null) {
            final Context context = webserviceContext.getApplicationContext();

            sRetrofit = buildRetrofit(sRetrofit, sslPinned, context, context.getString(R.string.protocol_secure), context.getString(R.string.base_url), context.getResources().getString(R.string.base_url_path), false);
        }

        return sRetrofit;
    }


    public static Retrofit getRetrofitInstanceEvents(Context webserviceContext, boolean sslPinned) {
        if (eRetrofit == null) {

            final Context context = webserviceContext.getApplicationContext();
            eRetrofit = buildRetrofit(eRetrofit, sslPinned, context, context.getString(R.string.protocol_secure), context.getString(R.string.base_url_events), context.getString(R.string.base_path_events), false);
        }

        return eRetrofit;
    }

    public static Retrofit getRetrofitInstancePing(Context webserviceContext, boolean sslPinned) {
        if (pingAuthenticationRetrofit == null) {

            final Context context = webserviceContext.getApplicationContext();
            pingAuthenticationRetrofit = buildRetrofit(pingAuthenticationRetrofit, sslPinned, context, context.getString(R.string.protocol_secure), context.getString(R.string.base_url_ping),"", false);
        }

        return pingAuthenticationRetrofit;
    }



    public static Retrofit getRetrofitInstanceImedSystem(Context webserviceContext, boolean sslPinned) {
        if (imedSystemRetrofit == null) {

            final Context context = webserviceContext.getApplicationContext();
            imedSystemRetrofit = buildRetrofit(imedSystemRetrofit, sslPinned, context, context.getString(R.string.protocol_secure), context.getString(R.string.base_url_imed), "", false);
        }

        return imedSystemRetrofit;
    }

    public static Retrofit getRetrofitInstanceImedSystemCentral(Context webserviceContext, boolean sslPinned) {
        if (imedSystemCentralRetrofit == null) {

            final Context context = webserviceContext.getApplicationContext();
            imedSystemCentralRetrofit = buildRetrofit(imedSystemCentralRetrofit, sslPinned, context, context.getString(R.string.protocol_secure), context.getString(R.string.base_url_imed), context.getString(R.string.base_path_imed), false);
        }

        return imedSystemCentralRetrofit;
    }


    public static Retrofit getRetrofitInstanceSnap(Context webserviceContext, boolean sslPinned) {
        if (snapAppRetrofit == null) {
            final Context context = webserviceContext.getApplicationContext();

            snapAppRetrofit = buildRetrofit(snapAppRetrofit, sslPinned, context, context.getString(R.string.protocol_secure), context.getString(R.string.base_url_snap), "/", true);
        }

        return snapAppRetrofit;
    }

    public static Retrofit getRetrofitInstancePlumline(Context webserviceContext, boolean sslPinned) {
        if (plumlineRetrofit == null) {
            final Context context = webserviceContext.getApplicationContext();

            plumlineRetrofit = buildRetrofit(plumlineRetrofit, sslPinned, context, context.getString(R.string.protocol_unsecure), context.getString(R.string.base_url_plumline), "/", false);
        }

        return plumlineRetrofit;
    }


    protected static <S> S createApiServiceImedSystem(Context webserviceContext, Class<S> serviceClass, boolean sslPinned) {

        Retrofit retrofit = getRetrofitInstanceImedSystem(webserviceContext, sslPinned);
        return retrofit.create(serviceClass);
    }

    protected static <S> S createApiServiceImedSystemCentral(Context webserviceContext, Class<S> serviceClass, boolean sslPinned) {
        Retrofit retrofit = getRetrofitInstanceImedSystemCentral(webserviceContext, sslPinned);
        return retrofit.create(serviceClass);
    }


    protected static <S> S createApiServicePlumlines(Context webserviceContext, Class<S> serviceClass, boolean sslPinned) {

        Retrofit retrofit = getRetrofitInstancePlumline(webserviceContext, sslPinned);
        return retrofit.create(serviceClass);
    }

    protected static <S> S createApiServicePing(Context webserviceContext, Class<S> serviceClass, boolean sslPinned) {

        Retrofit retrofit = getRetrofitInstancePing(webserviceContext, sslPinned);
        return retrofit.create(serviceClass);
    }



    public static <S> S createApiService(Context webserviceContext, Class<S> serviceClass, boolean sslPinned) {

        Retrofit retrofit = getRetrofitInstance(webserviceContext, sslPinned);
        return retrofit.create(serviceClass);
    }

    protected static <S> S createApiServiceSnap(Context webserviceContext, Class<S> serviceClass, boolean sslPinned) {

        Retrofit retrofit = getRetrofitInstanceSnap(webserviceContext, sslPinned);
        return retrofit.create(serviceClass);
    }


    protected static <S> S createApiServiceEvents(Context webserviceContext, Class<S> serviceClass, boolean sslPinned) {


        Retrofit retrofit = getRetrofitInstanceEvents(webserviceContext, sslPinned);
        return retrofit.create(serviceClass);
    }

    protected static void postErrorResponse(ResponseBody errorBody, ErrorResponse errorResponse) {
        try {
            String string = errorBody.string();
            ErrorResponse parsedResponse = new Gson().fromJson(string, errorResponse.getClass());

            if (parsedResponse.getMessage() == null || parsedResponse.getMessage().isEmpty()) {
                parsedResponse.setMessage("So sorry! We have encountered a technical error. Please try again later.");
            }

            parsedResponse.setRequest(errorResponse.getRequest());
            EventBus.getDefault().post(parsedResponse);
        } catch (Exception e) {
            postErrorResponse(errorResponse);
        }
    }


    protected static void postErrorResponseResetCreds(ResponseBody errorBody, ErrorResponseResetCreds errorResponse) {
        try {
            String string = errorBody.string();
            ErrorResponseResetCreds parsedResponse = new Gson().fromJson(string, errorResponse.getClass());

            if (parsedResponse.getMessage() == null || parsedResponse.getMessage().isEmpty()) {


                parsedResponse.setMessage(null);
            }

            parsedResponse.setRequest(errorResponse.getRequest());
            EventBus.getDefault().post(parsedResponse);
        } catch (Exception e) {
            postErrorResponseResetCreds(errorResponse);
        }
    }


    protected static void postErrorResponseResetCreds(BaseErrorResponse errorResponse) {
        errorResponse.setStatus(UNKNOWN_STATUS_CODE);
        EventBus.getDefault().post(errorResponse);
    }


    protected static void postErrorResponse(ErrorResponse errorResponse) {
        errorResponse.setStatus(UNKNOWN_STATUS_CODE);
        EventBus.getDefault().post(errorResponse);
    }


    protected static void postErrorResponseString(StringErrorResponse errorResponse) {
        EventBus.getDefault().post(errorResponse);
    }


    public static Retrofit buildRetrofit(Retrofit retrofit, Boolean sslPinned, Context context, String protocol, String baseUrl, String urlPath, Boolean hasInterceptor) {

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient.Builder builder;
        if (sslPinned) {
            try {
                builder = SslCertConfiguration.getSSLConfig(context).newBuilder();
            } catch (Exception e) {
                builder = new OkHttpClient.Builder();
            }
        } else {
            builder = new OkHttpClient.Builder();
        }

        if (hasInterceptor) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Basic QWRtaW5pc3RyYXRvcjpPTV9BbmRyMDFkX0RldmljM19BUCE=").build();
                    return chain.proceed(request);
                }
            });
        }
        builder.readTimeout(1, TimeUnit.MINUTES);
        builder.connectTimeout(1, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);

        builder.authenticator(new TokenAuthenticator(context));
        builder.cache(cache);

        retrofit = new Retrofit.Builder().client(builder.build()).baseUrl(protocol + baseUrl + urlPath).addConverterFactory(GsonConverterFactory.create(GsonHelper.getGson())).build();

        return retrofit;
    }
}
