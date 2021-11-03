package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.adapter.ProductsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentContractDetailsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContractDetailsFragment extends BaseFragment {

    FragmentContractDetailsBinding binding;
    ClientViewModel clientViewModel;
    ProductsAdapter pa;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        PreferencesHelper.setCustomerFullName(getContext(), clientViewModel.getSelected().getValue().getContractingParty());

        String jsonString = "";

        try {
            jsonString = String.valueOf(new JSONObject()
                    .put("contractNumber", clientViewModel.getSelected().getValue().getContractNumber())
                    .put("contractingParty", clientViewModel.getSelected().getValue().getContractingParty()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.MissedPremiumContractsAccessed.getNumVal(),jsonString));

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contract_details, container, false);

        binding.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if(activity!=null) {
                    Fragment fragment = new ContactDetailsFragment3();
                    fragmentUtility.replaceFragment(activity,fragment , R.id.fragment_container, true, "CDF");
                    if(activity instanceof BaseActivity){
                        ((BaseActivity) activity).decideWhetherToShowBackButton(fragment);
                    }
                }
            }
        });

        clientViewModel.contractProducts.observe(getViewLifecycleOwner(), new Observer<List<MissedPremium>>() {
            @Override
            public void onChanged(@Nullable List<MissedPremium> missedPremiums) {

                ArrayList<MissedPremium> groupedPremiums = new ArrayList<>();

                for(MissedPremium m: missedPremiums){
                    if(clientViewModel.getSelected().getValue().getContractNumber().equals(m.getContractNumber())){
                        groupedPremiums.add(m);
                    }
                }

                pa = new ProductsAdapter(groupedPremiums, getContext());
                binding.productList.setLayoutManager( new LinearLayoutManager(ContractDetailsFragment.this.getContext()));
                binding.productList.setHasFixedSize(true);
                binding.productList.setAdapter(pa);
            }
        });

        return binding.getRoot();
    }
}
