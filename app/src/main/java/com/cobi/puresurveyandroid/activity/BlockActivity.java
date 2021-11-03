package com.cobi.puresurveyandroid.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.View;

import com.cobi.puresurveyandroid.BuildConfig;
import com.cobi.puresurveyandroid.PureSurveyFirebaseMessagingService;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityBlockBinding;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.PingDigitalIdResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.FBRemoteConfigHelper;
import com.cobi.puresurveyandroid.util.NetworkHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ApiResponse.DigitalIdResponse;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.PingAuthenticationController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.huawei.agconnect.remoteconfig.ConfigValues;
import com.huawei.hms.aaid.HmsInstanceId;

import org.xms.g.common.ConnectionResult;
import org.xms.g.common.ExtensionApiAvailability;
import org.xms.g.tasks.OnFailureListener;
import org.xms.g.tasks.OnSuccessListener;
import org.xms.f.dynamiclinks.ExtensionDynamicLinks;
import org.xms.f.dynamiclinks.PendingDynamicLinkData;
import org.xms.f.remoteconfig.ExtensionRemoteConfig;
import org.xms.f.remoteconfig.ExtensionRemoteConfigSettings;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xms.g.utils.GlobalEnvSetting;

import static com.huawei.hms.push.HmsMessaging.DEFAULT_TOKEN_SCOPE;

/**
 * Created by admin on 2017/10/09.
 */

public class BlockActivity extends BaseActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 99;

    private ActivityBlockBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_block);
    }

    protected void getConfig() {

        try {

            if (!GlobalEnvSetting.isHms()) {

                if (checkPlayServices()) {
                    initFirebaseRemoteConfig();

                    final FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                    mFirebaseRemoteConfig.fetch(1000).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mFirebaseRemoteConfig.activate();
                                route();
                            } else {
                                getConfig();
                            }
                        }
                    });

                    // Determine how to process the Task object that stores the configuration based on the service logic.
                }
            }

            else{
                route();
            }

        } catch (Exception e) {

        }

    }

    protected void route() {
        Intent intent = getIntent();

        try {
            if (intent != null && intent.getExtras() != null && intent.hasExtra(PureSurveyFirebaseMessagingService.TYPE_KEY)) {
                handlePushNotification(intent);
            } else if (FBRemoteConfigHelper.getDeepLinkActivated() && !PreferencesHelper.isUnlocked(BlockActivity.this)) {
                mBinding.blockLayout.setVisibility(View.VISIBLE);
                handleDeepLink();
            } else if (!PreferencesHelper.hasShowOnboarding(BlockActivity.this)) {
                showOnboardingActivity();
            } else {

                if (PreferencesHelper.isUsingBiometric(this)) {
                    gotoDestinationActivity(LoginActivity.class);
                } else {

                    //check if password update flag i.e call getdigital id
                    String token = PreferencesHelper.getAccessToken(BlockActivity.this);

                    if (token != null && !token.isEmpty()) {
                        PingAuthenticationController.getDigitalID(this, token);
                    } else {
                        logout(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mFirebaseAnalytics.setCurrentScreen(this, getString(R.string.block_screen), this.getClass().getSimpleName() /* class override */);


        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, getString(R.string.block_screen), this.getClass().getSimpleName());
        }


        handleConfiguration();
    }

    public void handleConfiguration() {

        if (NetworkHelper.isNetworkConnected(this)) {
            if (checkPlayServices()) {
                getConfig();
            }
        } else {
            messagesAlertDialog("Please check your internet connection and try again", "No Internet Connection", this);
        }
    }

    public static void messagesAlertDialog(String message, String error, final Context context) {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

            builder.setMessage(error);
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ((BlockActivity) context).handleConfiguration();
                    dialog.dismiss();
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        } catch (final Exception e) {
            Log.d(AlertDialogHelper.class.getSimpleName(), "showAlertDialog(): Failed.", e);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(IMEDDetails response) {
        navigateToActivity(response);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(PingDigitalIdResponse result) {
        //check here for changepassword flag..

        if (result.getObpasswordchangeflag() != null && result.getObpasswordchangeflag().equals("1")) {

            gotoDestinationActivity(NewPasswordActivity.class);
        } else {
            String token = PreferencesHelper.getAccessToken(BlockActivity.this);
            String commonName = PreferencesHelper.getCommonName(BlockActivity.this);
            if (token != null && !token.isEmpty() && commonName != null && !commonName.isEmpty()) {
                CSIApiController.getIntermediaryDetails(BlockActivity.this, token, commonName);
            } else {
                logout(false);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_INTERMEDIARY_DETAILS)) {
            gotoDestinationActivity(LoginActivity.class);
        }

        if (errorResponse.getRequest().equals(PingAuthenticationController.CUSTOMER_GET_DIGITAL_ID)) {
            gotoDestinationActivity(LoginActivity.class);
        }
    }

    private void initFirebaseRemoteConfig() {
        ExtensionRemoteConfigSettings configSettings = new ExtensionRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG).build();
        ExtensionRemoteConfig.getInstance().setConfigSettings(configSettings);
        ExtensionRemoteConfig.getInstance().setDefaults(R.xml.remote_config_defaults);
    }

    public void handleDeepLink() {
        ExtensionDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {

                if (pendingDynamicLinkData != null) {
                    PreferencesHelper.setUnlocked(BlockActivity.this, true);
                    gotoDestinationActivity(LoginActivity.class);
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("PURESURVEY", "getDynamicLink:onFailure", e);
            }
        });
    }

    protected void handleRegister() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Register").setCancelable(false).setMessage("You need to register again").setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout(false);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        ExtensionApiAvailability apiAvailability = ExtensionApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.getSUCCESS()) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                //"user resolvable" means Google Play is available to download the last version of Play Services APK
                //This will open Google dialog fragment displaying the proper message depending on "resultCode"
                if (!isFinishing()) {
                    Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            } else {
                // Should not happen. This device does not support Play Services.
                showDeviceNotSupportedDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
            return false;
        }
        return true;
    }
}
