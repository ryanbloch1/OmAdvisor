package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;

import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.MyCpdData;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.PlumlineInterface;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class PlumlineApiController extends BaseApiController {

    public static final String CPD = "cpd";

    public static void getUserCpd(final Context context, String username ){
        PlumlineInterface service = createApiServicePlumlines(context, PlumlineInterface.class, false);
        Call<MyCpdData> call = service.getUserCpd("vytJnUDk!B8jrNTG3+LggOB", username);
        call.enqueue(new BaseCallback<MyCpdData>(context) {

            @Override
            public void onFailure(Call<MyCpdData> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CPD));
            }

            @Override
            protected void onSuccess(MyCpdData response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CPD));
            }
        });
    }
}
