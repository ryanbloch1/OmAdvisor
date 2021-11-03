package com.cobi.puresurveyandroid.fragment;

import androidx.lifecycle.Observer;
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

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.PipelineProductsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentPipelineProductsBinding;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.PipelineProduct;
import com.cobi.puresurveyandroid.model.PipelineProductRequest;
import com.cobi.puresurveyandroid.model.PipelineProductResponse;
import com.cobi.puresurveyandroid.model.RequirementGroup;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.model.key;
import com.cobi.puresurveyandroid.webservice.controller.PipelinesApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PipelineProductsFragment extends BaseFragment {


    private ClientViewModel clientViewModel;
    private PipelineProductsAdapter mAdapter;
    private static final String TAG = PipelineFragment.class.getSimpleName();
    private FragmentPipelineProductsBinding mBinding;
    private String errorMessage;
    private static final String ERROR_KEY = "ERROR_MESSAGE";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pipeline_products, container, false);

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        if(clientViewModel.getPipelineHash() != null){
            clientViewModel.getPipelineHash().observe(getViewLifecycleOwner(), new Observer<HashMap<String, List<String>>>() {
                @Override
                public void onChanged(@Nullable HashMap<String, List<String>> pipeHash) {

                    if(clientViewModel.getContractingParty()!=null){

                        PipelineProductRequest request = new PipelineProductRequest();

                        mBinding.progress.setVisibility(View.VISIBLE);

                        List<key> keys = new ArrayList<>();
                        for(String key : pipeHash.get(clientViewModel.getContractingParty())){
                          keys.add(new key(key));
                        }
                        request.setKeys(keys);

                        PipelinesApiController.getPipelineProducts(getContext(),request);
                    }

                }
            });
        }

        if(savedInstanceState != null && savedInstanceState.containsKey(ERROR_KEY)){

            errorMessage = savedInstanceState.getString(ERROR_KEY);
            mBinding.setError(errorMessage);
        }

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        registerEventBus();

    }

    public void onPause() {
        super.onPause();

        unregisterEventBus();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(!TextUtils.isEmpty(errorMessage)){
            outState.putString(ERROR_KEY, errorMessage);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(PipelineProductResponse result) {

        List<PipelineProduct> response = result.getPipelineProducts();
        ArrayList<PipelineProduct> filteredResponse = new ArrayList<>();


        mBinding.progress.setVisibility(View.GONE);
        if (response != null && response.size() != 0) {

            for(PipelineProduct pipelineProduct: response){

                checkForRequirements: {

                    for(RequirementGroup requirementGroup: pipelineProduct.getRequirementGroup()){
                        if (requirementGroup.getRequirements() != null) {

                            filteredResponse.add(pipelineProduct);
                            break checkForRequirements;

                        }
                    }

                }
            }

            if(filteredResponse != null && filteredResponse.size() > 0){
                populateAdapter(filteredResponse);
            }
            else{
                mBinding.setError("No Requirements");
                mBinding.pipelineProductList.setVisibility(View.GONE);
            }

        } else {
            if(mAdapter!=null) {
                mAdapter.clearItems();
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progress.setVisibility(View.GONE);

        if (mAdapter != null) {
            mAdapter.clearItems();
        }

        else if (errorResponse.getRequest().equals(PipelinesApiController.CUSTOMER_GET_PIPELINE_PRODUCTS)) {
            errorMessage = errorResponse.getMessage();

            mBinding.setError(errorResponse.getMessage());
        }

    }

    public void populateAdapter(List<PipelineProduct> data){
        mAdapter = new PipelineProductsAdapter(data, getContext());
        mBinding.pipelineProductList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.pipelineProductList.setHasFixedSize(true);
        mBinding.pipelineProductList.setAdapter(mAdapter);
    }
}
