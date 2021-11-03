package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.activity.SalesActivity;
import com.cobi.puresurveyandroid.databinding.FragmentCommissionBinding;
import com.cobi.puresurveyandroid.listener.OnBackPressedListener;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PageTypes;
import com.cobi.puresurveyandroid.model.Commission;
import com.cobi.puresurveyandroid.model.CommissionData;
import com.cobi.puresurveyandroid.model.CommissionResponse;
import com.cobi.puresurveyandroid.model.CommissionResponseAPI;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.Constants;
import com.cobi.puresurveyandroid.util.FBRemoteConfigHelper;
import com.cobi.puresurveyandroid.util.NumberFormatter;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.util.ViewHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CommissionFragment extends ViewPagerFragment implements View.OnClickListener, TextWatcher, OnBackPressedListener {
    private FragmentCommissionBinding mBinding;

    private static String currency;
    String month_to_date;
    public HashMap<String, String> salesKeyValue;
    Gson gson;
    ClientViewModel clientViewModel;

    public CommissionFragment() {
        // Required empty public constructor
    }

    public static CommissionFragment newInstance() {
        return new CommissionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_commission, container, false);


        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(getContext());
            instanceFA.setCurrentScreen(getActivity(), "Commission Screen", this.getClass().getSimpleName());
        }

        gson = new Gson();

        salesKeyValue = new HashMap<>();

        if ((PreferencesHelper.getSerializedMap(getContext()) != null)) {
            String s = PreferencesHelper.getSerializedMap(getContext());
            salesKeyValue = gson.fromJson(s, salesKeyValue.getClass());
        }

        initView();

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        mBinding.progress.setVisibility(View.VISIBLE);

        if (clientViewModel.getCurrentCommission() != null) {
            setSalesData(clientViewModel.getCurrentCommission().getValue());
        }


        return mBinding.getRoot();
    }

    @Override
    public void performBack() {
        if (mBinding.layoutSelection.getVisibility() == View.VISIBLE) {
            removeInputContainer();
            mBinding.layoutSelection.setVisibility(View.GONE);
        } else {
            if (getActivity() != null && getActivity() instanceof SalesActivity) {
                ((SalesActivity) getActivity()).setOnBackPressedListener(null);
                getActivity().onBackPressed();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterEventBus();
        Activity activity = getActivity();
        if (activity instanceof SalesActivity) {
            ((SalesActivity) getActivity()).setOnBackPressedListener(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
        Activity activity = getActivity();
        if (activity instanceof SalesActivity) {
            ((SalesActivity) getActivity()).setOnBackPressedListener(this);
        }
    }

    @Override
    public void onBecommingVisible(boolean visible) {
        super.onBecommingVisible(visible);
        if (visible) {

            AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.SalesEarningsAccessed.getNumVal(), ""));

            Context context = getContext();
            if (context != null) {
                CSIApiController.getSalesEarnings(getContext(), PreferencesHelper.getSalesCode(getContext()));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Activity activity = getActivity();
        if (!(activity instanceof OnSelectedListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement OnSelectedListener");
        }
        switch (v.getId()) {
            case R.id.targetValue:
                mBinding.contentInput.setMinValue(NumberFormatter.formatCurrencyNumber(90, currency));
                mBinding.contentInput.setMaxValue(NumberFormatter.formatCurrencyNumber(900000, currency));
                mBinding.layoutSelection.setVisibility(View.VISIBLE);
                setupInputContainer();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                break;
            case R.id.leadsMenuButton:
                ((OnSelectedListener) activity).onSelected(SalesActivity.Selected.MENU);
                showNestedFragment(new CampaignsFragment());
                break;
            case R.id.pipelineMenuButton:
                ((OnSelectedListener) activity).onSelected(SalesActivity.Selected.MENU);
                showNestedFragment(new PipelineFragment());
                break;
            case R.id.lapsesMenuButton:
                ((OnSelectedListener) activity).onSelected(SalesActivity.Selected.MENU);
                showNestedFragment(new MissedPremiumsFragment());
                break;
            case R.id.issued_sales:
                ((OnSelectedListener) activity).onSelected(SalesActivity.Selected.MENU);
                showNestedFragment(new IssuedSalesFragment());
                break;
            case R.id.btn_close:
                removeInputContainer();
                mBinding.layoutSelection.setVisibility(View.GONE);
                break;
            case R.id.btn_save:
                saveNewTarget();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mBinding.contentInput.targetInput.removeTextChangedListener(this);
        if (!TextUtils.isEmpty(s)) {
            String value = getStringInputValue();
            if (!TextUtils.isEmpty(value)) {
                try {
                    mBinding.contentInput.targetInput.setText(NumberFormatter.formatCurrencyNumberNoDecimals(Integer.valueOf(value), currency));
                    if (validateInput(Integer.valueOf(value))) {
                        mBinding.contentInput.inputTargetContainer.setBackground(ResourceHelper.getDrawable(R.drawable.rounded_focus_background));
                        mBinding.contentInput.btnSave.setTextColor(ResourceHelper.getColour(R.color.white));
                        ViewHelper.setTextViewDrawableColor(mBinding.contentInput.btnSave, R.color.white);
                        mBinding.contentInput.btnSave.setOnClickListener(this);
                        mBinding.contentInput.setErrorText(null);
                    } else {
                        mBinding.contentInput.inputTargetContainer.setBackground(ResourceHelper.getDrawable(R.drawable.rounded_input_error_background));
                        mBinding.contentInput.setErrorText(getString(R.string.input_error_validation, mBinding.contentInput.getMinValue(), mBinding.contentInput.getMaxValue()));
                        mBinding.contentInput.btnSave.setTextColor(ResourceHelper.getColour(R.color.disabledTextColour));
                        ViewHelper.setTextViewDrawableColor(mBinding.contentInput.btnSave, R.color.disabledTextColour);
                    }
                } catch (NumberFormatException e) {
                    mBinding.contentInput.btnSave.setOnClickListener(null);
                    Toast.makeText(getContext(), getString(R.string.invalid_number_exception), Toast.LENGTH_SHORT).show();
                }
            }
        }
        mBinding.contentInput.targetInput.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (currency != null && !s.toString().startsWith(currency)) {
            mBinding.contentInput.targetInput.setText(currency + s);
        }
        Selection.setSelection(mBinding.contentInput.targetInput.getText(), mBinding.contentInput.targetInput.getText().length());
        if (currency != null) {
            mBinding.contentInput.btnSave.setEnabled(mBinding.contentInput.targetInput.getText().length() > currency.length());
        } else {
            mBinding.contentInput.btnSave.setEnabled(mBinding.contentInput.targetInput.getText().length() > 0);
        }
    }

    private void initView() {
        mBinding.targetValue.setOnClickListener(this);
        mBinding.leadsMenuButton.setOnClickListener(this);
        mBinding.pipelineMenuButton.setOnClickListener(this);
        mBinding.lapsesMenuButton.setOnClickListener(this);
        mBinding.issuedSales.setOnClickListener(this);
        mBinding.contentInput.targetInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mBinding.contentInput.targetHint.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
                mBinding.contentInput.inputTargetContainer.setBackground(ResourceHelper.getDrawable(hasFocus ? R.drawable.rounded_focus_background : R.drawable.rounded_input_background));
            }
        });
    }

    private void setCommissionData() {

        String value = getStringInputValue();
        String defaultValue = "";

        if (TextUtils.isEmpty(defaultValue)) {
            defaultValue = "50000";
        }

        Integer commAmount = Integer.valueOf(PreferencesHelper.getCommissionAmount(getContext()));

        if (commAmount == null) {
            PreferencesHelper.setCommissionAmount(getContext(), Integer.valueOf(defaultValue));
        } else if (commAmount == null && TextUtils.isEmpty(value)) {
            PreferencesHelper.setCommissionAmount(getContext(), Integer.valueOf(commAmount));
        } else if (TextUtils.isEmpty(value)) {
            PreferencesHelper.setCommissionAmount(getContext(), Integer.valueOf(defaultValue));
        } else {
            PreferencesHelper.setCommissionAmount(getContext(), Integer.valueOf(value));
        }
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

            if (month_to_date != null) {
                data.setCurrent(Double.valueOf(month_to_date) / 100);
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

    private boolean validateInput(Integer value) {
        return (value >= Integer.valueOf(FBRemoteConfigHelper.getSCTargetMinimum()) && value <= Integer.valueOf(FBRemoteConfigHelper.getSCTargetMaximum()));
    }

    private void setupInputContainer() {
        mBinding.contentInput.btnSave.setOnClickListener(this);
        mBinding.contentInput.btnClose.setOnClickListener(this);
        mBinding.contentInput.targetInput.addTextChangedListener(this);
        mBinding.contentInput.targetInput.requestFocus();
        mBinding.contentInput.inputTargetContainer.setBackground(ResourceHelper.getDrawable(R.drawable.rounded_focus_background));
    }

    private void removeInputContainer() {
        ((BaseActivity) getActivity()).hideKeyboard();
        mBinding.contentInput.setErrorText(null);

        mBinding.contentInput.targetInput.clearFocus();
        mBinding.contentInput.btnSave.setOnClickListener(null);
        mBinding.contentInput.btnClose.setOnClickListener(null);
        mBinding.contentInput.targetInput.removeTextChangedListener(this);
    }

    private void saveNewTarget() {

        try {
            ((BaseActivity) getActivity()).hideKeyboard();
            String value = getStringInputValue();

            if (!TextUtils.isEmpty(value)) {

                if (validateInput(Integer.valueOf(value))) {

                    CommissionData data = new CommissionData();

                    if (month_to_date != null) {
                        data.setCurrent(Double.valueOf(Integer.valueOf(month_to_date) / 100));
                    }
                    data.setTarget(Integer.valueOf(value));
                    mBinding.setData(data);

                    setCommissionData();

                    salesKeyValue.put(PreferencesHelper.getSalesCode(getContext()), value);

                    String serializedMap = gson.toJson(salesKeyValue);

                    PreferencesHelper.setSerializedMap(getContext(), serializedMap);

                    mBinding.layoutSelection.setVisibility(View.GONE);
                    removeInputContainer();


                    AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.SalesPersonalTargetEdit.getNumVal(), ""));

                } else {
                    mBinding.contentInput.setErrorText(getString(R.string.input_error_validation, mBinding.contentInput.getMinValue(), mBinding.contentInput.getMaxValue()));
                }
            } else {
                mBinding.progress.setVisibility(View.VISIBLE);

                mBinding.layoutSelection.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(":::::::::::::::::", e.toString());
        }
    }

    private String getStringInputValue() {
        String value = "";
        if (currency != null && mBinding.contentInput.targetInput.getText().toString().contains(currency)) {
            value = mBinding.contentInput.targetInput.getText().toString().substring(currency.length(), mBinding.contentInput.targetInput.getText().length());
        } else {
            value = mBinding.contentInput.targetInput.getText().toString();
        }
        value = value.replace(" ", "");
        return value;
    }

    public interface OnSelectedListener {
        void onSelected(SalesActivity.Selected position);
    }

    public void setSalesData(Commission result) {

        if (result != null) {
            if (result.getMonthtoDateTotal() != null) {
                month_to_date = result.getMonthtoDateTotal().toString();
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

        if (result.getPreviousMonthTotal() != null && currency != null) {

            mBinding.previousMonth.setText(NumberFormatter.formatCurrencyNumber(result.getPreviousMonthTotal() / 100, currency));
        } else if (result.getPreviousMonthTotal() == null && currency != null) {
            mBinding.previousMonth.setText(NumberFormatter.formatCurrencyNumber(0, currency));
        } else {
            mBinding.previousMonth.setText("");
        }

        mBinding.progress.setVisibility(View.GONE);
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
        Commission result = response.getCommission();

        clientViewModel.setCurrentCommission(result);

        setSalesData(result);

        if (response.getCommission().getMessage() != null && !result.getCode().equals("400")) {
            AlertDialogHelper.showAlertDialog(response.getCommission().getMessage(), getContext());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_SALES_EARNINGS)) {
            mBinding.progress.setVisibility(View.GONE);

            AlertDialogHelper.showAlertDialog(errorResponse.getMessage(), getContext());
        }
    }
}