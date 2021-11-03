package com.cobi.puresurveyandroid.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.OnboardingPageBinding;
import com.cobi.puresurveyandroid.model.OnboardingInfo;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingAdapter extends PagerAdapter {

    private OnboardingPageBinding mBinding;
    private Context context;
    private List<OnboardingInfo> mInfoData;

    public OnBoardingAdapter(Context context, List<OnboardingInfo> dataObjectList) {
        this.context = context;
        this.mInfoData = dataObjectList;
    }

    @Override
    public int getCount() {
        return mInfoData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mBinding = DataBindingUtil.inflate(((Activity) context).getLayoutInflater(), R.layout.onboarding_page, null, false);
        mBinding.setInfo(mInfoData.get(position));
        mBinding.setShowButton(position == mInfoData.size() - 1);
        container.addView(mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addAllData(ArrayList<OnboardingInfo> fragments) {
        mInfoData.addAll(fragments);
    }

    public void addFragments(OnboardingInfo info) {
        mInfoData.add(info);
    }
}
