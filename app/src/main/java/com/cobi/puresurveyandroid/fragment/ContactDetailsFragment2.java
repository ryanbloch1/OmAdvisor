package com.cobi.puresurveyandroid.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentContactDetails2Binding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.CSIContactDetails;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.StringErrorResponse;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactDetailsFragment2  extends ContactDetailFragment implements View.OnClickListener {

    FragmentContactDetails2Binding binding;
    private CSIContactDetails contact;
    ClientViewModel clientViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        clientViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(ClientViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details2, container, false);

        if(clientViewModel.getSelectedLead().getValue() != null){
            CSIApiController.getContactDetailsLeads(getContext(),clientViewModel.getSelectedLead().getValue().getLeadId());
        }

        binding.progress.setVisibility(View.VISIBLE);
        initialize();

        return binding.getRoot();
    }

    private void initialize() {
        binding.layoutContactPhone.setOnClickListener(this);
        binding.layoutContactMobile.setOnClickListener(this);
        binding.layoutContactEmail.setOnClickListener(this);
        binding.layoutEmail.setOnClickListener(this);
        binding.layoutSms.setOnClickListener(this);
        binding.whatsapp.setOnClickListener(this);
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

    public  void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutContactPhone:
                if (contact != null)
                {
                    makePhoneCall(contact.getWorkNumber(), ActionTypes.LeadContactPhone);
                }
                break;
            case R.id.layoutContactMobile:
                if(contact  != null)
                {
                    makePhoneCall(contact.getWorkMobileNumber(), ActionTypes.LeadContactPhone);
                }
                break;
            case R.id.layoutContactEmail:
                if(contact != null)
                {
                    sendEmail(contact.getWorkEmailAddress(),false ,ActionTypes.LeadContactEmail);
                }
                break;
            case R.id.layoutEmail:
                if(contact != null)
                {
                    sendEmail(contact.getWorkEmailAddress(),false, ActionTypes.LeadContactEmail);
                }
                break;
            case R.id.layoutSms:
                if(contact != null)
                {
                    sendSms(contact.getWorkMobileNumber(), false,ActionTypes.LeadContactSms);
                }

                break;
            case R.id.whatsapp:

                if (contact != null && !TextUtils.isEmpty(contact.getWorkMobileNumber())) {
                    openWhatsappConversation(contact.getWorkMobileNumber(), "");
                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(CSIContactDetails result) {
        binding.progress.setVisibility(View.GONE);
        if (result != null) {
            binding.setData(result);
            contact = result;
            


            String jsonString = "";

            try {
                jsonString = String.valueOf(new JSONObject()
                        .put("fullName", result.getFullName())
                        .put("gCSId", result.getGCSId()));

                PreferencesHelper.setGcsId(getContext(), result.getGCSId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LeadCustomerAccessed.getNumVal(), jsonString));

        } else {
            showToast();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        binding.progress.setVisibility(View.GONE);
        if(errorResponse.getRequest().equals(CSIApiController.CUSTOMER_GET_CONTACT_DETAILS_LEADS)) {
            showToast("Sorry! Client details can only be viewed when the lead is accepted.");
        }
    }

    public void showToast(String error)
    {
        Context context = getContext();
        if(context!=null) {
            Toast toast = Toast.makeText(context, error, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
