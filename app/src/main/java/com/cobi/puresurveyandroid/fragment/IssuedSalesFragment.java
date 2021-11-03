package com.cobi.puresurveyandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentIssuedSalesBinding;
import com.cobi.puresurveyandroid.databinding.FragmentMyCpdPointsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.Commission;
import com.cobi.puresurveyandroid.model.CommissionData;
import com.cobi.puresurveyandroid.model.CommissionResponseAPI;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.MyCpdData;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.Constants;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.PlumlineApiController;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class IssuedSalesFragment extends BaseFragment {



    private FragmentIssuedSalesBinding mBinding;
    String issued_sales;
    private static String currency;
    public HashMap<String, String> salesKeyValue;
    Gson gson;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.CpdAccessed.getNumVal(), ""));

        // Inflate the layout for this fragment
        mBinding  =   DataBindingUtil.inflate(inflater, R.layout.fragment_issued_sales, container, false);

        CSIApiController.getSalesEarnings(getContext(), PreferencesHelper.getSalesCode(getContext()));

        salesKeyValue = new HashMap<>();

        gson = new Gson();

        if ((PreferencesHelper.getSerializedMap(getContext()) != null)) {
            String s = PreferencesHelper.getSerializedMap(getContext());
            salesKeyValue = gson.fromJson(s, salesKeyValue.getClass());
        }


        mBinding.progress.setVisibility(View.VISIBLE);


        return mBinding.getRoot();

    }


    public void setSalesEarningsData() {
        try {
            CommissionData data = new CommissionData();

            if (salesKeyValue.get(PreferencesHelper.getSalesCode(getContext())) != null) {
                data.setTarget(Integer.valueOf(salesKeyValue.get(PreferencesHelper.getSalesCode(getContext()))));
            } else {
                data.setTarget(50000);
            }

            DateFormat d = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            date = cal.getTime();

            PreferencesHelper.setUpdatedDate(getContext(), d.format(date));

            if (issued_sales != null) {
                data.setCurrent(Double.valueOf(issued_sales) / 100);
            } else {
                data.setCurrent(0D);
            }

            salesKeyValue.put(PreferencesHelper.getSalesCode(getContext()), data.getTarget().toString());
            mBinding.setData(data);

            mBinding.setUpdateDate(PreferencesHelper.getUpdatedDate(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setSalesData(Commission result) {

        if (result != null) {
            if (result.getMonthToDateIssuedSalesTotal() != null) {
                issued_sales = result.getMonthToDateIssuedSalesTotal().toString();
            }

            setSalesEarningsData();


            if (result.getCurrency() != null) {
                if (result.getCurrency().equals("ZAR") || result.getCurrency() == null) {
                    mBinding.setCurrency(Constants.ZAR);
                    currency = Constants.ZAR;
                }
                if (result.getCurrency().equals("NAD") || result.getCurrency() == null) {
                    mBinding.setCurrency(Constants.NAD);
                    currency = Constants.NAD;
                }
            } else {
                setCountry();
            }
        } else {
           setCountry();
        }

    }

    private void setCountry() {
        String country = PreferencesHelper.getCountryReg(getContext());

        if (country != null) {
            if (country.equals("NA")) {
                mBinding.setCurrency(Constants.NAD);
                currency = Constants.NAD;
            } else {
                mBinding.setCurrency(Constants.ZAR);
                currency = Constants.ZAR;
            }
        } else {
            mBinding.setCurrency(Constants.ZAR);
            currency = Constants.ZAR;
        }

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(CommissionResponseAPI response) {

        mBinding.progress.setVisibility(View.GONE);

        Commission result = response.getCommission();

        setSalesData(result);


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
        mBinding.progress.setVisibility(View.GONE);

    }


}
