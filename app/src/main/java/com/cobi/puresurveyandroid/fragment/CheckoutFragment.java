package com.cobi.puresurveyandroid.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentCheckoutBinding;

public class CheckoutFragment extends BaseFragment {

    private FragmentCheckoutBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);


        mBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNestedFragment(new EventFragment());

            }
        });

        mBinding.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNestedFragment(new RateSessionFragment());

            }
        });

        return mBinding.getRoot();
    }
}
