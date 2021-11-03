
        package com.cobi.puresurveyandroid.fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentContactDetailsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.CSIContactDetails;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ContactDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = ContactDetailFragment.class.getSimpleName();

    private static final String EXTRA_CUSTOMER_DETAIL = "CUSTOMER_DETAIL";
    public FragmentContactDetailsBinding mBinding;
    private CSIContactDetails contact;

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false);

        initialize();
        CSIApiController.getContactDetails(getContext(), PreferencesHelper.getSalesCode(getContext()), PreferencesHelper.getGcsId(getContext()));
        return mBinding.getRoot();
    }

    private void initialize() {
        mBinding.layoutContactPhone.setOnClickListener(this);
        mBinding.layoutContactMobile.setOnClickListener(this);
        mBinding.layoutContactEmail.setOnClickListener(this);
        mBinding.layoutEmail.setOnClickListener(this);
        mBinding.layoutSms.setOnClickListener(this);
        mBinding.whatsapp.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (contact == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.layoutContactPhone:
                makePhoneCall(contact.getWorkNumber(), ActionTypes.BirthdayContactPhone);
                break;
            case R.id.layoutContactMobile:
                makePhoneCall(contact.getWorkMobileNumber(), ActionTypes.BirthdayContactPhone);
                break;
            case R.id.layoutContactEmail:
                sendEmail(contact.getWorkEmailAddress(), true, ActionTypes.BirthdayContactEmail);
                break;
            case R.id.layoutEmail:
                sendEmail(contact.getWorkEmailAddress(), true, ActionTypes.BirthdayContactEmail);
                break;
            case R.id.layoutSms:
                sendSms(contact.getWorkMobileNumber(), true, ActionTypes.BirthdayContactSms);
                break;
            case R.id.whatsapp:
//
                if (contact != null && !TextUtils.isEmpty(contact.getWorkMobileNumber())) {
                    openWhatsappConversation(contact.getWorkMobileNumber(), "Amazing people deserve to have an amazing birthday.  May your birthday be out of this world!");
                }



                break;
        }
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(CSIContactDetails result) {
        mBinding.progress.setVisibility(View.GONE);
        if (result != null) {
            mBinding.setData(result);
            contact = result;

            String jsonString = "";

            try {
                jsonString = String.valueOf(new JSONObject().put("fullName", result.getFullName()).put("gCSId.", result.getGCSId()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.BirthdayCustomerAccessed.getNumVal(), jsonString));
        } else {
            showToast();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progress.setVisibility(View.GONE);
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_GET_CONTACT_DETAILS)) {
            showToast();
        }
    }

    public void showToast() {
        Toast toast = Toast.makeText(getContext(), "No details for this customer.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
