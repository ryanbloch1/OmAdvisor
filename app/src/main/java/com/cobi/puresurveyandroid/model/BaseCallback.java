package com.cobi.puresurveyandroid.model;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cobi.puresurveyandroid.activity.LoginActivity;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<T> {

    private final Context context;

    public BaseCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            try {
                onError(response.errorBody());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
    protected abstract void onSuccess(T response);

    protected abstract void onError(ResponseBody errorBody) throws IOException;
}
