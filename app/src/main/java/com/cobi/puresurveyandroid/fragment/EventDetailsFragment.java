package com.cobi.puresurveyandroid.fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cobi.puresurveyandroid.adapter.ImageSliderAdapter;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.EventMessage;
import com.cobi.puresurveyandroid.model.Message;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.DateHelper;
import org.xms.g.maps.model.Marker;
import com.google.android.material.tabs.TabLayout;

import android.os.Handler;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentEventDetailsBinding;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;
import org.xms.g.maps.CameraUpdateFactory;
import org.xms.g.maps.ExtensionMap;
import org.xms.g.maps.MapsInitializer;
import org.xms.g.maps.OnMapReadyCallback;
import org.xms.g.maps.model.LatLng;
import org.xms.g.maps.model.MarkerOptions;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventDetailsFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {

    private FragmentEventDetailsBinding mBinding;
    private SharedViewModelEventDetails sharedViewModelEventDetails;
    private String location;
    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false);

        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);

        if (sharedViewModelEventDetails.getEvent() != null) {

            sharedViewModelEventDetails.getEvent().observe(getViewLifecycleOwner(), new Observer<SingleEvent>() {
                @Override
                public void onChanged(SingleEvent event) {
                    mBinding.setData(event);

                    location = event.getLocation();

                    title = event.getTitle();
                }
            });
        }

        if (sharedViewModelEventDetails.getMedia() != null) {
            mBinding.imageSlider.setSliderAdapter(new ImageSliderAdapter(getContext(), sharedViewModelEventDetails.getMedia()));
        }

        initView();

        initializeTabs();

        mBinding.tabs.getTabAt(sharedViewModelEventDetails.getEventDetailsTabPosition()).select();

        return mBinding.getRoot();
    }

    private void initView() {

        replaceFragmentNestedInContainer(new AgendaFragment(), R.id.fragment_container_event_details);

        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                OMEventsApiController.getMessages(getContext(), PreferencesHelper.getEventId(getContext()));
            }
        });

        if (PreferencesHelper.getEventReadMoreFlag(getContext()) != null) {

            if (PreferencesHelper.getEventReadMoreFlag(getContext())) {
                mBinding.setHasReadMore(true);
            } else {
                mBinding.setHasReadMore(false);
            }
        } //may need to port over to viewmodel

        mBinding.readMore.setOnClickListener(this);

        mBinding.rateButton.setOnClickListener(this);

        mBinding.addToc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Are you sure you want to add this event to your Calendar?", getContext());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mBinding.mapView != null) {
            mBinding.mapView.onCreate(null);
            mBinding.mapView.onResume();
            mBinding.mapView.getMapAsync(this);
        }
    }

    private void initializeTabs() {

        mBinding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                sharedViewModelEventDetails.setEventDetailsTabPosition(tab.getPosition());

                if (tab.getPosition() == 0) {

                    replaceFragmentNestedInContainer(new AgendaFragment(), R.id.fragment_container_event_details);
                } else if (tab.getPosition() == 1) {

                    replaceFragmentNestedInContainer(new EventDiscussionFragment(), R.id.fragment_container_event_details);
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
        mBinding.mapView.onResume();
        registerEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
        unregisterEventBus();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBinding.mapView.onStop();
    }

    @Override
    public void onDestroy() {
        mBinding.mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageResponse(List<EventMessage> result) {
        mBinding.swipeContainer.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.swipeContainer.setRefreshing(false);
    }

    public void addToCal(SingleEvent event) {

        try {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, 2);
            } else {
                ContentResolver cr = getContext().getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, DateHelper.getTimeinMil(event.getStartDate()));
                values.put(CalendarContract.Events.DTEND, DateHelper.getTimeinMil(event.getEndDate()));
                values.put(CalendarContract.Events.TITLE, event.getTitle());
                values.put(CalendarContract.Events.DESCRIPTION, event.getAgenda());
                values.put(CalendarContract.Events.CALENDAR_ID, 1);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                Toast.makeText(getContext(), "You have added this event to your calender", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.read_more:
                mBinding.readMore.setVisibility(View.GONE);
                mBinding.extendedView.setVisibility(View.VISIBLE);
                PreferencesHelper.setEventReadMoreFlag(getContext(), true);
                break;
            case R.id.rate_button:
                PreferencesHelper.setRatingSpeaker(getContext(), false);
                showNestedFragmentCustom(new RateSessionFragment(), true);
                break;
        }
    }

    @Override
    public void onMapReady(final ExtensionMap map) {

        Context context = getContext();

        if (context != null) {
            MapsInitializer.initialize(context);
        }


            if (getSingleLocationFromAddress(location) != null) {
                Marker marker = map.addMarker(new MarkerOptions().position(getSingleLocationFromAddress(location)).title(title));
                marker.showInfoWindow();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(getSingleLocationFromAddress(location), 13));
            }


        map.setMapType(ExtensionMap.getMAP_TYPE_NORMAL());
    }

    public LatLng getSingleLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> address;
        Address location;
        LatLng temp = null;
        String strAddresNew = strAddress.replace(",", " ");
        try {
            address = coder.getFromLocationName(strAddresNew, 1);
            if (!address.isEmpty()) {
                location = address.get(0);
                location.getLatitude();
                location.getLongitude();
                temp = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d("Latlng : ", temp + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public void showAlertDialog(String text, Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(text);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    if (sharedViewModelEventDetails.getEvent() != null) {

                        sharedViewModelEventDetails.getEvent().observe(getViewLifecycleOwner(), new Observer<SingleEvent>() {
                            @Override
                            public void onChanged(SingleEvent event) {
                                addToCal(event);
                            }
                        });
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (final Exception e) {
            Log.d(AlertDialogHelper.class.getSimpleName(), "showAlertDialog(): Failed.", e);
        }
    }
}
