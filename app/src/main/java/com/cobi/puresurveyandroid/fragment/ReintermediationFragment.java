package com.cobi.puresurveyandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.ReinterLeadsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentReListBinding;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Lead;
import com.cobi.puresurveyandroid.model.ReIntermediary;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.ReinterApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cobi.puresurveyandroid.webservice.controller.ReinterApiController.RE_LIST;

public class ReintermediationFragment extends ViewPagerFragment implements ReinterLeadsAdapter.onLeadSelected {

    private FragmentReListBinding mBinding;
    public final String NAME = "LeadsFragment";
    ReinterLeadsAdapter leadsAdapter;
    ClientViewModel clientViewModel;


    public static ReintermediationFragment newInstance() {
        return new ReintermediationFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LeadCampaignAccessed.getNumVal(), String.valueOf(PreferencesHelper.getCampaignId(getContext()))));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_re_list, container, false);

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

//        mBinding.progress.setVisibility(View.VISIBLE);
//
//        Context context = getContext();
//        if (context != null) {
//
//            CSIApiController.getLeads(context, PreferencesHelper.getSalesCode(context), String.valueOf(PreferencesHelper.getCampaignId(context)));
//        }

        return mBinding.getRoot();
    }

    public void callAPI() {
        ReinterApiController.GetReIntermediaries(getContext(), PreferencesHelper.getSalesCode(getContext()));

    }


    @Override
    public void onBecommingVisible(boolean visible) {


        if (visible) {

            mBinding.progress.setVisibility(View.VISIBLE);

            ReinterApiController.GetReIntermediaries(getContext(), PreferencesHelper.getSalesCode(getContext()));

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


    private void populateAdapter(List<ReIntermediary> response) {
        leadsAdapter = new ReinterLeadsAdapter(response, getContext(), this);
        mBinding.leadsList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.leadsList.setHasFixedSize(true);
        mBinding.leadsList.setAdapter(leadsAdapter);
        leadsAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(List<ReIntermediary> response) {


        if (response.isEmpty()) {
            mBinding.progress.setVisibility(View.GONE);

            mBinding.setError("Sorry you have no leads that need to be Re-intermediated");
            mBinding.textViewError.setVisibility(View.VISIBLE);

        } else {

            mBinding.setError(null);
            mBinding.textViewError.setVisibility(View.GONE);


            if (response.get(0) instanceof ReIntermediary) {

                mBinding.progress.setVisibility(View.GONE);

                List<ReIntermediary> sorted = response;

                Collections.sort(sorted, ReIntermediary.pComparator);


                populateAdapter(sorted);

            }
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {


        if (errorResponse.getRequest().equals(RE_LIST)) {
            mBinding.progress.setVisibility(View.GONE);
            mBinding.setError("Sorry you have no leads that need to be Re-intermediated");
            mBinding.textViewError.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onLeadSelected(ReIntermediary lead) {
        clientViewModel.setSelectedReLead(lead);

        showNestedFragment(new CustomerProfileFragment());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


}
