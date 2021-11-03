package com.cobi.puresurveyandroid.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;

public class MenuFragment extends BaseFragment  {


    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.coming_soon, container, false);

        return view;
    }

//    private void initView() {
//
//        mBinding.layoutLeads.setOnClickListener(this);
//        mBinding.layoutPipeline.setOnClickListener(this);
//        mBinding.layoutLapses.setOnClickListener(this);
//        mBinding.layoutHelp.setOnClickListener(this);
//        mBinding.buttonLogout.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.layoutLeads:
//                showNestedFragment(new CampaignsFragment());
//                break;
//            case R.id.layoutPipeline:
//                showNestedFragment(new PipelineFragment());
//                break;
//            case R.id.layoutLapses:
//                showNestedFragment(new MissedPremiumsFragment());
//                break;
//            case R.id.layoutHelp:
//                showNestedFragment(new HelpFragment());
//                break;
//            case R.id.buttonLogout:
//                if (getActivity() != null) {
//                    ((BaseActivity) getActivity()).logout();
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
}
