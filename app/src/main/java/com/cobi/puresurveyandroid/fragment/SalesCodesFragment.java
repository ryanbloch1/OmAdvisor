package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.MatrixSelectionActivity;
import com.cobi.puresurveyandroid.activity.SalesActivity;
import com.cobi.puresurveyandroid.adapter.SalesCodesAdapter;
import com.cobi.puresurveyandroid.model.ChannelRole;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.model.SalesCodeModel;
import com.cobi.puresurveyandroid.model.ValidateResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.ImedAuthenticationController;
import com.cobi.puresurveyandroid.webservice.controller.SnapappAuthenticationApiController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;

public class SalesCodesFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SalesCodesAdapter mAdapter;
    private GifImageView progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sales_codes, container, false);

        recyclerView = view.findViewById(R.id.sales_code_recycler_view);
        progress = view.findViewById(R.id.progress);

        progress.setVisibility(View.VISIBLE);

        return view;
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(IMEDDetails response) {

        try {
            final List<ChannelRole> result = response.getIntermediaryDetails().get(0).getPerson().get(0).getChannelRole();
            if (result != null) {
                ArrayList<SalesCodeModel> sales_code = new ArrayList<>();

                if (result.size() > 1) {

                    for (ChannelRole salescode : result) {
                        sales_code.add(new SalesCodeModel(salescode.getExternalReference(), salescode.getChannelSegment().get(0).getName()));
                    }

                    mAdapter = new SalesCodesAdapter(sales_code, new SalesCodesAdapter.SalesCodeSelectedListener() {
                        @Override
                        public void salesCodeSelected(String code) {
                            Activity activity = getActivity();
                            if (activity != null) {
                                PreferencesHelper.setSalesCode(activity, code);

                                //get imed token
                                registerAndContinue();
                            }
                        }
                    });

                    Activity activity = getActivity();
                    if (activity != null) {
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        progress.setVisibility(View.GONE);
                    } else {
                        progress.setVisibility(View.GONE);
                        AlertDialogHelper.showAlertDialog(getResources().getString(R.string.no_intermediaries), getContext());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onValidationSuccess(ValidateResponse response) {

        if (response instanceof ValidateResponse) {
            goToDestinationActivity(MatrixSelectionActivity.class);
        }

    }

    private void registerAndContinue() {
        ImedAuthenticationController.register(getContext(), PreferencesHelper.getSalesCode(getContext()), PreferencesHelper.getCommonName(getContext()), PreferencesHelper.getClientEmail(getContext()), PreferencesHelper.getChannel(getContext()), PreferencesHelper.getRegion(getContext()), PreferencesHelper.getTeam(getContext()), PreferencesHelper.getRole(getContext()), PreferencesHelper.getSegment(getContext()), PreferencesHelper.getFirstName(getContext()), PreferencesHelper.getLastName(getContext()), PreferencesHelper.getImedToken(getContext()), PreferencesHelper.getArea(getContext()));



        try{
            String segment = PreferencesHelper.getSegment(getContext());

            if (segment.equals("MFC-ZA")) {       //do check here for Retail mass....snap ap

                SnapappAuthenticationApiController.validate(getContext(), PreferencesHelper.getSalesCode(getContext()));
            } else {
                goToDestinationActivity(SalesActivity.class);
            }
        }catch ( Exception e){

        }



    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_INTERMEDIARY_DETAILS)) {
            progress.setVisibility(View.GONE);
        }
    }
}

