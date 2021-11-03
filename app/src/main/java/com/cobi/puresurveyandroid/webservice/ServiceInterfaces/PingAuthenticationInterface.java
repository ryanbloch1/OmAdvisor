package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.PingDigitalIdResponse;
import com.cobi.puresurveyandroid.webservice.ApiResponse.DigitalIdResponse;
import com.cobi.puresurveyandroid.webservice.ApiResponse.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PingAuthenticationInterface {


    @POST("as/token.oauth2")
    @FormUrlEncoded
    Call<TokenResponse> getToken(@Header("Content-Type") String content_type,
                                 @Field("client_id") String client_id,
                                 @Field("client_secret") String client_secret,
                                 @Query("scope") String scope,
                                 @Query("grant_type") String grant_type,
                                 @Query("username") String username,
                                 @Query("password") String password);

    @POST("as/token.oauth2")
    @FormUrlEncoded
    Call<TokenResponse> refreshToken(@Header("Content-Type") String content_type,
                                     @Field("client_id") String client_id,
                                     @Field("client_secret") String client_secret,
                                     @Field("grant_type") String grant_type,
                                     @Field("refresh_token") String refreshToken);

    @GET("idp/userinfo.openid")
    Call<PingDigitalIdResponse> getDigitalID(@Header("Authorization") String access_token,
                                             @Header("Content-Type") String content_type);
}
