package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;

import com.cobi.puresurveyandroid.model.Affordability;
import com.cobi.puresurveyandroid.model.Campaigns;
import com.cobi.puresurveyandroid.model.Corporate;
import com.cobi.puresurveyandroid.model.Customer;
import com.cobi.puresurveyandroid.model.Demographic;
import com.cobi.puresurveyandroid.model.Engagement;
import com.cobi.puresurveyandroid.model.Holdings;
import com.cobi.puresurveyandroid.model.LikeYou;
import com.cobi.puresurveyandroid.model.Maturity;
import com.cobi.puresurveyandroid.model.Needs;
import com.cobi.puresurveyandroid.model.ReIntermediary;
import com.cobi.puresurveyandroid.model.ReProduct;
import com.cobi.puresurveyandroid.model.RecommendedProduct;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReintermediationInterface {


    @GET("ReIntermediation/GetReIntermediaries/{staffCode}")
    Call<List<ReIntermediary>> GetReIntermediaries(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("Authorization") String bearer, @Path(value = "staffCode", encoded = true) String staffId);

    @GET("ReIntermediation/GetDemographics/{customerId}")
    Call<Demographic> GetDemographics(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetMaturities/{customerId}")
    Call<List<Maturity>> GetMaturities(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetAffordability/{customerId}")
    Call<Affordability> GetAffordability(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetCorporate/{customerId}")
    Call<Corporate> GetCorporate(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetNeeds/{customerId}")
    Call<Needs> GetNeeds(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetHoldings/{customerId}")
    Call<Holdings> GetHoldings(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetEngagement/{customerId}")
    Call<Engagement> GetEngagement(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetProducts/{customerId}")
    Call<List<ReProduct>> GetProducts(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetRecommended/{customerId}")
    Call<RecommendedProduct> GetRecommended(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetCampaigns/{customerId}")
    Call<Campaigns> GetCampaigns(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetLikeYou/{customerId}")
    Call<LikeYou> GetLikeYou(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @GET("ReIntermediation/GetCustomer/{customerId}")
    Call<Customer> GetCustomer(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @POST("ReIntermediation/PostAccept/{customerId}")
    Call<ResponseBody> PostAccept(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

    @POST("ReIntermediation/PostDecline/{customerId}")
    Call<ResponseBody> PostDecline(@Header("ApiKey") String apiKey, @Header("AppId") String appId, @Header("UserKey") String staffId, @Header("Authorization") String bearer, @Path(value = "customerId", encoded = true) String customerId);

}
