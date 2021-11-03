package com.cobi.puresurveyandroid.fragment;

import android.Manifest;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.EventsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentEventsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PageTypes;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.model.EventsResponse;
import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.MyEventsResponse;
import com.cobi.puresurveyandroid.model.PostUserRequest;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static android.provider.CalendarContract.Events.DTEND;

public class EventFragment extends ViewPagerFragment implements EventsAdapter.OnEventSelected {

    private FragmentEventsBinding mBinding;
    private EventsAdapter mAdapter;
    private ClientViewModel clientViewModel;
    private SharedViewModelEventDetails sharedViewModelEventDetails;
    private static final int REQUEST_CALENDAR = 0;
    private final String TAB_POSITION = "tab_position";

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false);
//
//        OMEventsApiController.postUser(getContext(), generateRandomColor());
//
//        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
//            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(getContext());
//            instanceFA.setCurrentScreen(getActivity(), "Events", this.getClass().getSimpleName());
//        }
//
//        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);
//
//        fetchEventDataFromViewModel(); //for screen rotations..to persist event data
//
//        if (savedInstanceState != null) {
//            mBinding.eventsTabs.getTabAt(savedInstanceState.getInt(TAB_POSITION)).select();
//        }
//
//        mBinding.eventsTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
////                if (tab.getPosition() == 0) {
////
////                    mBinding.noEvents.setVisibility(View.GONE);
////
////                    retrieveOMEventData();
////                } else if (tab.getPosition() == 1) {
////
////                    //clicking on my events tab calls myEvents api and populates adapter...
////
////                    mBinding.noEvents.setVisibility(View.GONE);
////
////                    retrieveMyEventsData();
////                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                //check position of tab and retrieve event data accordingly
//
//                if (mBinding.eventsTabs.getSelectedTabPosition() == 0) {
//                    retrieveOMEventData();
//                } else {
//                    retrieveMyEventsData();
//                }
//            }
//        });
//
//        if (PreferencesHelper.getSalesCode(getContext()) == null) {
//
//            mBinding.noEvents.setVisibility(View.GONE);
//
//            retrieveOMEventData();
//        }
//
        return mBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(TAB_POSITION, mBinding.eventsTabs.getSelectedTabPosition());
    }

    public void fetchEventDataFromViewModel() {
        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

//        if (clientViewModel.getEvents() != null) {
//
//            clientViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
//                @Override
//                public void onChanged(@Nullable List<Event> events) {
//
//                    if (events != null && !events.isEmpty()) {
//                        populateEventsAdapter(events);
//                        mBinding.noEvents.setVisibility(View.GONE);
//                    } else {
//                        mBinding.noEvents.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//        } else {
//            mBinding.noEvents.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onBecommingVisible(boolean visible) {
        super.onBecommingVisible(visible);
//        if (visible) {
//
//
//            AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.EventsAccessed.getNumVal(), ""));
//
//            mBinding.noEvents.setVisibility(View.GONE);
//
//            if (mBinding.eventsTabs.getSelectedTabPosition() == 0) {
//                retrieveOMEventData();
//            } else {
//                retrieveMyEventsData();
//            }
//        }
    }

    @Override
    public void onAttach(Context context) {
        registerEventBus();
        super.onAttach(context);
    }

    private void showLoaders(boolean progress) {
        mBinding.swipeContainer.setRefreshing(false);
        mBinding.progress.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    private void retrieveOMEventData() {

        mBinding.noEventsText.setText("There are no events.");
        OMEventsApiController.getEvents(getContext());
        mBinding.progress.setVisibility(View.VISIBLE);
    }

    private void retrieveMyEventsData() {

        OMEventsApiController.getMyEvents(getContext());
        mBinding.noEventsText.setText("Unfortunately, you have no events yet.");
        mBinding.progress.setVisibility(View.VISIBLE);
    }

    private void populateEventsAdapter(List<Event> result) {

        ArrayList<Event> liveEvents = new ArrayList<>();  //filters result into 2 sections if there are live and other events
        ArrayList<Event> otherEvents = new ArrayList<>();

        for (Event e : result) {

            if (e.isLive()) {
                liveEvents.add(e);
            } else {
                otherEvents.add(e);
            }
        }

        int h2Pos = 0;

        if (!liveEvents.isEmpty() && !otherEvents.isEmpty()) {

            //meaning there are 2 headings/sections
            h2Pos = liveEvents.size();
        }

        if (h2Pos != 0) {
            mAdapter = new EventsAdapter(result, getContext(), this, h2Pos + 1, liveEvents.size(), otherEvents.size());
        } else {
            mAdapter = new EventsAdapter(result, getContext(), this, h2Pos);
        }

        mBinding.eventsList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.eventsList.setHasFixedSize(true);
        mBinding.eventsList.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventsResponse(EventsResponse response) {
        showLoaders(false);

        if (response instanceof EventsResponse) {

            List<Event> result = response.getEvents();

            if (result != null && !result.isEmpty()) {
                mBinding.noEvents.setVisibility(View.GONE);

                populateEventsAdapter(result);
                clientViewModel.setEvents(result);
            } else {
                if (mAdapter != null) {
                    mAdapter.clearItems();
                }
                mBinding.noEvents.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEventsResponse(MyEventsResponse response) { //for filtering purposes of Myevents to show live events first
        showLoaders(false);

        if (response instanceof MyEventsResponse) {

            List<Event> result = response.getEvents();

            if (result != null && !result.isEmpty()) {
                mBinding.noEvents.setVisibility(View.GONE);

                Collections.sort(result, new Comparator<Event>() {
                    @Override
                    public int compare(Event event1, Event event2) {
                        //only for api 19 and up..
                        // return Boolean.compare(event2.isLive(), event1.isLive());

                        boolean b1 = event1.isLive();
                        boolean b2 = event2.isLive();

                        return (b1 != b2) ? (b1) ? -1 : 1 : 0;
                    }
                });

                populateEventsAdapter(result);
                clientViewModel.setEvents(result);
            } else {
                if (mAdapter != null) {
                    mAdapter.clearItems();
                }
                mBinding.noEvents.setVisibility(View.VISIBLE);
            }
        }
    }

    public String generateRandomColor() {

        // create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        return String.format("#%06x", nextInt);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {

        showLoaders(false);
    }

    @Override
    public void onEventSelected(Event event, Boolean openQr) {

        sharedViewModelEventDetails.setMedia(event.getMedia());

        sharedViewModelEventDetails.setEventMutableLiveData(event);

        PreferencesHelper.setEventId(getContext(), event.getId());

        PreferencesHelper.setEventReadMoreFlag(getContext(), false);

        Activity activity = getActivity();
        if (activity != null) {

            if (openQr) {

                if (event.getRegistrationStatus() == 2) {  //user is registering
                    // open browser for registration

                    String regUrl = getContext().getString(R.string.protocol_secure) + getContext().getString(R.string.base_url_events) + getContext().getString(R.string.base_path_events) + "Register/Index/" + PreferencesHelper.getCommonName(getContext());

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(regUrl));
                    startActivity(i);
                } else { //user is checking in or out

                    if (event.isLive() && !event.getHasCheckedOut()) {
                        if (event.getRegistrationStatus() == 1) {  //user has registered
                            QRCodeFragment fragment = new QRCodeFragment();
                            showNestedFragment(fragment);
                        }
                    }
                }
            } else {
                EventMainFragment fragment = new EventMainFragment();
                showNestedFragment(fragment);
            }
        }
    }
}

