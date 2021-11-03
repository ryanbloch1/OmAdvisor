package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.OmpAdapter;
import com.cobi.puresurveyandroid.adapter.PipelineAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentPipelineBinding;
import com.cobi.puresurveyandroid.databinding.PopupOrBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.OmpClientRequest;
import com.cobi.puresurveyandroid.model.OmpOutstandingRequirements;
import com.cobi.puresurveyandroid.model.OutstandingReqParty;
import com.cobi.puresurveyandroid.model.UwRequirement;
import com.cobi.puresurveyandroid.model.OmpReqStrings;
import com.cobi.puresurveyandroid.model.AdviserSubmit;
import com.cobi.puresurveyandroid.model.OmpClientsResponse;
import com.cobi.puresurveyandroid.model.Party;
import com.cobi.puresurveyandroid.model.Pipeline;
import com.cobi.puresurveyandroid.model.PipelineResponse;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.FragmentUtility;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.OmpController;
import com.cobi.puresurveyandroid.webservice.controller.PipelinesApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PipelineFragment extends BaseFragment implements PipelineAdapter.OnPipelineSelected, View.OnClickListener, OmpAdapter.OnOmpItemSelected {


    private ClientViewModel clientViewModel;
    private PipelineAdapter mAdapter;
    private static final String TAG = PipelineFragment.class.getSimpleName();
    private FragmentPipelineBinding mBinding;
    FragmentUtility fragmentUtility;
    private String errorMessage;
    private static final String ERROR_KEY = "ERROR_MESSAGE";
    private OutstandingReqParty mClient = null;

    private List<OmpClientsResponse> ompClientsResponseList;

    boolean gB = false;
    boolean omb = false;
    boolean sib = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.PipelineAccessed.getNumVal(), ""));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pipeline, container, false);

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);


        mBinding.greenlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisLists(mBinding.pipelineList, mBinding.ompList);


                changeImage(mBinding.glarrow, gB == false ? false : true);

                gB = !gB;

//                changeImage(mBinding.omparrow, omb == false ? false : true);
//
//                omb = !omb;

            }
        });


        mBinding.glarrow.setOnClickListener(this);
        mBinding.omparrow.setOnClickListener(this);


        fragmentUtility = new FragmentUtility();

        mBinding.progress.setVisibility(View.VISIBLE);
        PipelinesApiController.getPipelines(getContext());

        OmpController.getClients(getContext(), new OmpClientRequest(PreferencesHelper.getSalesCode(getContext()), PreferencesHelper.getRole(getContext())));


        if (savedInstanceState != null && savedInstanceState.containsKey(ERROR_KEY)) {

            errorMessage = savedInstanceState.getString(ERROR_KEY);
            mBinding.setError(errorMessage);
        }

        return mBinding.getRoot();
    }

    public void toggleVisLists(View view, View view2) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }

        }

        if (view2.getVisibility() == View.VISIBLE) {
            view2.setVisibility(View.GONE);

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mBinding.omp.setOnClickListener(this);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!TextUtils.isEmpty(errorMessage)) {
            outState.putString(ERROR_KEY, errorMessage);
        }
    }


    private void changeImage(ImageView imageView, boolean b) {

        if (b == false) {
            imageView.setBackground(ResourceHelper.getDrawable(R.drawable.uparrow));
        } else {
            imageView.setBackground(ResourceHelper.getDrawable(R.drawable.downarrow));


        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(List<OmpClientsResponse> response) {


        ArrayList<Party> ompList = new ArrayList<>();


        ompClientsResponseList = response;

        if (!response.isEmpty()) {

            mBinding.ompProtectSection.setVisibility(View.VISIBLE);

            for (OmpClientsResponse client : response) {

                for (Party p : client.getAdviserSubmit().getParty()) {

                    if (p.getRoles().contains("POLICY OWNER")) {
                        p.setApplicationNumber(client.getAdviserSubmit().getApplicationInstance());

                        p.setDateTimeStampForApplicationInstance(client.getAdviserSubmit().getDateTimestamp());

                        ompList.add(p);

                    }

                }

            }


            //remove nulls from list




            for (int i = 0; i < ompList.size(); i++) {


                if (ompList.get(i).getFirstName() == null && ompList.get(i).getLastName() == null) {

                    if (ompList.get(i).getPartyType().equals("Organization")) {

                        if(ompList.get(i).getLegalStructure() != null || ompList.get(i).getName() != null){

                            ompList.get(i).setLastName(ompList.get(i).getLegalStructure());
                            ompList.get(i).setFirstName(ompList.get(i).getName());
                        }
                        else{
                            ompList.remove(i);
                        }

                    }
                    else{
                        ompList.remove(i);
                    }

                }
            }

            //sort alphabetically..

            Collections.sort(ompList, Party.pComparator);


            mBinding.omno.setText("(" + ompList.size() + ")");


            OmpAdapter Adapter = new OmpAdapter(ompList, getContext(), this);
            mBinding.ompList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            mBinding.ompList.setHasFixedSize(true);
            mBinding.ompList.setAdapter(Adapter);
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(PipelineResponse result) {


        if (result instanceof PipelineResponse) {
            List<Pipeline> response = result.getPipelines();
            mBinding.progress.setVisibility(View.GONE);
            if (response != null && response.size() != 0) {

                mBinding.greenlightSection.setVisibility(View.VISIBLE);


                ArrayList<String> groupedResult = new ArrayList<>();

                HashMap<String, List<String>> hashMap = new HashMap<>();


                for (Pipeline pipeline : response) {

                    String contractingParty = pipeline.getContractingParty();

                    if (!hashMap.containsKey(contractingParty)) {
                        List<String> keys = new ArrayList<>();


                        for (int i = 0; i < response.size(); i++) {
                            if (response.get(i).getContractingParty().equals(contractingParty)) {
                                keys.add(pipeline.getKey());
                            }
                        }

                        hashMap.put(contractingParty, keys);
                    }
                }

                if (hashMap != null) {

                    clientViewModel.setPipelineHash(hashMap);


                    for (String key : hashMap.keySet()) {
                        groupedResult.add(key);
                    }


                    mBinding.glno.setText("(" + groupedResult.size() + ")");

                    Collections.sort(groupedResult);
                    populateAdapter(groupedResult);
                }

            } else {



                if (mAdapter != null) {
                    mAdapter.clearItems();
                }
            }
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progress.setVisibility(View.GONE);

//        if (mAdapter != null) {
//            mAdapter.clearItems();
//        }
//
//        if (errorResponse.getRequest().equals(PipelinesApiController.CUSTOMER_GET_PIPELINES)) {
//            mBinding.greenlightSection.setVisibility(View.GONE);
//        }
//        if (errorResponse.getRequest().equals(OmpController.CUSTOMER_GET_ClIENTS)) {
//            mBinding.ompProtectSection.setVisibility(View.GONE);
//
//        }


    }

    public void populateAdapter(List<String> data) {
        mAdapter = new PipelineAdapter(data, getContext(), this);
        mBinding.pipelineList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.pipelineList.setHasFixedSize(true);
        mBinding.pipelineList.setAdapter(mAdapter);
    }


    @Override
    public void onPipelineSelected(String contractingParty) {

        clientViewModel.setContractingParty(contractingParty);

        String jsonString = "";

        try {
            jsonString = String.valueOf(new JSONObject()
                    .put("contractingParty", contractingParty));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.PipelineCustomerAccessed.getNumVal(), jsonString));


        Activity activity = getActivity();
        if (activity != null) {
            PipelineProductsFragment fragment = new PipelineProductsFragment();
            if (fragmentUtility != null) {
                fragmentUtility.addFragment(activity, fragment, R.id.fragment_container, true, TAG);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.omp:

                toggleVisLists(mBinding.ompList, mBinding.pipelineList);


                changeImage(mBinding.omparrow, omb == false ? false : true);

                omb = !omb;

//                changeImage(mBinding.glarrow, gB == false ? false : true);
//
//                gB = !gB;


        }


    }

    @Override
    public void OnOmpItemSelected(Party contractingParty) {


        //go to next screen..


        AdviserSubmit tempAdvisorSubmit = null;

        if (ompClientsResponseList != null && !ompClientsResponseList.isEmpty()) {
            for (OmpClientsResponse r : ompClientsResponseList) {

                try {

                    if( r.getAdviserSubmit().getDateTimestamp()!= null){
                        if (r.getAdviserSubmit().getApplicationInstance().equals(contractingParty.getApplicationNumber()) && r.getAdviserSubmit().getDateTimestamp().equals(contractingParty.getDateTimeStampForApplicationInstance())) {

                            tempAdvisorSubmit = r.getAdviserSubmit();

                            break;
                        }
                    }

                    else{
                        if (r.getAdviserSubmit().getApplicationInstance().equals(contractingParty.getApplicationNumber())) {

                            tempAdvisorSubmit = r.getAdviserSubmit();

                            break;
                        }
                    }


                } catch (Exception e) {

                }


            }
        }

        if (tempAdvisorSubmit != null) {
            clientViewModel.setSelectOmProtectClients(tempAdvisorSubmit);
        }

        clientViewModel.setSelectedParty(contractingParty);


        //got to fragment


        Activity activity = getActivity();
        if (activity != null) {

            if (fragmentUtility != null) {
                fragmentUtility.addFragment(activity, new OmpContractFragment(), R.id.fragment_container, true, OmpContractFragment.class.getSimpleName());
            }
        }


    }


}
