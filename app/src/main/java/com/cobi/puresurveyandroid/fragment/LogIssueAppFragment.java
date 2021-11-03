package com.cobi.puresurveyandroid.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentIssueLogBinding;
import com.cobi.puresurveyandroid.databinding.FragmentLogOmIssueBinding;
import com.cobi.puresurveyandroid.model.IssueLog;
import com.cobi.puresurveyandroid.model.OMIssueLog;
import com.cobi.puresurveyandroid.model.UserDetailsLogIssue;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.OmIssueController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class LogIssueAppFragment extends BaseFragment {

    FragmentIssueLogBinding mBinding;


    List<String> appIssue = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue_log, container, false);


        mBinding.setData(new UserDetailsLogIssue(PreferencesHelper.getFullName(getContext()), PreferencesHelper.getClientEmail(getContext()), ""));


        mBinding.dateTooltip.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                new SimpleTooltip.Builder(getContext())
                        .anchorView(mBinding.dateTooltip)
                        .text("If you are unsure of the date and time the incident occurred, please give an approximate time")
                        .gravity(Gravity.END)
                        .backgroundColor(ResourceHelper.getColour(R.color.light_grey))
                        .arrowColor(ResourceHelper.getColour(R.color.light_grey))
                        .transparentOverlay(false)
                        .build()
                        .show();
            }
        });

        mBinding.commentsTooltip.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                new SimpleTooltip.Builder(getContext())
                        .anchorView(mBinding.commentsTooltip)
                        .text("Please add any relevant information about the error.")
                        .gravity(Gravity.START)
                        .backgroundColor(ResourceHelper.getColour(R.color.light_grey))
                        .arrowColor(ResourceHelper.getColour(R.color.light_grey))
                        .transparentOverlay(false)
                        .build()
                        .show();
            }
        });


        init();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth) ;
                updateDateLabel(mBinding.dateError, mBinding.dateIssueOccured, mBinding.timeError, mBinding.time);
            }
        };

        mBinding.dateIssueOccured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mBinding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(mBinding.timeError, mBinding.time);
            }
        });


        return mBinding.getRoot();
    }

    private void init() {

        appIssue.add("Sales Earnings");
        appIssue.add("Leads");
        appIssue.add("Missed Premiums");
        appIssue.add("Reintermediation");
        appIssue.add("Client Birthdays");
        appIssue.add("Pipeline");
        appIssue.add("Logging in");
        appIssue.add("General");
        appIssue.add(getText(R.string.choose_spinner_option).toString());


        ArrayAdapter<String> dataAdapterappIssue = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_layout, appIssue) {

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };

        dataAdapterappIssue.setDropDownViewResource(R.layout.spinner_dropdown_item);


        mBinding.appSpinner.setAdapter(dataAdapterappIssue);
        mBinding.appSpinner.setSelection(dataAdapterappIssue.getCount());


        mBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();
            }
        });

    }

    private void validate() {
        try {
            String email = mBinding.email.getText().toString();
            String contact = mBinding.contact.getText().toString();
            String date = mBinding.dateIssueOccured.getText().toString();
            String time = mBinding.time.getText().toString();
            String appIssueSpinner = mBinding.appSpinner.getSelectedItem().toString();
            String comments = mBinding.commentBox.getText().toString();


            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(contact) && !appIssueSpinner.equals(getText(R.string.choose_spinner_option))) {

                if (!isValidEmail(email) || !validCellPhone(contact)) {

                    if (!isValidEmail(email)) {
                        mBinding.email.setError("Please enter a valid email address");
                        mBinding.email.requestFocus();
                    }

                    if (!validCellPhone(contact)) {
                        mBinding.contact.setError("Please enter a valid 10 digit contact number");
                        mBinding.contact.requestFocus();
                    }

                } else {

                    postIncident();

                }
            } else {
                if (TextUtils.isEmpty(email)) {
                    mBinding.email.setError("Email required");
                    mBinding.email.requestFocus();
                } else {
                    if (!isValidEmail(email)) {
                        mBinding.email.setError("Please enter valid email address");
                        mBinding.email.requestFocus();
                    }
                }


                if (TextUtils.isEmpty(contact)) {
                    mBinding.contact.setError("Contact number required");
                    mBinding.contact.requestFocus();
                } else {
                    if (!validCellPhone(contact)) {
                        mBinding.contact.setError("Please enter a valid 10 digit contact number");
                        mBinding.contact.requestFocus();
                    }
                }

                if (appIssueSpinner.equals(getText(R.string.choose_spinner_option))) {
                    showSpinnerError(mBinding.appSpinner);
                }

                if (date.equals(getText(R.string.date_placeholder))) {
                    mBinding.dateError.setVisibility(View.VISIBLE);
                    mBinding.dateIssueOccured.requestFocus();
                }

                if (time.equals(getText(R.string.time_placeholder))) {
                    mBinding.timeError.setVisibility(View.VISIBLE);
                    mBinding.time.requestFocus();
                }


            }
        } catch (Exception ex) {
            Log.e("RETROFIT : ", ex.toString());
        }
    }


    private void postIncident() {
        String salesCode = PreferencesHelper.getSalesCode(getContext());
        String email = mBinding.email.getText().toString();
        String contactNo = mBinding.contact.getText().toString();
        String appIssue = mBinding.appSpinner.getSelectedItem().toString();
        String dateissueOccured =mBinding.dateIssueOccured.getText().toString() + "T" + mBinding.time.getText().toString();
        String comments = mBinding.commentBox.getText().toString();

        IssueLog object = new IssueLog(salesCode, email, contactNo, dateissueOccured, comments, appIssue);

        OmIssueController.postAppIncident(getContext(), object);
        mBinding.thanks.setVisibility(View.VISIBLE);
    }


}
