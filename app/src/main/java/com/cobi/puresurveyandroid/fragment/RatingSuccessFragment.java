package com.cobi.puresurveyandroid.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentRatingCapturedBinding;

public class RatingSuccessFragment extends BaseFragment {

    private FragmentRatingCapturedBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating_captured, container, false);

        mBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(RatingSuccessFragment.this).commit();
               getActivity().onBackPressed();

            }
        });

        return mBinding.getRoot();
    }

}
