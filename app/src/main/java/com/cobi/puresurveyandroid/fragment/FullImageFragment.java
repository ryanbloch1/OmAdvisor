package com.cobi.puresurveyandroid.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.ImageSliderAdapter;
import com.cobi.puresurveyandroid.databinding.FullImageBinding;
import com.cobi.puresurveyandroid.model.Birthday;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class FullImageFragment extends BaseFragment {

    FullImageBinding mBinding;
    private SharedViewModelEventDetails sharedViewModelEventDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.full_image, container, false);

        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);

        if (sharedViewModelEventDetails.getMedia() != null && !sharedViewModelEventDetails.getMedia().isEmpty()) {

            mBinding.imageSlider.setSliderAdapter(new ImageSliderAdapter(getContext(), sharedViewModelEventDetails.getMedia()));

            mBinding.imageSlider.setCurrentPagePosition(sharedViewModelEventDetails.getSelectedImage());
        }

        return mBinding.getRoot();
    }
}
