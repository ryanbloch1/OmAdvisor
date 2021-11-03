package com.cobi.puresurveyandroid.fragment;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentRateSessionBinding;
import com.cobi.puresurveyandroid.model.Agenda;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Question;
import com.cobi.puresurveyandroid.model.RatingRequest;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class RateSessionFragment extends BaseFragment implements View.OnClickListener {

    private FragmentRateSessionBinding mBinding;
    private float ratingValue;
    private String questionId;
    private boolean flag = false;
    private ArrayList<CheckBox> checkboxes = new ArrayList<>();
    private SharedViewModelEventDetails sharedViewModelEventDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rate_session, container, false);

        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);

        if (!PreferencesHelper.getRatingSpeaker(getContext())) {  //checks to see if user is rating a speaker or event. could move this data to sharedviewmodel

            if (sharedViewModelEventDetails.getEvent() != null) {
                mBinding.setEventData(sharedViewModelEventDetails.getEvent().getValue());
            }
        } else {

            if (sharedViewModelEventDetails.getAgenda() != null) {

                mBinding.setAgenda(sharedViewModelEventDetails.getAgenda().getValue());
            }
        }

        if (!TextUtils.isEmpty(PreferencesHelper.getClientName(getContext()))) {
            mBinding.setFullName(PreferencesHelper.getClientName(getContext()));
        }

        if (!TextUtils.isEmpty(PreferencesHelper.getClientEmail(getContext()))) {
            mBinding.setEmail(PreferencesHelper.getClientEmail(getContext()));
        }

        fetchRatingQuestion();

        mBinding.submit.getBackground().setAlpha(128);

        mBinding.stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                flag = true;

                ratingValue = v;

                mBinding.submit.getBackground().setAlpha(255);
            }
        });

        mBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //api post request with rating and comments... navigation to thanks frag..

                if (flag) {
                    RatingRequest ratingRequest;

                    if (mBinding.stars.getVisibility() == View.VISIBLE) {

                        if (PreferencesHelper.getRatingSpeaker(getContext())) { //checks if a speaker or an event is being rated
                            ratingRequest = new RatingRequest(PreferencesHelper.getCommonName(getContext()), PreferencesHelper.getSpeakerId(getContext()), questionId, (int) ratingValue, mBinding.comment.getText().toString());

                            if (questionId != null) {
                                OMEventsApiController.postRatingSpeaker(getContext(), ratingRequest);
                            }
                        } else {
                            ratingRequest = new RatingRequest(PreferencesHelper.getCommonName(getContext()), PreferencesHelper.getEventId(getContext()), questionId, (int) ratingValue, mBinding.comment.getText().toString());

                            if (questionId != null) {
                                OMEventsApiController.postRatingEvent(getContext(), ratingRequest);
                            }
                        }
                    } else {
                        //check which check box is checked and send that tag value?

                        for (CheckBox c : checkboxes) {

                            if (c.isChecked()) {
                                int answer = Integer.valueOf(c.getTag().toString());

                                if (PreferencesHelper.getRatingSpeaker(getContext())) {
                                    ratingRequest = new RatingRequest(PreferencesHelper.getCommonName(getContext()), PreferencesHelper.getSpeakerId(getContext()), questionId, answer, mBinding.comment.getText().toString());

                                    if (questionId != null) {
                                        OMEventsApiController.postRatingSpeaker(getContext(), ratingRequest);
                                    }
                                } else {
                                    ratingRequest = new RatingRequest(PreferencesHelper.getCommonName(getContext()), PreferencesHelper.getEventId(getContext()), questionId, answer, mBinding.comment.getText().toString());

                                    if (questionId != null) {
                                        OMEventsApiController.postRatingEvent(getContext(), ratingRequest);
                                    }
                                }

                                break;
                            }
                        }
                    }

                    hideKeyboard();

                    showNestedFragment(new RatingSuccessFragment());
                }
            }
        });

        mBinding.disa.setOnClickListener(this);
        mBinding.fair.setOnClickListener(this);
        mBinding.na.setOnClickListener(this);
        mBinding.excel.setOnClickListener(this);
        mBinding.good.setOnClickListener(this);

        checkboxes.add(mBinding.disa);
        checkboxes.add(mBinding.fair);
        checkboxes.add(mBinding.na);
        checkboxes.add(mBinding.excel);
        checkboxes.add(mBinding.good);

        return mBinding.getRoot();
    }

    private void fetchRatingQuestion() {

        if (PreferencesHelper.getRatingSpeaker(getContext())) {
            OMEventsApiController.getSpeakerRatingQuestion(getContext(), PreferencesHelper.getSpeakerId(getContext()));
        } else {
            OMEventsApiController.getEventRatingQuestion(getContext(), PreferencesHelper.getEventId(getContext()));
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQuestionSuccess(Question question) {

        mBinding.setQuestion(question);
        questionId = question.getId();

        if (question.getQuestionType() == 1) {
            mBinding.checkBoxes.setVisibility(View.GONE);
            mBinding.stars.setVisibility(View.VISIBLE);
        } else {
            mBinding.checkBoxes.setVisibility(View.VISIBLE);
            mBinding.stars.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        //Show error if unable to acccess data??

    }

    @Override
    public void onClick(View v) {

        mBinding.submit.getBackground().setAlpha(255);

        flag = true;

        final int checkedId = v.getId();
        for (int i = 0; i < checkboxes.size(); i++) {
            final CheckBox current = checkboxes.get(i);
            if (current.getId() == checkedId) {
                current.setChecked(true);
            } else {
                current.setChecked(false);
            }
        }
    }
}

