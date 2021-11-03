package com.cobi.puresurveyandroid.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityLoginBinding;
import com.cobi.puresurveyandroid.drawer.CustomDrawerAdapter;
import com.cobi.puresurveyandroid.drawer.DrawerItem;
import com.cobi.puresurveyandroid.fragment.HelpFragment;
import com.cobi.puresurveyandroid.fragment.LoginFragment;
import com.cobi.puresurveyandroid.fragment.RootedDeviceFragment;
import com.cobi.puresurveyandroid.fragment.TermsConditionsFragment;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.DeviceRequest;
import com.cobi.puresurveyandroid.model.MenuEnum;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.scottyab.rootbeer.RootBeer;

import java.util.ArrayList;


public class LoginActivity extends BaseActivity {

    // REFACTORING REQUIRED FOR THE WHOLE OF THIS FILE

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.WRITE_CALENDAR}, 0);
        }


//        RootBeer rootBeer = new RootBeer(this);
//
//        if (rootBeer.isRootedWithoutBusyBoxCheck()) {
//
//            RootedDeviceFragment rootedDeviceFragment = new RootedDeviceFragment();
//
//            showFragment(R.id.fragment_container, rootedDeviceFragment);
//        } else {

            showFragment(R.id.fragment_container, new LoginFragment());
            initToolbarMenu();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1:
                replaceFragment(new HelpFragment());
                break;
            case 2:
                replaceFragment(new TermsConditionsFragment());
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
    }

    public void replaceFragment(Fragment fragment) {
        fragmentUtility.replaceFragmentSlide(this, fragment, R.id.fragment_container, true, fragment.getClass().getSimpleName());
    }

    @Override
    public void initToolbarMenu() {
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

            ArrayList<DrawerItem> dataList = new ArrayList<>();
            if (versionName != null) {
                dataList.add(new DrawerItem(MenuEnum.VERSION_POSITION.getNumVal(), "Version: " + versionName, false, false, DrawerItem.DrawerType.TITLE, null));
            }

            dataList.add(new DrawerItem(MenuEnum.HELP_POSITION.getNumVal(), getString(R.string.help_title), true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_help_4_icon, null)));
            dataList.add(new DrawerItem(MenuEnum.OMTC_POSITION.getNumVal(), "OM T&C's", true, true, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_om_tc_icon, null)));

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
    protected void onResume() {
        super.onResume();


        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, "Login Screen", this.getClass().getSimpleName());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}