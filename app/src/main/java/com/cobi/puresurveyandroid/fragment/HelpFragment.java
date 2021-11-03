package com.cobi.puresurveyandroid.fragment;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentHelpBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;

public class HelpFragment extends BaseFragment implements View.OnClickListener  {

//    private static final String TAG = HelpFragment.class.getSimpleName();
//    private static final String EXTRA_HELP_INFO = "HELP_INFO";

    private FragmentHelpBinding mBinding;
//    private HelpAdapter mAdapter;
//    private ArrayList<Help> mHelps;
//
//    public HelpFragment() {
//        // Required empty public constructor
//    }

    public static HelpFragment newInstance(){
        return  new HelpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.HelpPageAccessed.getNumVal(), ""));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false);

        mBinding.email.setOnClickListener(this);
        mBinding.number.setOnClickListener(this);
        mBinding.imageViewEmail.setOnClickListener(this);
        mBinding.imageViewPhone.setOnClickListener(this);

        mBinding.emailNam.setOnClickListener(this);
        mBinding.numberNam.setOnClickListener(this);
        mBinding.imageViewEmailNam.setOnClickListener(this);
        mBinding.imageViewPhoneNam.setOnClickListener(this);

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.number:
                makePhoneCall(mBinding.number.getText().toString(), ActionTypes.HelpPagePhone);
                break;
            case R.id.email:
                sendEmail(mBinding.email.getText().toString(),false, ActionTypes.HelpPageEmail);
                break;
            case R.id.numberNam:
                makePhoneCall(mBinding.numberNam.getText().toString(), ActionTypes.HelpPagePhone);
                break;
            case R.id.emailNam:
                sendEmail(mBinding.emailNam.getText().toString(),false, ActionTypes.HelpPageEmail);
                break;
            case R.id.imageViewPhone:
                makePhoneCall(mBinding.number.getText().toString(), ActionTypes.HelpPagePhone);
                break;
            case R.id.imageViewEmail:
                sendEmail(mBinding.email.getText().toString(),false,ActionTypes.HelpPageEmail);
                break;
            case R.id.imageViewPhoneNam:
                makePhoneCall(mBinding.numberNam.getText().toString(), ActionTypes.HelpPagePhone);
                break;
            case R.id.imageViewEmailNam:
                sendEmail(mBinding.emailNam.getText().toString(),false,ActionTypes.HelpPageEmail);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
        catch (Exception e){

        }
    }

//    private void retrieveHelpInfo() {
//        SalesApiController.getHelpInfo(getActivity());
////        mBinding.progress.setVisibility(View.VISIBLE);
//    }

//    private void displayHelpInfo(ArrayList<Help> mHelps) {
//
//        mBinding.layoutHelp.setVisibility(View.VISIBLE);
//        mAdapter = new HelpAdapter(getActivity(), mHelps, this);
//        mBinding.contentRecyclerView.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        mBinding.contentRecyclerView.recyclerView.setHasFixedSize(true);
//        mBinding.contentRecyclerView.recyclerView.setAdapter(mAdapter);
//
//        mAdapter.notifyDataSetChanged();
//        mAdapter.setClickHandler(this);
//    }

//    @Override
//    public void onClick(Help help) {
//        makePhoneCall(help.getNumber());
//    }
//
//    public void onPause() {
//        super.onPause();
//        unregisterEventBus();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        registerEventBus();
//        retrieveHelpInfo();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(EXTRA_HELP_INFO, mHelps);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onSuccessResponse(HelpResponse response) {
//        Log.d(TAG, "onSuccessResponse: " + response.getMessage());
//
//        mBinding.progress.setVisibility(View.GONE);
//        mHelps = response.getItems();
//        if (mHelps != null && mHelps.size() != 0) {
//            displayHelpInfo(mHelps);
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onErrorResponse(ErrorResponse errorResponse) {
//        Log.d(TAG, "onErrorResponse: " + errorResponse.getMessage());
//        mBinding.progress.setVisibility(View.GONE);
//        if (getActivity() != null) {
//            ((BaseActivity) getActivity()).showConnectionDialog(new DialogD.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    retrieveHelpInfo();
//                }
//            });
//        }
//    }
}