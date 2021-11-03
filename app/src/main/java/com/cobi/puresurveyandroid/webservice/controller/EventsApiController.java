package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEvent;
//import com.cobi.puresurveyandroid.model.UserCustomerEvent_Table;
import com.cobi.puresurveyandroid.model.UserCustomerEvent_Table;
import com.cobi.puresurveyandroid.model.UserCustomerEventsRequest;
import com.cobi.puresurveyandroid.model.UserCustomerEventsResponse;
import com.cobi.puresurveyandroid.model.MatrixRequest;
import com.cobi.puresurveyandroid.model.MatrixResponse;
import com.cobi.puresurveyandroid.model.ReadReceiptRequest;
import com.cobi.puresurveyandroid.model.ReadReceiptResponse;
import com.cobi.puresurveyandroid.model.ReceiveReceiptRequest;
import com.cobi.puresurveyandroid.model.ReceiveReceiptResponse;
import com.cobi.puresurveyandroid.model.ReplyRequest;
import com.cobi.puresurveyandroid.model.ReplyResponse;
import com.cobi.puresurveyandroid.model.User;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.service.EventsApiService;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/10/07.
 */

public class EventsApiController extends BaseApiController {

    public static final String EVENTS = "events";
    public static final String REPLY = "reply";
    public static final String READ_RECEIPT = "readreceipt";
    public static final String RECEIVE_RECEIPT = "receivereceipt";
    public static final String MATRIX = "matrix";
    private static final String TAG = EventsApiController.class.getSimpleName();

    public static Call<UserCustomerEventsResponse> events(final Context context, Date since) {
        EventsApiService service = createApiServiceImedSystem(context, EventsApiService.class,false);
        final UserCustomerEventsRequest request = new UserCustomerEventsRequest();
        request.setGuid(PreferencesHelper.getUUUID(context));
        if (since == null) {
            since = DateHelper.ISO8601ToDate("1970-01-01T00:00:00");
        }

        request.setSince(DateHelper.dateToISO8601(since));
        Call<UserCustomerEventsResponse> call = service.events(request);
        call.enqueue(new Callback<UserCustomerEventsResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserCustomerEventsResponse> call, @NonNull Response<UserCustomerEventsResponse> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    if (response.body().getData().getEvents().size() > 0) {
                        List<UserCustomerEvent> events = response.body().getData().events;
                        List<String> messageIds = new ArrayList<String>();

                        ModelAdapter<User> userModelAdapter = FlowManager.getModelAdapter(User.class);
                        for (UserCustomerEvent e : events) {
                            if (e.getType().toLowerCase().equals("message") || e.getType().toLowerCase().equals("reply")) {
                                if (e.getFrom() != null) {
                                    userModelAdapter.save(new User(e.getFrom(), e.getFrom()));
                                    userModelAdapter.closeUpdateStatement();
                                } else {
                                    userModelAdapter.save(new User("null", "null"));
                                    e.setFrom("Unknown");
                                }

                                messageIds.add(e.getMessageId());
                            }
                        }
                        ModelAdapter<UserCustomerEvent> adapter = FlowManager.getModelAdapter(UserCustomerEvent.class);
                        adapter.saveAll(events);

                        for (UserCustomerEvent e : events) {
                            if (e.getType().toLowerCase().equals("recall")) {
                                UserCustomerEvent.handleRecall(context, e.getMessageId());
                            }
                        }

                        PreferencesHelper.setSince(context, DateHelper.ISO8601ToDate(response.body().getData().getTimestamp()));

                        EventBus.getDefault().post(response.body());

                        if (messageIds.size() > 0) {
                            receiveReceipt(context, messageIds);
                        }
                        if (ShortcutBadger.isBadgeCounterSupported(context)) {
                            int unreadEvents = SQLite.select().from(UserCustomerEvent.class).where(UserCustomerEvent_Table.type.eq("message")).and(UserCustomerEvent_Table.isRead.eq(false)).queryList().size();
                            ShortcutBadger.applyCount(context, unreadEvents);
                        }
                    } else {
                        Log.d(TAG, "onResponse - no events!");
                        EventBus.getDefault().post(response.body());
                    }

                    EventBus.getDefault().post(response.body());
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(EVENTS));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserCustomerEventsResponse> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    postErrorResponse(new ErrorResponse(EVENTS));
                }
            }
        });

        return call;
    }

    public static void reply(Context context, String messageId, String message) {
        EventsApiService service = createApiServiceImedSystem(context, EventsApiService.class,false);
        ReplyRequest request = new ReplyRequest();
        request.setBody(message);
        request.setMessageId(messageId);

        Call<ReplyResponse> call = service.reply(request);
        call.enqueue(new Callback<ReplyResponse>() {
            @Override
            public void onResponse(Call<ReplyResponse> call, Response<ReplyResponse> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(REPLY));
                }
            }

            @Override
            public void onFailure(Call<ReplyResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(REPLY));
            }
        });
    }

    public static void retrieveMatrix(Context context, String guid) {
        EventsApiService service = createApiServiceImedSystem(context, EventsApiService.class,false);
        MatrixRequest request = new MatrixRequest();
        request.setGuid(guid);
        request.setTokenId(PreferencesHelper.getCloudMessagingRegistrationId(context));


        Call<MatrixResponse> call = service.retrieveMatrix(request);
        call.enqueue(new Callback<MatrixResponse>() {
            @Override
            public void onResponse(Call<MatrixResponse> call, Response<MatrixResponse> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(MATRIX));
                }
            }

            @Override
            public void onFailure(Call<MatrixResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(MATRIX));
            }
        });
    }

    public static void receiveReceipt(Context context, List<String> messageIds) {
        EventsApiService service = createApiServiceImedSystem(context, EventsApiService.class,false);
        ReceiveReceiptRequest request = new ReceiveReceiptRequest();
        request.setMessageIds(messageIds);

        Call<ReceiveReceiptResponse> call = service.receiveReceipt(request);
        call.enqueue(new Callback<ReceiveReceiptResponse>() {
            @Override
            public void onResponse(Call<ReceiveReceiptResponse> call, Response<ReceiveReceiptResponse> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(RECEIVE_RECEIPT));
                }
            }

            @Override
            public void onFailure(Call<ReceiveReceiptResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(RECEIVE_RECEIPT));
            }
        });
    }

    public static void readReceipt(Context context, List<String> messageIds) {
        EventsApiService service = createApiServiceImedSystem(context, EventsApiService.class,false);
        ReadReceiptRequest request = new ReadReceiptRequest();
        request.setMessageIds(messageIds);

        Call<ReadReceiptResponse> call = service.readReceipt(request);
        call.enqueue(new Callback<ReadReceiptResponse>() {
            @Override
            public void onResponse(Call<ReadReceiptResponse> call, Response<ReadReceiptResponse> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
                } else {
                    postErrorResponse(response.errorBody(), new ErrorResponse(READ_RECEIPT));
                }
            }

            @Override
            public void onFailure(Call<ReadReceiptResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(READ_RECEIPT));
            }
        });
    }
}
