package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.GalleryAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentGalleryBinding;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class GalleryFragment extends BaseFragment implements GalleryAdapter.ImageSelected {

    private FragmentGalleryBinding mBinding;
    private GalleryAdapter mAdapter;
    private SharedViewModelEventDetails sharedViewModelEventDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);

        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);

        if(sharedViewModelEventDetails.getMedia() !=null){

            populateGallery(sharedViewModelEventDetails.getMedia());
        }

        return mBinding.getRoot();
    }

    private void populateGallery(List<Medium> result){

        mAdapter = new GalleryAdapter(result, getContext(), this);
        mBinding.galleryGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mBinding.galleryGrid.setHasFixedSize(true);
        mBinding.galleryGrid.setAdapter(mAdapter);

    }

    @Override
    public void ImageSelected(View v, int position, String url) {

        sharedViewModelEventDetails.setSelectedImage(position);

        Activity activity = getActivity();
        if (activity != null) {

            String extension = url.substring(url.lastIndexOf("."));

            if (extension.equals(".pdf") || extension.equals(".mp4") || extension.equals(".mp3") || extension.equals(".pptx")) {

                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }catch(Exception e){
                    e.printStackTrace();
                }


            } else {
                addFragment(new FullImageFragment(), R.id.fragment_container);
            }
        }
    }
}
