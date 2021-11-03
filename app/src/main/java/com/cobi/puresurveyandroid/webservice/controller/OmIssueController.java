package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.IssueLog;
import com.cobi.puresurveyandroid.model.MyCpdData;
import com.cobi.puresurveyandroid.model.OMIssueLog;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.OmIssueInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.PlumlineInterface;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;

public class OmIssueController extends BaseApiController {

    public static void postIncident(final Context context, OMIssueLog request) {
        OmIssueInterface service = createApiServiceImedSystemCentral(context, OmIssueInterface.class, false);
        Call<ResponseBody> call = service.postIncident(IMED_API_KEY, IMED_APP_ID, "application/json","Bearer "+ PreferencesHelper.getImedToken(context), request);
        call.enqueue(new BaseCallback<ResponseBody>(context) {

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

            @Override
            protected void onSuccess(ResponseBody response) {
                String s;
            }

            @Override
            protected void onError(ResponseBody errorBody) throws IOException {
                System.out.println(errorBody.string());

            }
        });
    }

    public static void postAppIncident(final Context context, IssueLog request) {
        OmIssueInterface service = createApiServiceImedSystemCentral(context, OmIssueInterface.class, false);
        Call<ResponseBody> call = service.postAppIncident(IMED_API_KEY, IMED_APP_ID,"application/json","Bearer "+ PreferencesHelper.getImedToken(context), request);
        call.enqueue(new BaseCallback<ResponseBody>(context) {

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

            @Override
            protected void onSuccess(ResponseBody response) {
                String s;
            }

            @Override
            protected void onError(ResponseBody errorBody) throws IOException {
                System.out.println(errorBody.string());

            }
        });
    }
}
