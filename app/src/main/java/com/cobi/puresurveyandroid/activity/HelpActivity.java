package com.cobi.puresurveyandroid.activity;

import android.content.Intent;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityHelpBinding;
import com.cobi.puresurveyandroid.drawer.CustomDrawerAdapter;
import com.cobi.puresurveyandroid.drawer.DrawerItem;
import com.cobi.puresurveyandroid.fragment.HelpFragment;
import com.cobi.puresurveyandroid.model.MenuEnum;

import java.util.ArrayList;

public class HelpActivity extends BaseActivity {

    ActivityHelpBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_help);


        showBackButton(true);
        showFragment(R.id.fragment_container, new HelpFragment());

        initToolbarMenu();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void decideWhetherToShowBackButton(Fragment fragment) {
        showBackButton(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this,TandCActivity.class));
                break;

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
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


            dataList.add(new DrawerItem(MenuEnum.OMTC_POSITION.getNumVal(), "OM T&C's", true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(),R.drawable.ic_om_tc_icon, null)));

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
}
