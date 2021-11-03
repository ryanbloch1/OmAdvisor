package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;

import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Notification;
import com.cobi.puresurveyandroid.model.NotificationStatusRequest;
import com.cobi.puresurveyandroid.model.NotificationsRequest;
import com.cobi.puresurveyandroid.model.NotificationsResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.ImedAuthenticationInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.NotificationsService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;

public class NotificationManagerController extends BaseApiController {

    public static final String GET_NOTIFICATIONS = "get_notifications";
    public static final String POST_NOTIFICATIONS = "post_notifications";
    public static final String STATUS_CHANGE = "status_change";


    public static void getNotifications(final Context context, String token, String staffId) {

        NotificationsService service = createApiServiceImedSystemCentral(context, NotificationsService.class, false);

        Call<NotificationsResponse> call = service.getNotifications(IMED_API_KEY, IMED_APP_ID,  "Bearer "+ token, staffId);

        call.enqueue(new BaseCallback<NotificationsResponse>(context) {
            @Override
            protected void onSuccess(NotificationsResponse response) {
                EventBus.getDefault().post(response);


            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(GET_NOTIFICATIONS));

            }

            @Override
            public void onFailure(Call<NotificationsResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(GET_NOTIFICATIONS));

            }
        });
    }


    public static void postNotifications(final Context context, String token, NotificationsRequest request) {

        NotificationsService service = createApiServiceImedSystemCentral(context, NotificationsService.class, false);

        Call<ResponseBody> call = service.postNotifications(IMED_API_KEY, IMED_APP_ID,  "Bearer "+ token, request);

        call.enqueue(new BaseCallback<ResponseBody>(context) {
            @Override
            protected void onSuccess(ResponseBody response) {
                EventBus.getDefault().post(response);


            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(POST_NOTIFICATIONS));

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postErrorResponse(new ErrorResponse(POST_NOTIFICATIONS));

            }
        });
    }


    public static void statusChange(final Context context, String token, NotificationStatusRequest request) {

        NotificationsService service = createApiServiceImedSystemCentral(context, NotificationsService.class, false);

        Call<Boolean> call = service.statusChange(IMED_API_KEY, IMED_APP_ID,  "Bearer "+ token, request);

        call.enqueue(new BaseCallback<Boolean>(context) {
            @Override
            protected void onSuccess(Boolean response) {
                EventBus.getDefault().post(response);


            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(STATUS_CHANGE));

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                postErrorResponse(new ErrorResponse(STATUS_CHANGE));

            }
        });
    }


}
