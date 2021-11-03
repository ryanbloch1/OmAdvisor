package com.cobi.puresurveyandroid.fragment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.google.android.material.tabs.TabLayout;

import android.os.Handler;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentEventMainBinding;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.List;

public class EventMainFragment extends BaseFragment {

    private FragmentEventMainBinding mBinding;
    private EventDetailsFragment eventDetailsFragment;
    private SharedViewModelEventDetails sharedViewModelEventDetails;
    private GalleryFragment galleryFragment;
    private SingleEvent mainEvent;
    private final String TAB_POSITION = "tab_position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_main, container, false);

        OMEventsApiController.getEvent(getContext(), PreferencesHelper.getEventId(getContext()));

        galleryFragment = new GalleryFragment();
        eventDetailsFragment = new EventDetailsFragment();

        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);

        if (sharedViewModelEventDetails.getEvent() != null) {

            mBinding.setData(sharedViewModelEventDetails.getEvent().getValue());

            mainEvent = sharedViewModelEventDetails.getEvent().getValue();
        }

        mBinding.checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainEvent != null) {
                    if (mainEvent.getRegistrationStatus() == 1 && mainEvent.isLive() && !mainEvent.getHasCheckedOut()) {
                        showNestedFragmentCustom(new QRCodeFragment(), true);
                    } else if (mainEvent.getRegistrationStatus() == 2) {
                        String regUrl = getContext().getString(R.string.protocol_secure) + getContext().getString(R.string.base_url_events) + getContext().getString(R.string.base_path_events) + "Register/Index/" + PreferencesHelper.getCommonName(getContext());

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(regUrl));
                        startActivity(i);
                    }
                }
            }
        });

        initTabs();

        if (savedInstanceState != null) {
            mBinding.eventsTabs.getTabAt(savedInstanceState.getInt(TAB_POSITION)).select();
        }

        return mBinding.getRoot();
    }

    private void addFragmentsToContainer() {

        if (mBinding.eventsTabs.getTabAt(0).isSelected()) {
            delayInitEventDetailsOnLoad();
        } else {
            replaceFragmentNestedInContainer(galleryFragment, R.id.fragment_container_single_event);
        }
    }

    public void delayInitEventDetailsOnLoad() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isAdded()) {
                    replaceFragmentNestedInContainer(eventDetailsFragment, R.id.fragment_container_single_event);
                }
            }
        }, 500);
    }

    private void initTabs() {

        mBinding.eventsTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    replaceFragmentNestedInContainer(eventDetailsFragment, R.id.fragment_container_single_event);
                } else if (tab.getPosition() == 1) {
                    replaceFragmentNestedInContainer(galleryFragment, R.id.fragment_container_single_event);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(TAB_POSITION, mBinding.eventsTabs.getSelectedTabPosition());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(SingleEvent result) {

        if (result instanceof SingleEvent) {

            sharedViewModelEventDetails.setEvent(result);

            mBinding.setData(result);

            mainEvent = result;

            if (result.getCheckedIn() && result.isLive()) {
                mBinding.topText.setText("Now Live");
            } else if (!result.getCheckedIn() && result.isLive()) {
                mBinding.topText.setText("Live");
            } else {
                mBinding.topText.setText("");
            }

            addFragmentsToContainer();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {

        if (errorResponse.getRequest().equals(OMEventsApiController.CUSTOMER_GET_EVENT)) {
            AlertDialogHelper.showAlertDialog("Internet not available, Please check your internet connectivity and try again", getContext());
            getActivity().onBackPressed();
            unregisterEventBus();
        }
    }
}
