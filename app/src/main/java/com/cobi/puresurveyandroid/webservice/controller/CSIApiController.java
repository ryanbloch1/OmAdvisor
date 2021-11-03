package com.cobi.puresurveyandroid.webservice.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.LoginActivity;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.BaseCallback;
import com.cobi.puresurveyandroid.model.Birthday;
import com.cobi.puresurveyandroid.model.CSIContactDetails;
import com.cobi.puresurveyandroid.model.Campaign;
import com.cobi.puresurveyandroid.model.CampaignResponse;
import com.cobi.puresurveyandroid.model.ChannelRole;
import com.cobi.puresurveyandroid.model.CommissionResponseAPI;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.IntermediaryAgreement;
import com.cobi.puresurveyandroid.model.IntermediaryDetailsRequest;
import com.cobi.puresurveyandroid.model.MyCustomerCounts;
import com.cobi.puresurveyandroid.model.Lead;
import com.cobi.puresurveyandroid.model.LeadResponse;
import com.cobi.puresurveyandroid.model.LogoutResponse;
import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.model.MissedPremiumResponse;
import com.cobi.puresurveyandroid.model.MissedPremiumsRequest;
import com.cobi.puresurveyandroid.model.MyCustomerCounts;
import com.cobi.puresurveyandroid.model.PartyPerson;
import com.cobi.puresurveyandroid.model.PartyRegistration;
import com.cobi.puresurveyandroid.model.PartySearchCriteria;
import com.cobi.puresurveyandroid.model.PartySearchRequest;
import com.cobi.puresurveyandroid.model.Pipeline;
import com.cobi.puresurveyandroid.model.PipelineProduct;
import com.cobi.puresurveyandroid.model.PipelineProductRequest;
import com.cobi.puresurveyandroid.model.PipelineProductResponse;
import com.cobi.puresurveyandroid.model.PipelineResponse;
import com.cobi.puresurveyandroid.model.RefreshTokenResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEvent;
import com.cobi.puresurveyandroid.util.FBRemoteConfigHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ApiResponse.DigitalIdResponse;
import com.cobi.puresurveyandroid.webservice.ApiResponse.TokenResponse;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.AccessTokenServiceInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.CSIInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.DigitalIdInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.IntermediaryDetailsInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.MissedPremiumsInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.PipelinesInterface;
import com.cobi.puresurveyandroid.webservice.ServiceInterfaces.SalesEarningsInterface;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

public class CSIApiController extends BaseApiController {

    public static final String CUSTOMER_GET_CAMPAIGNS = "customer_campaigns";
    public static final String CUSTOMER_GET_BIRTHDAYS = "customer_birthdays";
    public static final String CUSTOMER_GET_MISSED_PREMIUM = "customer_missed_premium";
    public static final String CUSTOMER_GET_CONTACT_DETAILS = "customer_contact_details";
    public static final String CUSTOMER_GET_CONTACT_DETAILS_LEADS = "customer_contact_details_leads";
    public static final String CUSTOMER_GET_LEADS = "customer_leads";
    public static final String CUSTOMER_SALES_EARNINGS = "customer_sales_earnings";
    public static final String CUSTOMER_INTERMEDIARY_DETAILS = "customer_intermediary_details";
    public static final String CUSTOMER_PARTY_DETAILS = "customer_party_details";
    public static final String CUSTOMER_GET_DIGITAL_ID = "customer_digital_id";
    public static final String CUSTOMER_GET_TOKEN = "customer_token";
    public static final String CUSTOMER_REFRESH_TOKEN = "customer_refresh_token";
    public static final String CUSTOMER_GET_LANDING_PAGE_COUNTS = "customer_landing_page";

    public static void getToken(final Context context, String username, String password) {
        AccessTokenServiceInterface service = createApiService(context, AccessTokenServiceInterface.class, false);
        Call<TokenResponse> call = service.getToken("application/x-www-form-urlencoded", context.getString(R.string.client_id), context.getString(R.string.client_secret), "IMEDUserProfile.me IMEDUserProfile.retrieve", "password", username, password);
        call.enqueue(new BaseCallback<TokenResponse>(context) {

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_TOKEN));
            }

            @Override
            protected void onSuccess(TokenResponse response) {
                PreferencesHelper.setAccessToken(context, response.getAccessToken());
                PreferencesHelper.setRefreshToken(context, response.getRefreshToken());
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                PreferencesHelper.setAccessToken(context, null);
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_TOKEN));
            }
        });
    }

    public static void refreshToken(final Context context, String refreshToken) {
        AccessTokenServiceInterface service = createApiService(context, AccessTokenServiceInterface.class, false);
        Call<TokenResponse> call = service.refreshToken("application/x-www-form-urlencoded", context.getString(R.string.client_id), context.getString(R.string.client_secret), "IMEDUserProfile.me IMEDUserProfile.retrieve", "refresh_token", refreshToken);
        call.enqueue(new BaseCallback<TokenResponse>(context) {

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_REFRESH_TOKEN));
            }

            @Override
            protected void onSuccess(TokenResponse response) {
                PreferencesHelper.setAccessToken(context, response.getAccessToken());
                PreferencesHelper.setRefreshToken(context, response.getRefreshToken());
                EventBus.getDefault().post(new RefreshTokenResponse(response));
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                PreferencesHelper.setAccessToken(context, null);
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_REFRESH_TOKEN));
            }
        });
    }

    public static void requestInValidateAccessToken(final Context context, String token) {
        AccessTokenServiceInterface service = createApiService(context, AccessTokenServiceInterface.class, false);
        Call<LogoutResponse> call = service.logout("application/x-www-form-urlencoded", context.getString(R.string.client_id), context.getString(R.string.client_secret), "oracle-idm:/oauth/grant-type/resource-access-token/jwt", "delete", token);
        call.enqueue(new BaseCallback<LogoutResponse>(context) {

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                requestInValidateRefreshToken(context, PreferencesHelper.getRefreshoken(context));
            }

            @Override
            protected void onSuccess(LogoutResponse response) {
                requestInValidateRefreshToken(context, PreferencesHelper.getRefreshoken(context));
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                requestInValidateRefreshToken(context, PreferencesHelper.getRefreshoken(context));
            }
        });
    }

    public static void requestInValidateRefreshToken(final Context context, String token) {
        AccessTokenServiceInterface service = createApiService(context, AccessTokenServiceInterface.class, false);
        Call<LogoutResponse> call = service.logout("application/x-www-form-urlencoded", context.getString(R.string.client_id), context.getString(R.string.client_secret), "oracle-idm:/oauth/grant-type/resource-access-token/jwt", "delete", token);
        call.enqueue(new BaseCallback<LogoutResponse>(context) {

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {

                logout(context);
            }

            @Override
            protected void onSuccess(LogoutResponse response) {

                logout(context);
            }

            @Override
            protected void onError(ResponseBody errorBody) {

                logout(context);
            }
        });
    }

    public static void logout(Context context) {
        PreferencesHelper.setStaffNumber(context, null);
        PreferencesHelper.setSince(context, null);
        PreferencesHelper.setUUID(context, null);
        PreferencesHelper.setAccessToken(context, null);
        PreferencesHelper.setRefreshToken(context, null);
        PreferencesHelper.setCommonName(context, null);
        PreferencesHelper.setSegment(context, null);
        PreferencesHelper.setBusinessUnit(context, null);
        PreferencesHelper.setEmployeeType(context, null);
        PreferencesHelper.setClientName(context, null);
        PreferencesHelper.setCampaignId(context, -1);
        PreferencesHelper.setSalesCode(context, null);
        PreferencesHelper.setGcsId(context, null);
        PreferencesHelper.setCommissionAmount(context, 0);
        PreferencesHelper.setIsUsingBiometric(context, false);

        PreferencesHelper.setUpdatedDate(context, null);
        PreferencesHelper.setRefreshToken(context, null);
        PreferencesHelper.setHasMultipleSalesCodes(context, false);
        PreferencesHelper.setFullName(context, null);
        PreferencesHelper.setFirstName(context, null);
        PreferencesHelper.setLastName(context, null);
        PreferencesHelper.setClientName(context, null);
        PreferencesHelper.setStaffId(context, null);
        PreferencesHelper.setClientEmail(context, null);
        PreferencesHelper.setIMEI(context, null);
        PreferencesHelper.setIMSI(context, null);
        PreferencesHelper.setCustomerFullName(context, null);
        PreferencesHelper.setMissedPContractNo(context, null);
        PreferencesHelper.setCloudMessagingRegistrationId(context, null);

        AnalyticsController.PostAction(context, new ActionRequest(PreferencesHelper.getDeviceId(context), ActionTypes.LogOut.getNumVal(), ""));

        ModelAdapter<UserCustomerEvent> adapter = FlowManager.getModelAdapter(UserCustomerEvent.class);
        adapter.deleteAll(SQLite.select().from(UserCustomerEvent.class).queryList());

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void getContactDetails(Context context, String salesCode, String gcsId) {
        CSIInterface service = createApiService(context, CSIInterface.class, false);
        Call<CSIContactDetails> call = service.getContactDetails(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode, gcsId);
        call.enqueue(new BaseCallback<CSIContactDetails>(context) {
            @Override
            protected void onSuccess(CSIContactDetails response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_CONTACT_DETAILS));
            }

            @Override
            public void onFailure(Call<CSIContactDetails> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_CONTACT_DETAILS));
            }
        });
    }


    public static void getContactDetailsLeads(Context context, long leadId) {
        CSIInterface service = createApiService(context, CSIInterface.class, false);
        Call<CSIContactDetails> call = service.getContactDetailsLeads(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), leadId);
        call.enqueue(new BaseCallback<CSIContactDetails>(context) {
            @Override
            protected void onSuccess(CSIContactDetails response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_CONTACT_DETAILS_LEADS));
            }

            @Override
            public void onFailure(Call<CSIContactDetails> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_CONTACT_DETAILS_LEADS));
            }
        });
    }

    public static void getLeads(Context context, String salesCode, String campaignId) {
        CSIInterface service = createApiService(context, CSIInterface.class, false);

        Call<List<Lead>> call = service.getLeads(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode, campaignId);

        call.enqueue(new BaseCallback<List<Lead>>(context) {

            @Override
            protected void onSuccess(List<Lead> response) {
                EventBus.getDefault().post(new LeadResponse(response));
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_LEADS));
            }

            @Override
            public void onFailure(Call<List<Lead>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_LEADS));
            }
        });
    }

    public static void getSalesEarnings(Context context, String salesCode) {

        SalesEarningsInterface service = createApiService(context, SalesEarningsInterface.class, false);

        Call<CommissionResponseAPI> call = service.getSalesEarnings(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode);

        call.enqueue(new BaseCallback<CommissionResponseAPI>(context) {
            @Override
            public void onSuccess(CommissionResponseAPI response) {
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                try {
                    String string = errorBody.string();
                    CommissionResponseAPI parsedResponse = new Gson().fromJson(string, CommissionResponseAPI.class);
                    EventBus.getDefault().post(parsedResponse);
                } catch (Exception e) {
                    postErrorResponse(new ErrorResponse(CUSTOMER_SALES_EARNINGS));
                }
            }

            @Override
            public void onFailure(Call<CommissionResponseAPI> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_SALES_EARNINGS));
            }
        });
    }

    public static void getDigitalID(final Context context, String accessToken) {

        DigitalIdInterface service = createApiService(context, DigitalIdInterface.class, false);
        Call<DigitalIdResponse> call = service.getDigitalID(accessToken, "application/x-www-form-urlencoded", context.getString(R.string.client_id), context.getString(R.string.client_secret));
        call.enqueue(new BaseCallback<DigitalIdResponse>(context) {

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_DIGITAL_ID));
            }

            @Override
            public void onFailure(Call<DigitalIdResponse> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_DIGITAL_ID));
            }

            @Override
            protected void onSuccess(DigitalIdResponse response) {
                PreferencesHelper.setCommonName(context, response.getCommonName());

                EventBus.getDefault().post(response);
            }
        });
    }

    public static void getIntermediaryDetails(final Context context, String accessToken, String commonName) {
        IntermediaryDetailsInterface service = createApiService(context, IntermediaryDetailsInterface.class, false);
        final IntermediaryDetailsRequest request = new IntermediaryDetailsRequest(PreferencesHelper.getCommonName(context));

        Call<IMEDDetails> call = service.getImedDetails(accessToken, "application/x-www-form-urlencoded", context.getString(R.string.client_id_resetcreds), context.getString(R.string.client_secret_resetcreds), "IAM", commonName, request);

        call.enqueue(new BaseCallback<IMEDDetails>(context) {
            @Override
            public void onFailure(Call<IMEDDetails> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_INTERMEDIARY_DETAILS));
                PreferencesHelper.setSegment(context, "");
                PreferencesHelper.setHasMultipleSalesCodes(context, false);
            }

            @Override
            protected void onSuccess(IMEDDetails response) {

                if (response != null) {
                    if (response.getIntermediaryDetails() != null && !response.getIntermediaryDetails().isEmpty()) {
                        if (response.getIntermediaryDetails().get(0).getPerson() != null && !response.getIntermediaryDetails().get(0).getPerson().isEmpty()) {
                            ChannelRole mChannel = null;
                            try {
                                mChannel = response.getIntermediaryDetails().get(0).getPerson().get(0).getChannelRole().get(0);
                            } catch (Exception e) {
                            }


                            try{
                              List<PartyRegistration>  pList = response.getIntermediaryDetails().get(0).getPerson().get(0).getPartyRegistration();

                              //find national identifier

                              String country = "";

                              for(PartyRegistration p : pList){

                                  if(p.getTypeName().equals("NATIONAL IDENTITY")){

                                      country = p.getIsRegisteredByCountry();

                                      break;



                                  }



                              }


                              PreferencesHelper.setCountryReg(context, country);



                            }
                            catch (Exception e){

                            }




                            try {
                                PreferencesHelper.setSalesCode(context, mChannel.getExternalReference());
                            } catch (Exception e) {
                            }

                            try {
                                PreferencesHelper.setFullName(context, response.getIntermediaryDetails().get(0).getPerson().get(0).getPersonName().getFullName());

                            } catch (Exception e) {

                            }

                            try {
                                PreferencesHelper.setSegment(context, mChannel.getMarketSegment().get(0).getExternalReference());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setBusinessUnit(context, mChannel.getIntermediaryCommissionSharingCategory().get(0).getName());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setEmployeeType(context, mChannel.getTypeName());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setChannel(context, mChannel.getOrganisation().getTypeName());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setArea(context, mChannel.getOrganisation().getUnstructuredName().getFullname());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setRegion(context, mChannel.getOrganisation().getOrganisation().get(0).getUnstructuredName().getFullname());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setRole(context, mChannel.getTypeName());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setTeam(context, mChannel.getOrganisation().getOrganisation().get(0).getOrganisation().get(0).getUnstructuredName().getFullname());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                PreferencesHelper.setFirstName(context, response.getIntermediaryDetails().get(0).getPerson().get(0).getPersonName().getFirstName());

                            } catch (Exception e) {

                            }
                            try {
                                PreferencesHelper.setClientName(context, response.getIntermediaryDetails().get(0).getPerson().get(0).getPersonName().getFullName());

                            } catch (Exception e) {

                            }


                            try {
                                PreferencesHelper.setClientEmail(context, response.getIntermediaryDetails().get(0).getPerson().get(0).getEmailAddress().get(0).getEmailAddress());

                            } catch (Exception e) {

                            }

                            try {
                                PreferencesHelper.setLastName(context, response.getIntermediaryDetails().get(0).getPerson().get(0).getPersonName().getLastName());

                            } catch (Exception e) {

                            }

                            try {
                                PreferencesHelper.setMobileNo(context, response.getIntermediaryDetails().get(0).getPerson().get(0).getMobileNumber().get(0).getTelephoneNumber());

                            } catch (Exception e) {

                            }


                        }
                        List<IntermediaryAgreement> result = response.getIntermediaryDetails().get(0).getIntermediaryAgreement();
                        PreferencesHelper.setHasMultipleSalesCodes(context, result != null && result.size() > 1);
                    }
                }

                EventBus.getDefault().postSticky(response);

            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_INTERMEDIARY_DETAILS));
            }
        });
    }


    public static void getPartyDetails(final Context context, String accessToken) {
        String versionName = null;

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        IntermediaryDetailsInterface service = createApiService(context, IntermediaryDetailsInterface.class, false);
        final PartySearchRequest request = new PartySearchRequest(new PartySearchCriteria("DIGITAL IDENTITY", PreferencesHelper.getCommonName(context)));

        Call<PartyPerson> call = service.getPartyDetails(accessToken, "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), "Party", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), PreferencesHelper.getCommonName(context), "", "Mobile App", "AdviserWB", versionName, "2020-10-28T09:05:50.633183600", request);

        call.enqueue(new BaseCallback<PartyPerson>(context) {
            @Override
            public void onFailure(Call<PartyPerson> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_PARTY_DETAILS));
                PreferencesHelper.setSegment(context, "");
                PreferencesHelper.setHasMultipleSalesCodes(context, false);
            }

            @Override
            protected void onSuccess(PartyPerson response) {

                if (response != null) {
                    if (!response.getPerson().isEmpty()) {

                        EventBus.getDefault().post(response);

                        PreferencesHelper.setSegment(context, "MFC-ZA");

                    }

                }


            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_PARTY_DETAILS));
            }
        });
    }




    public static void getCampaigns(Context context, String salesCode) {
        CSIInterface service = createApiService(context, CSIInterface.class, false);

        Call<List<Campaign>> call = service.getCampaigns(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode);

        Log.e("test", "getcampaigns request");
        call.enqueue(new BaseCallback<List<Campaign>>(context) {
            @Override
            protected void onSuccess(List<Campaign> response) {
                EventBus.getDefault().post(new CampaignResponse(response));
                Log.e("test", "getcampaigns success");
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_CAMPAIGNS));
                Log.e("test", "getcampaigns error");
            }

            @Override
            public void onFailure(Call<List<Campaign>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_CAMPAIGNS));
                Log.e("test", "getcampaigns failure");
            }
        });
    }

    public static void getBirthdays(Context context, String salesCode) {
        CSIInterface service = createApiService(context, CSIInterface.class, false);

        Call<List<Birthday>> call = service.getClientBirthdays(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode);

        Log.e("test", "birthdays request");
        call.enqueue(new BaseCallback<List<Birthday>>(context) {

            @Override
            protected void onSuccess(List<Birthday> response) {

                Log.e("test", "birthday success");
                EventBus.getDefault().post(response);
            }

            @Override
            protected void onError(ResponseBody errorBody) {

                Log.e("test", "birthday error");
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_BIRTHDAYS));
            }

            @Override
            public void onFailure(Call<List<Birthday>> call, Throwable t) {
                Log.e("test", "birthday failure");
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_BIRTHDAYS));
            }
        });
    }

    public static void getMissedPremiumsDetails(Context context) {
        MissedPremiumsInterface service = createApiService(context, MissedPremiumsInterface.class, false);

        MissedPremiumsRequest request = new MissedPremiumsRequest(PreferencesHelper.getSalesCode(context));
        Call<List<MissedPremium>> call = service.getMissedPremiumsDetails(PreferencesHelper.getAccessToken(context), "application/json", context.getString(R.string.client_id), context.getString(R.string.client_secret), request);
        Log.e("test", "getmissedpremiums requested");
        call.enqueue(new BaseCallback<List<MissedPremium>>(context) {

            @Override
            protected void onSuccess(List<MissedPremium> response) {
                EventBus.getDefault().post(new MissedPremiumResponse(response));
                Log.e("test", "getmissedpremiums success");
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_MISSED_PREMIUM));
                Log.e("test", "getmissedpremiums error");
            }

            @Override
            public void onFailure(Call<List<MissedPremium>> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_MISSED_PREMIUM));
                Log.e("test", "getmissedpremiums failure");
            }
        });
    }

    public static void getCountsForLandingPage(Context context, String salesCode) {
        CSIInterface service = createApiService(context, CSIInterface.class, false);

        Call<MyCustomerCounts> call = service.getCountsForLandingPage(PreferencesHelper.getAccessToken(context), "application/x-www-form-urlencoded", context.getString(R.string.client_id), context.getString(R.string.client_secret), salesCode);
        Log.e("test", "getCountsForLandingPage requested");
        call.enqueue(new BaseCallback<MyCustomerCounts>(context) {

            @Override
            protected void onSuccess(MyCustomerCounts response) {
                EventBus.getDefault().post(response);
                Log.e("test", "getCountsForLandingPage success");
            }

            @Override
            protected void onError(ResponseBody errorBody) {
                postErrorResponse(errorBody, new ErrorResponse(CUSTOMER_GET_LANDING_PAGE_COUNTS));
                Log.e("test", "getCountsForLandingPage error");
            }

            @Override
            public void onFailure(Call<MyCustomerCounts> call, Throwable t) {
                postErrorResponse(new ErrorResponse(CUSTOMER_GET_LANDING_PAGE_COUNTS));
                Log.e("test", "getCountsForLandingPage failure");
            }
        });
    }
}
