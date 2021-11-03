package com.cobi.puresurveyandroid.activity;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ShareCompat;
import androidx.appcompat.app.AlertDialog;

import android.view.View;

import com.cobi.puresurveyandroid.BuildConfig;
import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityValidationBinding;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.RegisterResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.SnapappAuthenticationApiController;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



/**
 * Created by admin on 2017/10/08.
 */

public class ValidationActivity extends BaseActivity {

    private static final String TAG = ValidationActivity.class.getSimpleName();

    public static final String FIRST_NAME_KEY = "first_name_key";
    public static final String SURNAME_KEY = "surname_key";
    public static final String BRANCH_NAME_KEY = "branch_name_key";

    private ActivityValidationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_validation);


        mBinding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.confirmButton.setEnabled(false);
                register();
            }
        });

        mBinding.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSupportEmail();
            }
        });

        clearDetails();
        setDetails();

        setSupportActionBar(mBinding.includeToolbar.toolbar);
        showBackButton(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setDetails() {

        if (getIntent().hasExtra(FIRST_NAME_KEY)) {
            String firstName = getIntent().getStringExtra(FIRST_NAME_KEY);
            mBinding.firstNameTextView.setText(firstName);
        }

        if (getIntent().hasExtra(SURNAME_KEY)) {
            String surname = getIntent().getStringExtra(SURNAME_KEY);
            mBinding.surnameTextView.setText(surname);
        }

        if (getIntent().hasExtra(BRANCH_NAME_KEY)) {
            String branch = getIntent().getStringExtra(BRANCH_NAME_KEY);
            mBinding.branchTextView.setText(branch);
        }
    }

    private void clearDetails() {
        mBinding.firstNameTextView.setText("");
        mBinding.surnameTextView.setText("");
        mBinding.branchTextView.setText("");
    }

    private void register() {
        if (BuildConfig.DEBUG || (PureSurveyApplication.IMEI != null && PureSurveyApplication.IMSI != null)) {
            mBinding.progressBar.setVisibility(View.VISIBLE);
            mBinding.confirmButton.setVisibility(View.INVISIBLE);
            SnapappAuthenticationApiController.register(this, "33333");
        }
    }

    private boolean checkVersions(String mandatoryVersion, String upgradeVersion) {
        try {

            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

            if (version.contains(".bld")) {
                String[] removeBld = version.split(".bld");
                version = removeBld[0];
            }

            String[] versionPartitioned = version.split("\\.");
            String[] mandatoryVersionPartitioned = mandatoryVersion.split("\\.");
            String[] upgradeVersionPartitioned = upgradeVersion.split("\\.");

            for (int i = 0; i < mandatoryVersionPartitioned.length; i++) {
                int currentVersionPart = Integer.parseInt(versionPartitioned[i].substring(0, 1));
                int mandatoryVersionPart = Integer.parseInt(mandatoryVersionPartitioned[i]);

                if (mandatoryVersionPart > currentVersionPart) {
                    commenceMandatoryUpgrade(mandatoryVersion);
                    return true;
                }
            }

            for (int i = 0; i < upgradeVersionPartitioned.length; i++) {
                int currentVersionPart = Integer.parseInt(versionPartitioned[i].substring(0, 1));
                int upgradeVersionPart = Integer.parseInt(upgradeVersionPartitioned[i]);

                if (upgradeVersionPart > currentVersionPart) {
                    commenceOptionalUpgrade(upgradeVersion);
                    return true;
                }
            }
        } catch (Exception e) {
        }

        return false;
    }

    private void commenceOptionalUpgrade(String upgradeVersion) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false).setTitle("Register").setCancelable(false).setMessage("An upgrade is available, would you like to upgrade?").setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                PreferencesHelper.setStaffNumber(ValidationActivity.this, null);
                PreferencesHelper.setUUID(ValidationActivity.this, null);

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        }).setNeutralButton("No Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gotoMatrix();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void commenceMandatoryUpgrade(String mandatoryVersion) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(false).setTitle("Register").setMessage("You need to upgrade the app to version " + mandatoryVersion + " before continuing").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                PreferencesHelper.setStaffNumber(ValidationActivity.this, null);
                PreferencesHelper.setUUID(ValidationActivity.this, null);

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void gotoMatrix() {
        PureSurveyApplication.hasRegistered = true;
        mBinding.progressBar.setVisibility(View.GONE);
        mBinding.confirmButton.setVisibility(View.VISIBLE);
        mBinding.confirmButton.setEnabled(true);

        Intent inboxIntent = new Intent(this, MatrixSelectionActivity.class);
        inboxIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(inboxIntent);
        overridePendingTransition(0, 0);
    }

    public void sendSupportEmail() {
        Intent emailIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/email")
                .addEmailTo(getString(R.string.support_email_address))
                .setSubject(getString(R.string.support_email_subject))
                .setText(getString(R.string.support_email_message))
                .getIntent();
        startActivity(emailIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, "Validation Screen", null /* class override */);
        }

        if (!mBinding.confirmButton.isEnabled()) {
            gotoMatrix();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(RegisterResponse response) {

        if (response != null && response.getData() != null) {
            try {
                PreferencesHelper.setUUID(this, response.getData().getGuid());
                if (!checkVersions(response.getData().getMandatoryUpgradeVersion(), response.getData().getUpgradeVersion())) {
                    gotoMatrix();
                }
            } catch (Exception e) {
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progressBar.setVisibility(View.GONE);
        mBinding.confirmButton.setVisibility(View.VISIBLE);
        mBinding.confirmButton.setEnabled(true);
    }
}
