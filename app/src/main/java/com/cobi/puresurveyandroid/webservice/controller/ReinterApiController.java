package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;

import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.Customer;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Affordability;
import com.cobi.puresurveyandroid.model.Campaigns;
import com.cobi.puresurveyandroid.model.Corporate;
import com.cobi.puresurveyandroid.model.Demographic;
import com.cobi.puresurveyandroid.model.Engagement;
import com.cobi.puresurveyandroid.model.Holdings;
import com.cobi.puresurveyandroid.model.LikeYou;
import com.cobi.puresurveyandroid.model.Maturity;
import com.cobi.puresurveyandroid.model.Needs;
import com.cobi.puresurveyandroid.model.ReIntermediary;
import com.cobi.puresurveyandroid.model.ReProduct;
import com.cobi.puresurveyandroid.model.RecommendedProduct;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.ReintermediationInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;

public class ReinterApiController extends BaseApiController {

    private static final String APP_ID = "889df92f-2ce6-4163-88c9-a51d27990f50";
//    private static final String IMED_API_KEY = "MU0zZF9SMyFuVDNSTSNkIUB0ITBuLSR1dEguNVlzVDNtOlchdGgrIUQ9ODg5ZGY5MmYtMmNlNi00MTYzLTg4YzktYTUxZDI3OTkwZjUw";

    public static final String RE = "re";

    public static final String RE_LIST = "re-list";


    public static void GetReIntermediaries(Context context, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, false);

        Call<List<ReIntermediary>> call = service.GetReIntermediaries(IMED_API_KEY, IMED_APP_ID, "Bearer "+PreferencesHelper.getImedToken(context), staffId);

        call.enqueue(new BaseCallback<List<ReIntermediary>>(context) {
            @Override
            protected void onSuccess(List<ReIntermediary> response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE_LIST));

            }

            @Override
            public void onFailure(Call<List<ReIntermediary>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE_LIST));

            }
        });
    }


    public static void GetDemographics(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Demographic> call = service.GetDemographics(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Demographic>(context) {
            @Override
            protected void onSuccess(Demographic response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Demographic> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }

    public static void GetAffordability(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Affordability> call = service.GetAffordability(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Affordability>(context) {
            @Override
            protected void onSuccess(Affordability response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Affordability> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


    public static void GetProducts(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<List<ReProduct>> call = service.GetProducts(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<List<ReProduct>>(context) {
            @Override
            protected void onSuccess(List<ReProduct> response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<List<ReProduct>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


    public static void GetCorporate(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Corporate> call = service.GetCorporate(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Corporate>(context) {
            @Override
            protected void onSuccess(Corporate response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Corporate> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }

    public static void GetNeeds(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Needs> call = service.GetNeeds(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Needs>(context) {
            @Override
            protected void onSuccess(Needs response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Needs> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }

    public static void GetHoldings(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Holdings> call = service.GetHoldings(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Holdings>(context) {
            @Override
            protected void onSuccess(Holdings response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Holdings> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }

    public static void GetEngagement(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Engagement> call = service.GetEngagement(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Engagement>(context) {
            @Override
            protected void onSuccess(Engagement response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Engagement> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


    public static void GetRecommended(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<RecommendedProduct> call = service.GetRecommended(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<RecommendedProduct>(context) {
            @Override
            protected void onSuccess(RecommendedProduct response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<RecommendedProduct> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }

    public static void GetCampaigns(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Campaigns> call = service.GetCampaigns(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Campaigns>(context) {
            @Override
            protected void onSuccess(Campaigns response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Campaigns> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }

    public static void GetLikeYou(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<LikeYou> call = service.GetLikeYou(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<LikeYou>(context) {
            @Override
            protected void onSuccess(LikeYou response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<LikeYou> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


    public static void GetMaturities(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<List<Maturity>> call = service.GetMaturities(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<List<Maturity>>(context) {
            @Override
            protected void onSuccess(List<Maturity> response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<List<Maturity>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


    public static void GetCustomer(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<Customer> call = service.GetCustomer(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<Customer>(context) {
            @Override
            protected void onSuccess(Customer response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


    public static void PostAccept(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<ResponseBody> call = service.PostAccept(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


    public static void PostDecline(Context context, String customerId, String staffId) {

        ReintermediationInterface service = createApiServiceImedSystemCentral(context, ReintermediationInterface.class, true);

        Call<ResponseBody> call = service.PostDecline(IMED_API_KEY,IMED_APP_ID, staffId, "Bearer "+PreferencesHelper.getImedToken(context),  customerId);

        call.enqueue(new BaseCallback<ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(RE));

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RE));

            }
        });
    }


}
