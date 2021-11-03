package com.cobi.puresurveyandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityMatrixSelectionBinding;
import com.cobi.puresurveyandroid.databinding.MatrixItemLayoutBinding;
import com.cobi.puresurveyandroid.drawer.CustomDrawerAdapter;
import com.cobi.puresurveyandroid.drawer.DrawerItem;
import com.cobi.puresurveyandroid.fragment.EventFragment;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.model.MatrixItem;
import com.cobi.puresurveyandroid.model.MatrixResponse;
import com.cobi.puresurveyandroid.model.MenuEnum;
import com.cobi.puresurveyandroid.model.RegisterResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.SnapappAuthenticationApiController;
import com.cobi.puresurveyandroid.webservice.controller.EventsApiController;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;
import com.cobi.puresurveyandroid.webservice.controller.ImedAuthenticationController;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;

/**
 * Created by admin on 2018/03/29.
 */

public class MatrixSelectionActivity extends BaseActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_ONBOARDING = "EXTRA_ONBOARDING";

    private ActivityMatrixSelectionBinding mBinding;
    private MatrixItemAdapter adapter;
    private boolean mBypassDefault;
    boolean mbyDefault;
    Bundle mbundle;//Boolean flag that will only be set if it has bypassed default before (Default State = False)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_matrix_selection);

        mBinding.setClientName(PreferencesHelper.getFirstName(this));

        if (mBinding.toolbarInclude != null) {
            setSupportActionBar(mBinding.toolbarInclude.toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (savedInstanceState != null) {
            mBypassDefault = savedInstanceState.getBoolean(EXTRA_BYPASS_DEFAULT, false);
        } else {
            if (getIntent().hasExtra(EXTRA_BYPASS_DEFAULT)) {
                mBypassDefault = getIntent().getBooleanExtra(EXTRA_BYPASS_DEFAULT, false);
            }
        }

        registerUser();

        initToolbarMenu();

        initView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        boolean hasMultipleSalesCodes = PreferencesHelper.hasMultipleSalesCodes(this);

        int menuItemId = dataList.get(position).id;

        if (menuItemId == MenuEnum.SWITCH_USER_POSITION.getNumVal()) {
            gotToSalesCodes();
        } else if (menuItemId == MenuEnum.HELP_POSITION.getNumVal()) {

            startActivity(new Intent(this, HelpActivity.class));
        } else if (menuItemId == MenuEnum.OMTC_POSITION.getNumVal()) {
            startActivity(new Intent(this, TandCActivity.class));
        } else if (menuItemId == MenuEnum.LOGOUT_POSITION.getNumVal()) {
            logout(true);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
    }


    protected void initToolbarMenu() {

        if (findViewById(R.id.menu_button) != null) {
            final DrawerLayout drawer = findViewById(R.id.drawer_layout);
            final ImageButton menuButton = findViewById(R.id.menu_button);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawer.isDrawerOpen(GravityCompat.END)) {
                        drawer.closeDrawer(GravityCompat.END);
                    } else {
                        drawer.openDrawer(GravityCompat.END);
                    }
                }
            });

            ListView mDrawerListView = findViewById(R.id.nav_view);

            dataList = new ArrayList<>();

            if (versionName != null) {
                dataList.add(new DrawerItem(MenuEnum.VERSION_POSITION.getNumVal(), "Version: " + versionName, false, false, DrawerItem.DrawerType.TITLE, null));
            }

            dataList.add(new DrawerItem(MenuEnum.PROFILE_POSITION.getNumVal(), !TextUtils.isEmpty(PreferencesHelper.getSalesCode(this)) ? PreferencesHelper.getSalesCode(this) : "N/A", false, false, DrawerItem.DrawerType.TITLE, null));
            dataList.add(new DrawerItem(MenuEnum.SEGMENT_POSITION.getNumVal(), !TextUtils.isEmpty(PreferencesHelper.getSegment(this)) ? PreferencesHelper.getSegment(this) : "N/A", false, false, DrawerItem.DrawerType.TITLE, null));

            boolean hasMultipleSalesCodes = PreferencesHelper.hasMultipleSalesCodes(this);
            if (hasMultipleSalesCodes) {
                dataList.add(new DrawerItem(MenuEnum.SWITCH_USER_POSITION.getNumVal(), getString(R.string.menu_switch_user), true, false, DrawerItem.DrawerType.TITLE, null));
            }

            //            if (!PreferencesHelper.getSegment(this).equals("MFC-ZA")) {
            //                String s = PreferencesHelper.getSegment(this);
            //                dataList.add(new DrawerItem("MY CPD POINTS", true, false, DrawerItem.DrawerType.TITLE, null));
            //            }

            dataList.add(new DrawerItem(MenuEnum.HELP_POSITION.getNumVal(), getString(R.string.help_title), true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_help_4_icon, null)));
            dataList.add(new DrawerItem(MenuEnum.OMTC_POSITION.getNumVal(), "OM T&C's", true, true, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_om_tc_icon, null)));

            //            dataList.add(new DrawerItem("SHARE THE APP", true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_share_icon, null)));

            dataList.add(new DrawerItem(MenuEnum.LOGOUT_POSITION.getNumVal(), getString(R.string.menu_logout), false, false, DrawerItem.DrawerType.BUTTON, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_logout_icon, null)));

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

    private void registerUser() {
        SnapappAuthenticationApiController.register(this, PreferencesHelper.getSalesCode(this));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_matrix:

                mBinding.home.setVisibility(View.VISIBLE);
                mBinding.fragmentContainer.setVisibility(View.GONE);

                break;
            case R.id.action_events:

                mBinding.home.setVisibility(View.GONE);
                showFragment(R.id.fragment_container, new EventFragment());

                mBinding.fragmentContainer.setVisibility(View.VISIBLE);


                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, getString(R.string.matrix_screen), null /* class override */);
        }
        retrieveMatrix();

        Date currentDate = new Date(System.currentTimeMillis());

        if (DateHelper.hasBeen2weeks(PreferencesHelper.getLoggedIn(this), currentDate)) {
            AlertDialogHelper.confirm("Please confirm your staff code", this);
        }
    }

    private void initView() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        mBinding.btnRetry.setOnClickListener(this);
//        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                retrieveMatrix();
//            }
//        });
    }

    private void showLoaders(boolean progress) {
//        mBinding.swipeContainer.setRefreshing(false);
        mBinding.progress.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetry) {
            mBinding.setVisible(false);
            showLoaders(true);
            retrieveMatrix();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_BYPASS_DEFAULT, mBypassDefault);
    }

    private void loadItems(final List<MatrixItem> items) {
        GridView gridView = findViewById(R.id.matrix);
        adapter = new MatrixItemAdapter(this, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items.get(position).getActive()) {
                    navigateByItem(items.get(position), false);
                }
            }
        });
    }

    private void navigateByItem(MatrixItem item, boolean byDefault) {
        mbundle = new Bundle();
        mbyDefault = byDefault;
        mbundle.putString(EXTRA_KEY, item.getTitle());
        if (item.getOnboardingList() != null && !item.getOnboardingList().isEmpty()) {
            mbundle.putSerializable(EXTRA_ONBOARDING, item.getOnboardingList());
        }
        switch (item.getType()) {
            //Only for Testing - Events onboarding
            case "events":
                break;
            case "sales":
                navigateTo(mbundle, SalesActivity.class, byDefault);
                break;
            case "inbox":
                navigateTo(mbundle, InboxActivity.class, byDefault);
                break;

            case "static":
                if (item.getTitle().equals("REMOTE SALES")) {

                    mbundle.putString(WebviewActivity.URL_EXTRA_KEY, "connect");

                    ImedAuthenticationController.getToken(this, "37c8980d-24cf-4bcc-a565-b4279f63992f", "MU0zZF9jM050cjRsLSR1dEguNVlzVDNtOlchdGgrIUQ9NDczOTYyMjFjMjZjNDEyNWJjN2JjMDUxZTkxMTY2ODA=",false);

                } else if (item.getTitle().equals("FM REMOTE SALES")) {
                    mbundle.putString(WebviewActivity.URL_EXTRA_KEY, "fmconnect");
                    ImedAuthenticationController.getToken(this, "37c8980d-24cf-4bcc-a565-b4279f63992f", "MU0zZF9jM050cjRsLSR1dEguNVlzVDNtOlchdGgrIUQ9NDczOTYyMjFjMjZjNDEyNWJjN2JjMDUxZTkxMTY2ODA=",false);
                } else if (item.getTitle().equals("HOW CAN WE HELPU?")) {
                    mbundle.putString(WebviewActivity.URL_EXTRA_KEY, "hcwh");
                    ImedAuthenticationController.getToken(this, IMED_APP_ID, IMED_API_KEY, false);

                } else {
                    mbundle.putString(WebviewActivity.URL_EXTRA_KEY, item.getUrl());
                    navigateTo(mbundle, WebviewActivity.class, byDefault);

                }
                break;

            case "web":


                mbundle.putString(WebviewActivity.URL_EXTRA_KEY, item.getUrl());
                navigateTo(mbundle, WebviewActivity.class, byDefault);


                break;
        }
    }

    private void retrieveMatrix() {
        if (PreferencesHelper.getUUUID(this) != null) {

            showLoaders(true);

            EventsApiController.retrieveMatrix(this, PreferencesHelper.getUUUID(this));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(MatrixResponse response) {
        showLoaders(false);

        if (response instanceof MatrixResponse) {
            if (response.getItems() != null && response.getItems().size() != 0) {
                mBinding.setVisible(false);
                boolean loadDefault = false;
                MatrixItem defaultItem = null;
                if (!mBypassDefault) {
                    for (MatrixItem item : response.getItems()) {
                        if (item.getLaunchDefault()) {
                            loadDefault = true;
                            defaultItem = item;
                            break;
                        }
                    }
                }

                //Check if it should load a default item or just populate view
                if (loadDefault) {
                    navigateByItem(defaultItem, true);
                } else {
                    loadItems(response.getItems());
                }
            } else {
                mBinding.setVisible(true);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.setVisible(false);
        showLoaders(false);
        showConnectionDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showLoaders(true);
                retrieveMatrix();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //                logout();
                logout(true);

            }
        });
    }

    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    protected void navigateTo(Bundle extras, Class destination, boolean forced) {
        Intent intent = new Intent(MatrixSelectionActivity.this, destination);
        intent.putExtras(extras);
        if (forced) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
    }

    private class MatrixItemAdapter extends BaseAdapter {
        private Context mContext;
        private List<MatrixItem> items;
        private MatrixItemLayoutBinding mItemBinding;

        public MatrixItemAdapter(Context c, List<MatrixItem> items) {
            mContext = c;
            this.items = items;
        }

        public int getCount() {
            return items.size();
        }

        public MatrixItem getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            mItemBinding = DataBindingUtil.inflate(((Activity) mContext).getLayoutInflater(), R.layout.matrix_item_layout, parent, false);
            mItemBinding.setItem(items.get(position));

            if (items.get(position).getTitle().equals("BROADCASTS")) {
                mItemBinding.badgeCount.setVisibility(View.GONE);
            } else {
                if (items.get(position).getBadgeCount() > 0) {
                    mItemBinding.badgeCount.setVisibility(View.VISIBLE);
                }
            }
            if (items.get(position).getBadgeCount() > 0) {
                mItemBinding.badgeCount.setVisibility(View.VISIBLE);
            }

            return mItemBinding.getRoot();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(RegisterResponse response) {

        if (response instanceof RegisterResponse) {

            if (response != null && response.getData() != null) {
                try {
                    PreferencesHelper.setUUID(this, response.getData().getGuid());
                    retrieveMatrix();
                } catch (Exception e) {
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponseToken(ImedTokenResponse response) {

        if (response instanceof ImedTokenResponse) {

            navigateTo(mbundle, WebviewActivity.class, mbyDefault);

        }
    }

}
