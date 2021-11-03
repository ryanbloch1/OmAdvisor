package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;

import com.cobi.puresurveyandroid.model.Agenda;
import com.cobi.puresurveyandroid.model.AgendaResponse;
import com.cobi.puresurveyandroid.model.BaseCallback;

import com.cobi.puresurveyandroid.model.CheckedInResponse;
import com.cobi.puresurveyandroid.model.CheckedOutResponse;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.EventMessage;
import com.cobi.puresurveyandroid.model.EventsResponse;
import com.cobi.puresurveyandroid.model.LikeRequest;
import com.cobi.puresurveyandroid.model.MyEventsResponse;
import com.cobi.puresurveyandroid.model.PostMessageRequest;
import com.cobi.puresurveyandroid.model.PostUserRequest;
import com.cobi.puresurveyandroid.model.Question;
import com.cobi.puresurveyandroid.model.RatingRequest;

import okhttp3.ResponseBody;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.EventsInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;

public class OMEventsApiController extends BaseApiController {

    public static final String CUSTOMER_GET_EVENTS = "customer_events";
    public static final String CUSTOMER_GET_MYEVENTS = "customer_myevents";
    public static final String CUSTOMER_GET_EVENT = "customer_event";
    public static final String CUSTOMER_GET_MEDIA = "customer_media";
    public static final String CUSTOMER_GET_AGENDA = "customer_agenda";
    public static final String CUSTOMER_PUT_CHECKIN = "customer_checkin";
    public static final String CUSTOMER_PUT_CHECKOUT = "customer_checkout";
    public static final String CUSTOMER_GET_MESSAGES = "customer_get_messages";
    public static final String CUSTOMER_POST_MESSAGE = "customer_post_message";
    public static final String CUSTOMER_POST_LIKE = "customer_post_like";
    public static final String CUSTOMER_POST_USER = "customer_post_user";
    public static final String CUSTOMER_GET_SPEAKER_QUESTION = "customer_get_speaker_question";
    public static final String CUSTOMER_GET_EVENT_QUESTION = "customer_get_event_question";
    public static final String CUSTOMER_POST_RATING = "customer_post_rating";
    private static final String REGISTER = "register";


    public static void getEvents(Context context) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<EventsResponse> call = service.getEvents("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",PreferencesHelper.getCommonName(context), PreferencesHelper.getSegment(context));
        call.enqueue(new BaseCallback<EventsResponse>(context) {

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_EVENTS));
            }

            @Override
            protected void onSuccess(EventsResponse response) {

                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_EVENTS));
            }
        });
    }

    public static void getMyEvents(Context context) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<MyEventsResponse> call = service.getMyEvents("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",PreferencesHelper.getCommonName(context), PreferencesHelper.getSegment(context));
        call.enqueue(new BaseCallback<MyEventsResponse>(context) {

            @Override
            public void onFailure(Call<MyEventsResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_MYEVENTS));
            }

            @Override
            protected void onSuccess(MyEventsResponse response) {

                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_MYEVENTS));
            }
        });
    }

    public static void getEvent(Context context, String eventId) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<SingleEvent> call = service.getEvent("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",eventId, PreferencesHelper.getCommonName(context));
        call.enqueue(new BaseCallback<SingleEvent>(context) {

            @Override
            public void onFailure(Call<SingleEvent> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_EVENT));
            }

            @Override
            protected void onSuccess(SingleEvent response) {

                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_EVENT));
            }
        });
    }


    public static void getAgenda(Context context, String eventId) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<List<Agenda>> call = service.getAgenda("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",eventId, PreferencesHelper.getCommonName(context));
        call.enqueue(new BaseCallback<List<Agenda>>(context) {

            @Override
            public void onFailure(Call<List<Agenda>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_AGENDA));
            }

            @Override
            protected void onSuccess(List<Agenda> response) {

                AgendaResponse agendaResponse = new AgendaResponse();

                agendaResponse.setAgendaList(response);

                EventBus.getDefault().post(agendaResponse);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_AGENDA));
            }
        });
    }

    public static void putCheckIn(Context context, String eventId) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<CheckedInResponse> call = service.PutCheckin("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",eventId, PreferencesHelper.getCommonName(context));
        call.enqueue(new BaseCallback<CheckedInResponse>(context) {

            @Override
            public void onFailure(Call<CheckedInResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_PUT_CHECKIN));
            }

            @Override
            protected void onSuccess(CheckedInResponse response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_PUT_CHECKIN));
            }
        });
    }

    public static void putCheckOut(Context context, String eventId) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<CheckedOutResponse> call = service.PutCheckOut("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",eventId, PreferencesHelper.getCommonName(context));
        call.enqueue(new BaseCallback<CheckedOutResponse>(context) {

            @Override
            public void onFailure(Call<CheckedOutResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_PUT_CHECKOUT));
            }

            @Override
            protected void onSuccess(CheckedOutResponse response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_PUT_CHECKOUT));
            }
        });
    }

    public static void getMessages(Context context, String eventId) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<List<EventMessage>> call = service.getMessages("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",eventId, PreferencesHelper.getCommonName(context));
        call.enqueue(new BaseCallback<List<EventMessage>>(context) {

            @Override
            public void onFailure(Call<List<EventMessage>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_MESSAGES));
            }

            @Override
            protected void onSuccess(List<EventMessage> response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_MESSAGES));
            }
        });
    }

    public static void postMessage(Context context, PostMessageRequest request) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<String> call = service.postMessage("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=","application/json", request);
        call.enqueue(new BaseCallback<String>(context) {

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_POST_MESSAGE));
            }

            @Override
            protected void onSuccess(String response) {

                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_POST_MESSAGE));
            }
        });
    }

    public static void postLike(Context context, LikeRequest request) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<Integer> call = service.postLike("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=","application/json", request);
        call.enqueue(new BaseCallback<Integer>(context) {

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_POST_LIKE));
            }

            @Override
            protected void onSuccess(Integer response) {

                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_POST_LIKE));
            }
        });
    }

    public static void postUser(Context context, String clientColor) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);

        PostUserRequest request = new PostUserRequest(PreferencesHelper.getCommonName(context),PreferencesHelper.getSalesCode(context),PreferencesHelper.getFirstName(context), PreferencesHelper.getLastName(context), PreferencesHelper.getClientEmail(context),clientColor);


        Call<ResponseBody> call = service.postUser("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=","application/json", request);
        call.enqueue(new BaseCallback<ResponseBody>(context) {

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_POST_USER));
            }

            @Override
            protected void onSuccess(ResponseBody response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_POST_USER));
            }
        });
    }

    public static void getSpeakerRatingQuestion(Context context, String speakerId) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<Question> call = service.getSpeakerRatingQuestion("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",speakerId);
        call.enqueue(new BaseCallback<Question>(context) {

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_SPEAKER_QUESTION));
            }

            @Override
            protected void onSuccess(Question response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_SPEAKER_QUESTION));
            }
        });
    }

    public static void getEventRatingQuestion(Context context, String eventId) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<Question> call = service.getEventRatingQuestion("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=",eventId);
        call.enqueue(new BaseCallback<Question>(context) {

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_EVENT_QUESTION));
            }

            @Override
            protected void onSuccess(Question response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_EVENT_QUESTION));
            }
        });
    }

    public static void postRatingEvent(Context context, RatingRequest request) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<ResponseBody> call = service.postRatingEvent("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=","application/json", request);
        call.enqueue(new BaseCallback<ResponseBody>(context) {

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_POST_RATING));
            }

            @Override
            protected void onSuccess(ResponseBody response) {

                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_POST_RATING));
            }
        });
    }

    public static void postRatingSpeaker(Context context, RatingRequest request) {
        EventsInterface service = createApiServiceEvents(context, EventsInterface.class, false);
        Call<ResponseBody> call = service.postRatingSpeaker("e2f83553-4cea-4f02-9e04-1763901ef7f0", "M1ZlbnQ2LU1AbmFnM3InUHVibCFjLUszeS1GMHIrVGgzbV80bkRyMCFEJ0IhMGI=","application/json", request);
        call.enqueue(new BaseCallback<ResponseBody>(context) {

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_POST_RATING));
            }

            @Override
            protected void onSuccess(ResponseBody response) {

                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {

                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_POST_RATING));
            }
        });
    }
}
