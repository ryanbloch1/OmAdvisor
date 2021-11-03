package com.cobi.puresurveyandroid.fragment;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentQrcodeBinding;
import com.cobi.puresurveyandroid.model.CheckedInResponse;
import com.cobi.puresurveyandroid.model.CheckedOutResponse;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.IntentIntegrator;
import com.cobi.puresurveyandroid.util.IntentResult;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class QRCodeFragment extends BaseFragment {

    private FragmentQrcodeBinding mBinding;
    private Boolean checkIn;
    private SharedViewModelEventDetails sharedViewModelEventDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_qrcode, container, false);

        mBinding.qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanNow();
            }
        });

        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);

        if (sharedViewModelEventDetails.getEventMutableLiveData() != null) {
            if (sharedViewModelEventDetails.getEventMutableLiveData().getValue() != null) {

                checkIn = sharedViewModelEventDetails.getEventMutableLiveData().getValue().getCheckedIn();
            }
        }

        return mBinding.getRoot();
    }

    public void scanNow() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setPrompt("Scan qrcode");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(true);
        integrator.forSupportFragment(QRCodeFragment.this).initiateScan();
        integrator.setBeepEnabled(false);
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String codeContent = scanningResult.getContents();

            if (checkIn != null) {
                if (checkIn) {

                    OMEventsApiController.putCheckOut(getContext(), codeContent);
                } else {
                    OMEventsApiController.putCheckIn(getContext(), codeContent);
                }
            }
        } else {
            Toast toast = Toast.makeText(getContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCheckInResponse(CheckedInResponse checkIn) {
        //if true navigate to CIN.. else... navigate to eventmainfrag..

        if (checkIn instanceof CheckedInResponse) {
            if (checkIn.getIsCheckedIn()) {

                showNestedFragment(new CheckinNotificationFragment());
            } else {
                showNestedFragment(new EventMainFragment());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCheckOutResponse(CheckedOutResponse checkOut) {

        if (checkOut instanceof CheckedOutResponse) {

            if (checkOut.getIsCheckedOut()) {
                showNestedFragment(new CheckoutFragment());
            } else {
                showNestedFragment(new EventFragment());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        //error on checkout.. navigate back to events list page..
        //error on checkin.. stay on event details..

        if (errorResponse.getRequest().equals(OMEventsApiController.CUSTOMER_PUT_CHECKIN)) {
            showNestedFragment(new EventMainFragment());
        }

        if (errorResponse.getRequest().equals(OMEventsApiController.CUSTOMER_PUT_CHECKOUT)) {
            showNestedFragment(new EventFragment());
        }
    }
}
