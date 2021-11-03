package com.cobi.puresurveyandroid.activity;

import android.Manifest;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.cobi.puresurveyandroid.BuildConfig;
import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.OnBoardingAdapter;
import com.cobi.puresurveyandroid.databinding.ActivityOnboardingBinding;
import com.cobi.puresurveyandroid.drawer.CustomDrawerAdapter;
import com.cobi.puresurveyandroid.drawer.DrawerItem;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.DeviceRequest;
import com.cobi.puresurveyandroid.model.CSIContactDetails;
import com.cobi.puresurveyandroid.model.OnboardingInfo;
import com.cobi.puresurveyandroid.model.RegisterResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.distribute.Distribute;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OnboardingActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_CURRENT_POSITION = "EXTRA_CURRENT_POSITION";
    private static final String EXTRA_ONBOARDING = "EXTRA_ONBOARDING";
    private static final String EXTRA_KEY = "EXTRA_KEY";

    private ActivityOnboardingBinding mBinding;
    private OnBoardingAdapter mPagerAdapter;
    private ArrayList<OnboardingInfo> mData;
    private int mCurrentPosition = 0;
    private String mOnboardingKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.WRITE_CALENDAR}, 0);
        }


        mData = new ArrayList<>();

        mData.add(new OnboardingInfo("keep_infomed","Track", "Set your personal target\n\n for the month and track your \n\nprogress throughout"));
        mData.add(new OnboardingInfo("engage_2","Engage", "See important customer\n\n  life events and engage with your\n\n  customers directly"));
        mData.add(new OnboardingInfo("old_mutual_17_05_2018_shot_01_1230_1_copy","Keep Informed", "See the summary of your campaign\n\n  leads, outstanding requirements\n\n  for new business sales and customers\n\n  with missed premiums, so that \n\n you can take action"));



        initView();
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPosition != 0) {
            mBinding.viewPager.setCurrentItem(--mCurrentPosition);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mData != null) {
            outState.putSerializable(EXTRA_ONBOARDING, mData);
        }
        outState.putInt(EXTRA_CURRENT_POSITION, mCurrentPosition);
        outState.putString(EXTRA_KEY, mOnboardingKey);
    }

    private void initView() {
        initToolbarMenu();
        initViewPager();
    }

    private void initViewPager() {
        mPagerAdapter = new OnBoardingAdapter(this, mData != null ? mData : new ArrayList<OnboardingInfo>());
        mBinding.viewPager.setAdapter(mPagerAdapter);
        mBinding.viewPager.setCurrentItem(mCurrentPosition);
        mBinding.viewPager.setSwipeable(true);
        mBinding.indicator.setupWithViewPager(mBinding.viewPager);
        mBinding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onGetStarted(View view) {
        PreferencesHelper.setShownOnboarding(this, Boolean.TRUE);

        Intent intent = new Intent(this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    protected void initToolbarMenu() {
        if (findViewById(R.id.menu_button) != null) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
                }
                else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        ListView mDrawerListView = findViewById(R.id.nav_view);

        ArrayList<DrawerItem> dataList = new ArrayList<>();
         //  dataList.add(new DrawerItem(getString(R.string.menu_dashboard), true, true, DrawerItem.DrawerType.TITLE));


    dataList.add(new DrawerItem(0,getString(R.string.help_title), true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(),R.drawable.ic_help_4_icon, null)));
        dataList.add(new DrawerItem(1,"OM T&C's", true, true, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(),R.drawable.ic_om_tc_icon, null)));

        if (mDrawerListView != null) {
            mDrawerListView.setAdapter(new CustomDrawerAdapter(this, R.layout.drawer_title_item, dataList));
            mDrawerListView.setOnItemClickListener(this);
        }

        ImageView mDrawerCloseButton = findViewById(R.id.nav_close);
        if (mDrawerCloseButton != null) {
            mDrawerCloseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.END);
                }
            });
        }
    }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: //Profile
                startActivity(new Intent(this,HelpActivity.class));
                break;
            case 1: //Dashboard
                startActivity(new Intent(this,TandCActivity.class));
                //gotoDashboard(true);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
    }

}
