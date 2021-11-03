package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.AdvisorAdapter;
import com.cobi.puresurveyandroid.adapter.CustomerEventsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentHotLeadsMainBinding;
import com.cobi.puresurveyandroid.databinding.FragmentHotLeadsReferBinding;
import com.cobi.puresurveyandroid.model.Advisor;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.DeclineReason;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.HotLeadsController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class HotLeadsFragment extends BaseFragment implements AdvisorAdapter.onAdvisorSelected{

    FragmentHotLeadsReferBinding mBinding;
    private ClientViewModel clientViewModel;
    private AdvisorAdapter advisorAdapter;
    private Advisor mAdvisor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot_leads_refer, container, false);


        mBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mBinding.fname.getText().toString().equals("")  && mBinding.sname.getText().toString().equals("") && mBinding.salesCode.getText().toString().equals("")){

                    Toast.makeText(getContext(), "Please enter at least one value", Toast.LENGTH_SHORT).show();

                }else{

                    HotLeadsController.searchAdvisors(getContext(), mBinding.salesCode.getText().toString(), mBinding.fname.getText().toString(), mBinding.sname.getText().toString());

                }

            }
        });

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        mBinding.refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mAdvisor != null){

                    if (clientViewModel.getSelectedLead().getValue() != null) {

                        HotLeadsController.referLead(getContext(), PreferencesHelper.getSalesCode(getContext()), mAdvisor.getSalesCode(), clientViewModel.getSelectedLead().getValue().getLeadId());



                        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LeadReferred.getNumVal(), generateHotLeadsJson(String.valueOf(clientViewModel.getSelectedLead().getValue().getLeadId()), PreferencesHelper.getSalesCode(getContext()))));

                    }
                }
                else{
                    Toast.makeText(getContext(), "Please select the advisor you would like to refer the lead to", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return mBinding.getRoot();
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


    private void populateAdapter(List<Advisor> advisors){
        advisorAdapter = new AdvisorAdapter(advisors, getContext(), this);
        mBinding.advisorList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.advisorList.setHasFixedSize(true);
        mBinding.advisorList.setAdapter(advisorAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(List<Advisor> response) {
        populateAdapter(response);
        mBinding.referSection.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessRefer(String response){
       AlertDialogHelper.showAlertDialog("Lead referred!", getContext());

       clearBackStack();

       showNestedFragment(new CampaignsFragment());

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(HotLeadsController.SEARCH_ADVISORS)) {

            AlertDialogHelper.showAlertDialog("No advisers found with those details, please try again.", getContext());
        }
    }

    @Override
    public void onAdvisorSelected(Advisor advisor) {

        mAdvisor = advisor;
    }
}