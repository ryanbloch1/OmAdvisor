package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.LogoutResponse;
import com.cobi.puresurveyandroid.webservice.ApiResponse.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AccessTokenServiceInterface {


    @POST("ms_oauth/oauth2/endpoints/imedoauthservice/tokens")
    @FormUrlEncoded
    Call<TokenResponse> getToken(@Header("Content-Type") String content_type,
                                 @Header("x-ibm-client-id") String client_id,
                                 @Header("x-ibm-client-secret") String client_secret,
                                 @Field("scope") String scope,
                                 @Field("grant_type") String grant_type,
                                 @Field("username") String username,
                                 @Field("password") String password);


    @POST("ms_oauth/oauth2/endpoints/imedoauthservice/tokens")
    @FormUrlEncoded
    Call<TokenResponse> refreshToken(@Header("Content-Type") String content_type,
                                 @Header("x-ibm-client-id") String client_id,
                                 @Header("x-ibm-client-secret") String client_secret,
                                 @Field("scope") String scope,
                                 @Field("grant_type") String grant_type,
                                 @Field("refresh_token") String refreshToken);



    @POST("ms_oauth/oauth2/endpoints/imedoauthservice/tokens")
    @FormUrlEncoded
    Call<LogoutResponse> logout(@Header("Content-Type") String content_type,
                                @Header("x-ibm-client-id") String client_id,
                                @Header("x-ibm-client-secret") String client_secret,
                                @Field("grant_type") String grant_type,
                                @Field("oracle_token_action") String oracle_token_action,
                                @Field("assertion") String access_token);

}
