package com.cobi.puresurveyandroid.fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.MissedPremiumsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentMissedPremiumsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PageTypes;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.model.MissedPremiumResponse;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.Constants;
import com.cobi.puresurveyandroid.util.NumberFormatter;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MissedPremiumsFragment extends BaseFragment  {

    private ClientViewModel clientViewModel;
    RecyclerView rv;
    MissedPremiumsAdapter ma;
    public final String NAME = "MissedPremiumsFragment";
    FragmentMissedPremiumsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.MissedPremiumsAccessed.getNumVal(), ""));

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_missed_premiums, container, false);

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        binding.progress.setVisibility(View.VISIBLE);

        CSIApiController.getMissedPremiumsDetails(getContext());

        return binding.getRoot();
    }

    private void getTotalClaw(List<MissedPremium> list) {
        int totalMissed = 0;
        for (MissedPremium missed : list) {
            totalMissed += Integer.valueOf(missed.getClawback());
        }

        binding.setTotalValue(NumberFormatter.formatNumber(Double.valueOf(totalMissed) / 100.0F));
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
    public void onErrorResponse(ErrorResponse errorResponse) {
        if(errorResponse.getRequest().equals(CSIApiController.CUSTOMER_GET_MISSED_PREMIUM)) {
            binding.progress.setVisibility(View.GONE);
            binding.text3.setVisibility(View.GONE);
            binding.totalValueLabel.setText("Total value: R0");
            binding.setError(errorResponse.getMessage());

            if(ma != null){
                ma.clearItems();
            }

        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(MissedPremiumResponse response) {
        List<MissedPremium> result = response.getMissedPremiums();
        binding.text3.setVisibility(View.VISIBLE);
        binding.totalValueLabel.setVisibility(View.VISIBLE);
        binding.progress.setVisibility(View.GONE);
        if(result != null){
            if(result.get(0).getCurrency().equals("ZAR")){
                binding.setCurrency(Constants.ZAR);
            }
            if(result.get(0).getCurrency().equals("NAD")){
                binding.setCurrency(Constants.NAD);
            }
            else {
                binding.setCurrency(Constants.ZAR);
            }
        }
        else{
            binding.setCurrency(Constants.ZAR);
        }

        clientViewModel.setContractProducts(result);

        HashMap<String, MissedPremium> contractingParties = new HashMap();
        for (MissedPremium missedPremium: result) {
            ArrayList<MissedPremium> missedPremiums;
            if (contractingParties.containsKey(missedPremium.getContractingParty())) {
                continue;
            }
            contractingParties.put(missedPremium.getContractingParty(), missedPremium);
        }

        Set<String> keyset = contractingParties.keySet();
        String keys[] = keyset.toArray(new String[keyset.size()]);
        Arrays.sort(keys);

        ArrayList<MissedPremium> groupedResult = new ArrayList<>();

        for (String key : keys) {
            groupedResult.add(contractingParties.get(key));
        }

        ma = new MissedPremiumsAdapter(groupedResult, getContext());
        binding.missedPremiumsList.setLayoutManager( new LinearLayoutManager(MissedPremiumsFragment.this.getContext()));
        binding.missedPremiumsList.setHasFixedSize(true);
        binding.missedPremiumsList.setAdapter(ma);

        try{
            getTotalClaw(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    }
