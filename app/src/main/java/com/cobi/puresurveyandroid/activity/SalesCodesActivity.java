package com.cobi.puresurveyandroid.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.fragment.SalesCodesFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SalesCodesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_codes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolbarMenu();

        showFragment(R.id.fragment_container, new SalesCodesFragment());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();



        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, "Sales Codes Screen", this.getClass().getSimpleName());
        }
    }
}

