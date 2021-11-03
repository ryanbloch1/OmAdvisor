package com.cobi.puresurveyandroid.fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentContactDetails3Binding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.NumberFormatter;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactDetailsFragment3 extends ContactDetailsFragment2 {
    FragmentContactDetails3Binding binding;
    private ClientViewModel clientViewModel;
    private MissedPremium client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details3, container, false);

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        if(clientViewModel.getSelected().getValue() !=null ){
            client = clientViewModel.getSelected().getValue();
            binding.setData(client);
            binding.setClawbackTotal(NumberFormatter.formatNumber(clientViewModel.getTotalClawback()/100.0F));

            String jsonString = "";


            try {
                jsonString = String.valueOf(new JSONObject()
                        .put("contractNumber", clientViewModel.getSelected().getValue().getContractNumber())
                        .put("contractingParty", clientViewModel.getSelected().getValue().getContractingParty()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.MissedPremiumsCustomerAccessed.getNumVal(),jsonString));


        }

        initialize();

        return binding.getRoot();
    }

    private void initialize() {
        binding.layoutContactPhone.setOnClickListener(this);
        binding.layoutContactMobile.setOnClickListener(this);
        binding.layoutContactEmail.setOnClickListener(this);
        binding.whatsapp.setOnClickListener(this);
        binding.layoutEmail.setOnClickListener(this);
        binding.layoutSms.setOnClickListener(this);
    }


    public  void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutContactPhone:
                makePhoneCall(client.getWorkTelephone(), ActionTypes.MissedPremiumsContactPhone);
                break;
            case R.id.layoutContactMobile:
                makePhoneCall(client.getCellNumber(), ActionTypes.MissedPremiumsContactPhone);
                break;
            case R.id.layoutContactEmail:
                sendEmail(client.getEmail(),false, ActionTypes.MissedPremiumsContactEmail);
                break;
            case R.id.layoutEmail:
                sendEmail(client.getEmail(),false, ActionTypes.MissedPremiumsContactEmail);
                break;
            case R.id.layoutSms:
                sendSms(client.getCellNumber(),false, ActionTypes.MissedPremiumsContactSms);
                break;
            case R.id.whatsapp:

                if (client != null && !TextUtils.isEmpty(client.getCellNumber())) {
                    openWhatsappConversation(client.getCellNumber(), "");
                }

                break;
        }
    }

}
