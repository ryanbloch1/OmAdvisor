package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import android.content.Context;
import android.content.res.Resources;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.Agenda;
import com.cobi.puresurveyandroid.model.CheckedInResponse;
import com.cobi.puresurveyandroid.model.CheckedOutResponse;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.model.EventMessage;
import com.cobi.puresurveyandroid.model.EventsResponse;
import com.cobi.puresurveyandroid.model.LikeRequest;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.model.MyEventsResponse;
import com.cobi.puresurveyandroid.model.PostMessageRequest;
import com.cobi.puresurveyandroid.model.PostUserRequest;
import com.cobi.puresurveyandroid.model.Question;
import com.cobi.puresurveyandroid.model.RatingRequest;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.util.Constants;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface EventsInterface {

    @GET("api/AppEvent/GetEvents/{digitalId}/{segmentChannel}")
    Call<EventsResponse> getEvents(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "digitalId", encoded = true) String digitalId, @Path(value = "segmentChannel", encoded = true) String segmentChannel);

    @GET("api/AppEvent/GetMyEvents/{digitalId}/{segmentChannel}")
    Call<MyEventsResponse> getMyEvents(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "digitalId", encoded = true) String digitalId, @Path(value = "segmentChannel", encoded = true) String segmentChannel);

    @GET("api/AppEvent/getEvent/{eventId}/{digitalId}")
    Call<SingleEvent> getEvent(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "eventId", encoded = true) String eventId, @Path(value = "digitalId", encoded = true) String digitalId);

    @GET("api/AppEvent/GetMedia/{eventId}")
    Call<List<Medium>> getMedia(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "eventId", encoded = true) String eventId);

    @GET("api/AppEvent/GetAgenda/{eventId}/{digitalId}")
    Call<List<Agenda>> getAgenda(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "eventId", encoded = true) String eventId, @Path(value = "digitalId", encoded = true) String digitalId);

    @PUT("api/AppEvent/PutCheckIn/{eventId}/{digitalId}")
    Call<CheckedInResponse> PutCheckin(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "eventId", encoded = true) String eventId, @Path(value = "digitalId", encoded = true) String digitalId);

    @PUT("api/AppEvent/PutCheckOut/{eventId}/{digitalId}")
    Call<CheckedOutResponse> PutCheckOut(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "eventId", encoded = true) String eventId, @Path(value = "digitalId", encoded = true) String digitalId);

    @GET("api/AppEvent/GetMessages/{eventId}/{digitalId}")
    Call<List<EventMessage>> getMessages(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "eventId", encoded = true) String eventId, @Path(value = "digitalId", encoded = true) String digitalId);

    @POST("api/AppEvent/PostMessage")
    Call<String> postMessage(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Header("Content-Type") String contentType, @Body PostMessageRequest request);

    @POST("api/AppEvent/PostLike")
    Call<Integer> postLike(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Header("Content-Type") String contentType, @Body LikeRequest request);

    @POST("api/AppEvent/PostUser")
    Call<ResponseBody> postUser(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Header("Content-Type") String contentType, @Body PostUserRequest request);

    @GET("api/AppEvent/GetSpeakerRatingQuestion/{speakerId}")
    Call<Question> getSpeakerRatingQuestion(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "speakerId", encoded = true) String speakerId);

    @GET("api/AppEvent/GetEventRatingQuestion/{eventId}")
    Call<Question> getEventRatingQuestion(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Path(value = "eventId", encoded = true) String eventId);

    @POST("api/AppEvent/PostRateEvent")
    Call<ResponseBody> postRatingEvent(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Header("Content-Type") String contentType, @Body RatingRequest request);

    @POST("api/AppEvent/PostRateSpeaker")
    Call<ResponseBody> postRatingSpeaker(@Header("AppId") String appId, @Header("ApiKey") String apiKey, @Header("Content-Type") String contentType, @Body RatingRequest request);
}
