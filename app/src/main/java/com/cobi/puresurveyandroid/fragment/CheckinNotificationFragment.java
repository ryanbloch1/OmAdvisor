package com.cobi.puresurveyandroid.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentCheckinBinding;

public class CheckinNotificationFragment extends BaseFragment {

    private FragmentCheckinBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_checkin, container, false);


        mBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showNestedFragment(new EventMainFragment());

            }
        });

        return mBinding.getRoot();
    }
}
