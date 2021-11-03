package com.cobi.puresurveyandroid.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cobi.puresurveyandroid.fragment.CustomerProfileFragment;
import com.cobi.puresurveyandroid.fragment.EventMainFragment;
import com.cobi.puresurveyandroid.fragment.ReintermediationFragment;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.StaffRequest;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.model.MatrixItem;
import com.cobi.puresurveyandroid.model.MatrixResponse;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.ImedAuthenticationController;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;
import com.cobi.puresurveyandroid.webservice.controller.PipelinesApiController;
import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.BasePagerAdapter;
import com.cobi.puresurveyandroid.databinding.ActivitySalesBinding;
import com.cobi.puresurveyandroid.fragment.CustomerContactFragment;
import com.cobi.puresurveyandroid.fragment.EventFragment;
//import com.cobi.puresurveyandroid.fragment.OmEventFragment;
import com.cobi.puresurveyandroid.fragment.CommissionFragment;
import com.cobi.puresurveyandroid.fragment.UserCustomerEventsFragment;
//import com.cobi.puresurveyandroid.fragment.NpsFragment;
import com.cobi.puresurveyandroid.fragment.ViewPagerFragment;
import com.cobi.puresurveyandroid.listener.OnBackPressedListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;

public class SalesActivity extends BaseActivity implements  ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener, CommissionFragment.OnSelectedListener {

    private static final String EXTRA_CURRENT_POSITION = "EXTRA_CURRENT_POSITION";
    private static final String EXTRA_FRAGMENT_SIZE = "EXTRA_FRAGMENT_SIZE";
    private static final String EXTRA_FRAGMENT_TAG = "EXTRA_FRAGMENT_TAG";
    protected OnBackPressedListener onBackPressedListener;
    private  ActivitySalesBinding mBinding;
    private  BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragments;
    private int mCurrentPosition = 0;
    private MenuItem prevMenuItem;
    private SharedViewModelEventDetails sharedViewModelEventDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sales);

        ImedAuthenticationController.getToken(this, IMED_APP_ID, IMED_API_KEY,false);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(EXTRA_CURRENT_POSITION);

            int fragmentSize = savedInstanceState.getInt(EXTRA_FRAGMENT_SIZE);
            mFragments = new ArrayList<>();
            FragmentManager fm = getSupportFragmentManager();
            for (int i = 0; i < fragmentSize; i++) {
                mFragments.add(fm.getFragment(savedInstanceState, EXTRA_FRAGMENT_TAG + i));
            }
        } else {
            mFragments = createFragments();
        }

        View activeLabel = mBinding.bottomNavigation.findViewById(com.google.android.material.R.id.largeLabel);
        if (activeLabel != null && activeLabel instanceof TextView) {
            ((TextView) activeLabel).setPadding(0, 0, 0, 0);
        }

        sharedViewModelEventDetails = ViewModelProviders.of(this).get(SharedViewModelEventDetails.class);

        initView();


//        if (!PreferencesHelper.hasShownOverlayNotifications(this)) {
//            getShowcase();
//            PreferencesHelper.hasShownOverlayNotifications(this, true);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, getString(R.string.sales_screen), this.getClass().getSimpleName());
        }

        if (getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            resetToDefaultState();
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();

//        if (fragments.size() > 5) {
//            mBinding.viewPager.setVisibility(View.GONE);
//            mBinding.fragmentContainer.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public void onBackPressed() {
        setCurrentPosition(mCurrentPosition);

        if(mCurrentPosition == 2){
            Fragment fragment = mFragments.get(2);
            if (fragment instanceof ViewPagerFragment) {
                ((ViewPagerFragment) fragment).onBecommingVisible(true);
            }
        }

        if (onBackPressedListener != null) {
            onBackPressedListener.performBack();
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != null) {
                resetToDefaultState();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void showFragment(int contentFrameId, Fragment replacingFragment) {
        mBinding.viewPager.setVisibility(View.GONE);
        mBinding.fragmentContainer.setVisibility(View.VISIBLE);
        super.showFragment(contentFrameId, replacingFragment);
    }

    @NonNull
    private ArrayList<Fragment> createFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(CustomerContactFragment.newInstance());
        fragments.add(UserCustomerEventsFragment.newInstance());
        fragments.add(ReintermediationFragment.newInstance());
        fragments.add(EventFragment.newInstance());
        fragments.add(CommissionFragment.newInstance());

        return fragments;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_CURRENT_POSITION, mCurrentPosition);
        outState.putInt(EXTRA_FRAGMENT_SIZE, mFragments != null ? mFragments.size() : 0);

        if (mFragments != null) {
            for (int i = 0; i < mFragments.size(); i++) {
                if (mFragments.get(i) != null && getSupportFragmentManager().findFragmentById(mFragments.get(i).getId()) != null) {
                    getSupportFragmentManager().putFragment(outState, EXTRA_FRAGMENT_TAG + i, mFragments.get(i));
                }
            }
        }
    }

    public void showBirthday() {
        mBinding.bottomNavigation.setSelectedItemId(R.id.action_birthdays);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            resetToDefaultState();
            EventBus.getDefault().unregister(currentFragment);
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mBinding.viewPager.setVisibility(View.VISIBLE);
            mBinding.fragmentContainer.setVisibility(View.GONE);
        }

        int fragmentIndex = -1;

        Fragment oldFragment = mFragments.get(mCurrentPosition);

        switch (item.getItemId()) {
            case R.id.action_my_customer:
                mBinding.viewPager.setCurrentItem(0);
                fragmentIndex = 0;
                break;
            case R.id.action_birthdays:
                mBinding.viewPager.setCurrentItem(1);
                fragmentIndex = 1;
                break;
            case R.id.action_reinter:
                mBinding.viewPager.setCurrentItem(2);
                fragmentIndex = 2;
                break;
            case R.id.action_events:
                mBinding.viewPager.setCurrentItem(3);
                fragmentIndex = 3;
                break;
            case R.id.action_commission:

//                AlertDialogHelper.showNewFeatureOverlay("Issued Sales", "New feature now includes issued \n" + "sales reporting.", this);

                mBinding.viewPager.setCurrentItem(4);
                fragmentIndex = 4;
                break;
            default:
                return false;
        }

        mCurrentPosition = fragmentIndex;

        if (oldFragment instanceof ViewPagerFragment) {
            ((ViewPagerFragment) oldFragment).onBecommingVisible(false);
        }
        Fragment fragment = mFragments.get(fragmentIndex);
        if (fragment instanceof ViewPagerFragment) {
            ((ViewPagerFragment) fragment).onBecommingVisible(true);
        }

        return true;
    }

    private BubbleShowCase getShowcase() {

        return new BubbleShowCaseBuilder(this) //Activity instance
                .title(String.valueOf(Html.fromHtml("<b>New:  Sales and Support Notifications added</b> <br><br>Control in your hands to select the ones you would like to see!")))
                .targetView((View) findViewById(R.id.menu_button)) //View to point out
                .backgroundColor(ResourceHelper.getColour(R.color.colorAccent))
                .arrowPosition(BubbleShowCase.ArrowPosition.RIGHT)
                .show();


    }


    public void initView() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        if (PreferencesHelper.getSalesCode(this) == null) {
            mBinding.bottomNavigation.getMenu().getItem(0).setEnabled(false);
            mBinding.bottomNavigation.getMenu().getItem(1).setEnabled(false);
            mBinding.bottomNavigation.getMenu().getItem(3).setEnabled(true);
            mBinding.bottomNavigation.getMenu().getItem(3).setChecked(true);
            mBinding.bottomNavigation.getMenu().getItem(2).setEnabled(false);
        }

        initViewPager();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mBinding.includeToolbar.toolbar);
        initToolbarMenu();
    }

    private void initViewPager() {
        mPagerAdapter = new BasePagerAdapter(mFragments, getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mPagerAdapter);
        mBinding.viewPager.addOnPageChangeListener(this);
        mBinding.viewPager.setOffscreenPageLimit(mFragments.size());

        if (PreferencesHelper.getSalesCode(this) == null) {

            mBinding.viewPager.setCurrentItem(3);
        } else {
            mBinding.viewPager.setCurrentItem(mCurrentPosition);
        }

        mBinding.viewPager.setSwipeable(false);

        if (mFragments.get(0) instanceof ViewPagerFragment) {
            ((ViewPagerFragment) mFragments.get(0)).onBecommingVisible(true);
            decideWhetherToShowBackButton(mFragments.get(0));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        setCurrentPosition(mCurrentPosition);
    }



    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onErrorResponse(ErrorResponse errorResponse) {
//        if (errorResponse.getRequest().equals(ImedAuthenticationController.POST_STAFF)) {
//            Toast.makeText(this, errorResponse.getMessage()  +  " ANd status :" + errorResponse.getStatus(), Toast.LENGTH_LONG).show();
//
//
//
//        }
//
//    }


    private void resetToDefaultState() {
        //Remove fragment from container
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (f instanceof EventMainFragment) {
                sharedViewModelEventDetails.setEventDetailsTabPosition(0);
            }

            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
            mBinding.viewPager.setVisibility(View.VISIBLE);
            mBinding.fragmentContainer.setVisibility(View.GONE);
            if (mFragments.size() > mCurrentPosition) {
                decideWhetherToShowBackButton(mFragments.get(mCurrentPosition));
            }
        } else {
            getSupportFragmentManager().popBackStack();
            decideWhetherToShowBackButton(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        }
    }



    @Override
    public void onSelected(SalesActivity.Selected position) {
        setCurrentPosition(position.val());
    }

    private void setCurrentPosition(int position) {

        prevMenuItem = mBinding.bottomNavigation.getMenu().getItem(position);
    }

//    @Override
//    public void reload() {
//
//        Fragment fragment = mFragments.get(2);
//        if (fragment instanceof ViewPagerFragment) {
//            ((ViewPagerFragment) fragment).onBecommingVisible(true);
//        }
//    }


    public enum Selected {
        MY_CUSTOMER(0), LEAD(1), RE_INTER(2), MENU(3), COMMISSION(4);

        private int val;

        Selected(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }



}