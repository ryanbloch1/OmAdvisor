package com.cobi.puresurveyandroid.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentNpsBinding;

public class NpsFragment extends Fragment {

    public FragmentNpsBinding mBinding;

    public NpsFragment() {
        // Required empty public constructor
    }

    public static NpsFragment newInstance() {
        return new NpsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate( R.layout.coming_soon, container, false);
        return  view;
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}