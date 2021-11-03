package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.CustomerEventsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentUserCustomerEventsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.Birthday;
import com.cobi.puresurveyandroid.model.BirthdayResponse;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.xms.f.analytics.ExtensionAnalytics;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class UserCustomerEventsFragment extends ViewPagerFragment implements View.OnClickListener, CustomerEventsAdapter.OnBirthdaySelected {

    private static final String TAG = UserCustomerEventsFragment.class.getSimpleName();
    private static final String EXTRA_CUSTOMER_EVENTS = "CUSTOMER_EVENTS";
    private static final String ERROR_KEY = "ERROR_MESSAGE";
    private FragmentUserCustomerEventsBinding mBinding;
    private CustomerEventsAdapter mAdapter;
    public static int bIndex = 0;
    private ClientViewModel clientViewModel;
    private List<Birthday> birthdaysList;
    private String errorMessage;

    public UserCustomerEventsFragment() {
        // Required empty public constructor
    }

    public static UserCustomerEventsFragment newInstance() {
        return new UserCustomerEventsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_customer_events, container, false);
        mBinding.setTitle(getResources().getString(R.string.events_title));

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);


        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(getContext());
            instanceFA.setCurrentScreen(getActivity(), "Birthdays", this.getClass().getSimpleName());
        }

        if (clientViewModel.getBirthdays() != null) {
            clientViewModel.getBirthdays().observe(getViewLifecycleOwner(), new Observer<List<Birthday>>() {
                @Override
                public void onChanged(@Nullable List<Birthday> birthdays) {

                    if (birthdays != null) {
                        birthdaysList = birthdays;

                        populateAdapter(birthdays);
                    }
                }
            });
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(ERROR_KEY)) {

            errorMessage = savedInstanceState.getString(ERROR_KEY);
            mBinding.setError(errorMessage);
        }

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View v) {

    }

    private void retrieveCustomerEventData() {
        CSIApiController.getBirthdays(getContext(), PreferencesHelper.getSalesCode(getContext()));
        mBinding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBecommingVisible(boolean visible) {
        super.onBecommingVisible(visible);
        if (visible) {

            AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.BirthdaysAccessed.getNumVal(), ""));

            retrieveCustomerEventData();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!TextUtils.isEmpty(errorMessage)) {
            outState.putString(ERROR_KEY, errorMessage);
        }
    }

    public void populateAdapter(List<Birthday> data) {
        mAdapter = new CustomerEventsAdapter(data, getContext(), this);
        mBinding.recyclerViewBirthdays.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.recyclerViewBirthdays.setHasFixedSize(true);
        mBinding.recyclerViewBirthdays.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(List<Birthday> response) {


        mBinding.progress.setVisibility(View.GONE);
        if (response != null && response.size() != 0) {

            if(response.get(0) instanceof  Birthday){
                clientViewModel.setBirthdays(response);

                populateAdapter(response);
            }


        }
//        else {
//            if (mAdapter != null) {
//                mAdapter.clearItems();
//            }
//            mBinding.setError(getString(R.string.error_no_events));
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_GET_BIRTHDAYS)) {
            Log.d(TAG, "onErrorResponse: " + errorResponse.getMessage());

            mBinding.progress.setVisibility(View.GONE);
            if (mAdapter != null) {
                mAdapter.clearItems();
            }


            errorMessage = errorResponse.getMessage();

            mBinding.setError(getString(R.string.error_no_events));
        }
    }

    @Override
    public void onBirthdaySelected(Birthday selectedBirthday) {
        Activity activity = getActivity();
        if (activity != null) {
            PreferencesHelper.setGcsId(getContext(), selectedBirthday.getGCSId());

            PreferencesHelper.setCustomerFullName(getContext(), selectedBirthday.getFullName());

            ContactDetailFragment fragment = new ContactDetailFragment();
            showNestedFragment(fragment);
        }
    }
}