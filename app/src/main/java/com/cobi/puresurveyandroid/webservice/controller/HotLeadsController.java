package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import android.util.Log;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.Advisor;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.DeclineReason;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Lead;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.PipelinesInterface;
import com.cobi.puresurveyandroid.webservice.service.HotLeadsApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.cobi.puresurveyandroid.util.FBRemoteConfigHelper.getClientId;
import static com.cobi.puresurveyandroid.util.FBRemoteConfigHelper.getClientSecret;

public class HotLeadsController extends BaseApiController {

    public static final String GET_DECLINE_REASONS = "decline_reasons";
    public static final String REJECT_LEAD = "reject_lead";
    public static final String ACCEPT_LEAD = "reject_lead";
    public static final String REFER_LEAD = "refer_lead";
    public static final String SEARCH_ADVISORS = "search_advisors";

    public static void getDeclineReasons(Context context) {

        HotLeadsApiService service = createApiService(context, HotLeadsApiService.class, true);

        Call<List<DeclineReason>> call = service.getDeclineReasons(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret));

        call.enqueue(new BaseCallback<List<DeclineReason>>(context) {
            @Override
            protected void onSuccess(List<DeclineReason> response) {
                EventBus.getDefault().post(response);
            }
            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(GET_DECLINE_REASONS));

            }
            @Override
            public void onFailure(Call<List<DeclineReason>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(GET_DECLINE_REASONS));

            }
        });
    }

    public static void rejectLead(Context context, String salesCode, long leadId, int reasonId) {

        HotLeadsApiService service = createApiService(context, HotLeadsApiService.class, true);

        Call<String> call = service.declineLead(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode, leadId, reasonId);

        call.enqueue(new BaseCallback<String>(context) {
            @Override
            protected void onSuccess(String response) {
                EventBus.getDefault().post(response);

            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(REJECT_LEAD));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                postErrorResponse(new ErrorResponse(REJECT_LEAD));
            }
        });
    }


    public static void acceptLead(Context context, String salesCode, long leadId) {

        HotLeadsApiService service = createApiService(context, HotLeadsApiService.class, true);

        Call<String> call = service.acceptLead(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode, leadId);

        call.enqueue(new BaseCallback<String>(context) {
            @Override
            protected void onSuccess(String response) {
                EventBus.getDefault().post(response);

            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(ACCEPT_LEAD));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                postErrorResponse(new ErrorResponse(ACCEPT_LEAD));
            }
        });
    }


    public static void referLead(Context context, String salesCode, String targetSalesCode, long leadId) {

        HotLeadsApiService service = createApiService(context, HotLeadsApiService.class, true);

        Call<String> call = service.referLead(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode,targetSalesCode, leadId);

        call.enqueue(new BaseCallback<String>(context) {
            @Override
            protected void onSuccess(String response) {
                EventBus.getDefault().post(response);

            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(REFER_LEAD));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                postErrorResponse(new ErrorResponse(REFER_LEAD));
            }
        });
    }

    public static void searchAdvisors(Context context, String salesCode, String name, String surname) {

        HotLeadsApiService service = createApiService(context, HotLeadsApiService.class, true);

        Call<List<Advisor>> call = service.searchIntermediary(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), name, surname, salesCode);

        call.enqueue(new BaseCallback<List<Advisor>>(context) {
            @Override
            protected void onSuccess(List<Advisor> response) {
                EventBus.getDefault().post(response);

            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(SEARCH_ADVISORS));
            }

            @Override
            public void onFailure(Call<List<Advisor>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(SEARCH_ADVISORS));
            }
        });
    }
}
