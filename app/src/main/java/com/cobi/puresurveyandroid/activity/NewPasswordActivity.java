package com.cobi.puresurveyandroid.activity;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityNewPasswordBinding;
import com.cobi.puresurveyandroid.drawer.CustomDrawerAdapter;
import com.cobi.puresurveyandroid.drawer.DrawerItem;
import com.cobi.puresurveyandroid.model.ChangePasswordResponse;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.MenuEnum;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.PasswordValidator;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.ResetCredentialsController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.cobi.puresurveyandroid.webservice.controller.CSIApiController.CUSTOMER_INTERMEDIARY_DETAILS;
import static com.cobi.puresurveyandroid.webservice.controller.ResetCredentialsController.UPDATE_PASSWORD;

public class NewPasswordActivity extends BaseActivity {

    ActivityNewPasswordBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_password);

        initToolbarMenu();

        mBinding.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1: //Profile
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case 2: //Dashboard
                startActivity(new Intent(this, TandCActivity.class));
                //gotoDashboard(true);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
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

    public boolean validate() {
        String newP = mBinding.newPassword.getText().toString().trim();
        String confirm = mBinding.confirmPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(newP) && !TextUtils.isEmpty(confirm)) {

            if (new PasswordValidator().validate(newP)) {
                if (confirm.equals(newP)) {
                    return true;
                } else {
                    mBinding.confirmPassword.setError("Passwords must match");
                    return false;
                }
            } else {
                mBinding.newPassword.setError("Invalid password - must follow password rules listed above");
                return false;
            }
        } else {
            if (newP.isEmpty()) {
                mBinding.newPassword.setError("Please enter a new password, following the rules listed above");
            }
            else if((!new PasswordValidator().validate(newP))){
                mBinding.newPassword.setError("Invalid password - must follow password rules listed above");
            }

            if (confirm.isEmpty() && newP.isEmpty()) {
                mBinding.confirmPassword.setError("Please confirm your new password");
            }

            return false;
        }
    }

    public void sendRequest() {
        if (validate()) {
            ResetCredentialsController.updatePassword(this, PreferencesHelper.getAccessToken(this), PreferencesHelper.getCommonName(this), mBinding.newPassword.getText().toString());
        }
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(ChangePasswordResponse response) {

        if(response.getStatus().equals("Success")){
            mBinding.credsSent.setVisibility(View.VISIBLE);


            mBinding.proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // go to sales activity or matrix depending on segment...

                    CSIApiController.getIntermediaryDetails(NewPasswordActivity.this, PreferencesHelper.getAccessToken(NewPasswordActivity.this), PreferencesHelper.getCommonName(NewPasswordActivity.this));

                }
            });
        }

        if(response.equals("Error")){
            AlertDialogHelper.showAlertDialog(response.getErrorMessage(), this);
        }
    }



    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(IMEDDetails response) {

        if(response instanceof  IMEDDetails){
            navigateToActivity(response);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(UPDATE_PASSWORD)) {
            //show technical error thing

            mBinding.technicalError.setVisibility(View.VISIBLE);

            mBinding.goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        if(errorResponse.getRequest().equals(CUSTOMER_INTERMEDIARY_DETAILS)){
            gotoDestinationActivity(SalesActivity.class);
        }
    }

}
