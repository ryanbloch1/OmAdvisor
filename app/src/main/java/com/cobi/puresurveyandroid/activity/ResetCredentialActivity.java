package com.cobi.puresurveyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cobi.puresurveyandroid.ErrorResponseResetCreds;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityResetCredentialBinding;
import com.cobi.puresurveyandroid.drawer.CustomDrawerAdapter;
import com.cobi.puresurveyandroid.drawer.DrawerItem;
import com.cobi.puresurveyandroid.model.BaseErrorResponse;
import com.cobi.puresurveyandroid.model.CountryCode;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.MenuEnum;
import com.cobi.puresurveyandroid.model.MessageSection;
import com.cobi.puresurveyandroid.model.ResetPasswordResponse;
import com.cobi.puresurveyandroid.model.SendUsernameResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.ResetCredentialsController;
import com.cobi.puresurveyandroid.webservice.service.ResetCredentialsService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import static com.cobi.puresurveyandroid.webservice.controller.ResetCredentialsController.GET_COUNTRY_CODES;
import static com.cobi.puresurveyandroid.webservice.controller.ResetCredentialsController.RESET_PASSWORD;
import static com.cobi.puresurveyandroid.webservice.controller.ResetCredentialsController.SEND_USERNAME;

public class ResetCredentialActivity extends BaseActivity {

    ActivityResetCredentialBinding mBinding;
    String credKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        credKey = intent.getStringExtra(CRED_KEY);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_credential);

        if (credKey != null && credKey.equals("username")) {
            mBinding.setIsUsernameRequest(true);
        } else {
            mBinding.setIsUsernameRequest(false);
        }

        mBinding.goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoDestinationActivity(LoginActivity.class);
            }
        });

        ResetCredentialsController.getPhoneCodes(this);

        initToolbarMenu();

        mBinding.countryCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!TextUtils.isEmpty(mBinding.cellNumber.getText().toString())){
                    mBinding.cellNumber.setError(null);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBinding.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });
    }

    private void initSpinner(final List<CountryCode> codes) {

        ArrayAdapter dataAdapter = new ArrayAdapter<CountryCode>(this, R.layout.spinner_item_layout, codes) {

            @Override
            public int getCount() {
                return codes.size();
            }

            @Override
            public CountryCode getItem(int position) {
                return codes.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            // And the "magic" goes here
            // This is for the "passive" state of the spinner
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
                TextView label = (TextView) super.getView(position, convertView, parent);
                //                label.setTextColor(Color.BLACK);
                // Then you can get the current item using the values array (Users array) and the current position
                // You can NOW reference each method you has created in your bean object (User class)
                label.setText(codes.get(position).getDomainDisplayValue() + " (" + codes.get(position).getRelatedDomainDisplayValue() + ")");

                // And finally return your dynamic (or custom) view for each spinner item
                return label;
            }

            // And here is when the "chooser" is popped up
            // Normally is the same view, but you can customize it if you want
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView label = (TextView) super.getDropDownView(position, convertView, parent);
                //                label.setTextColor(Color.BLACK);
                label.setText(codes.get(position).getDomainDisplayValue() + " (" + codes.get(position).getRelatedDomainDisplayValue() + ")");

                return label;
            }
        };

        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mBinding.countryCodeSpinner.setAdapter(dataAdapter);

        mBinding.countryCodeSpinner.setSelection(getSACodePos(codes));
    }

    private int getSACodePos(List<CountryCode> codes) {
        int pos = 0;

        for (int i = 0; i < codes.size(); i++) {

            CountryCode code = codes.get(i);

            if (code.getRelatedDomainDisplayValue().equals("+27")) {
                pos = i;
                break;
            }
        }

        return pos;
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

    private boolean checkCellnumber(String cellNumber, String code) {

        if (code.equals("+27")) {
            if (validateSouthAfricanCellNumber(cellNumber)) {
                //post
                return true;
            } else {
                mBinding.cellNumber.setError(Html.fromHtml(getString(R.string.invalid_number)).toString());
                return false;
            }
        } else {
            //else
            if (cellNumber.length() >= 2 && cellNumber.length() <= 15) {
                return true;
            } else {
                mBinding.cellNumber.setError(Html.fromHtml("Invalid cellphone number – must contain between 2 and 15 numerals only").toString());

                return false;
                //set error
            }
        }
    }

    private boolean validate() {
        String cellNumber = mBinding.cellNumber.getText().toString();
        String identifier = mBinding.identifier.getText().toString();

        CountryCode code = (CountryCode) mBinding.countryCodeSpinner.getSelectedItem();

        String countryCode = code.getRelatedDomainDisplayValue();

        if (!TextUtils.isEmpty(cellNumber) && !TextUtils.isEmpty(identifier)) {

            if (checkCellnumber(cellNumber, countryCode)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (TextUtils.isEmpty(identifier)) {
                mBinding.identifier.setError(credKey.equals("username") ? "Please enter your surname" : "Please enter your username or usernumber");
            }

            if (TextUtils.isEmpty(cellNumber)) {
                mBinding.cellNumber.setError("Please enter your cellphone number");
            }

            return false;
        }
    }

    private void sendRequest() {

        if (validate()) {

            CountryCode code = (CountryCode) mBinding.countryCodeSpinner.getSelectedItem();

            String countryCode = code.getRelatedDomainDisplayValue();

            //post
            if (credKey.equals("username")) {
                ResetCredentialsController.sendUsername(this, mBinding.identifier.getText().toString(), countryCode, mBinding.cellNumber.getText().toString(), "Mobile");
            } else {
                ResetCredentialsController.resetPassword(this, DateHelper.dateToISO8601(new Date()), DateHelper.dateToISO8601(new Date()), mBinding.identifier.getText().toString(), countryCode, mBinding.cellNumber.getText().toString());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(SendUsernameResponse response) {
        if (response instanceof SendUsernameResponse) {
            mBinding.credsSent.setVisibility(View.VISIBLE);

            setSuccessText(response.getErrorInfo().getMessage().get(0).getMessageSections());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(ResetPasswordResponse response) {

        mBinding.credsSent.setVisibility(View.VISIBLE);

        setSuccessText(response.getErrorInfo().getMessage().get(0).getMessageSections());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(List<CountryCode> response) {
        initSpinner(response);
    }

    private void setSuccessText(List<MessageSection> sections) {
        mBinding.successBlurb.setText(sections.get(sections.size() - 2).getSectionMessage());
        mBinding.successHeading.setText(sections.get(sections.size() - 1).getSectionMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponseResetCreds errorResponse) {
        //        mBinding.progress.setVisibility(View.GONE);
        if (errorResponse.getRequest().equals(SEND_USERNAME)) {

            String errorHtmlHeading = "<h2>" + errorResponse.getMessage().get(0).getMessageSections().get(0).getSectionMessage() + "</h2>";

            String errorHtmlBody = errorResponse.getMessage().get(0).getMessageSections().get(1) != null ? "<br><p>" + errorResponse.getMessage().get(0).getMessageSections().get(1).getSectionMessage() + "<br><br><a href=\"https://secure.oldmutual.co.za/account/forgotusername\">Provide more details to help us find your profile</a></p>" : "";

            AlertDialogHelper.showAlertDialogHtml(errorHtmlHeading + errorHtmlBody, this);


        }

        if (errorResponse.getRequest().equals(RESET_PASSWORD)) {
//            String error = "";
//
//            for (MessageSection section : errorResponse.getMessage().get(0).getMessageSections()) {
//
//                error += section.getSectionMessage() + "\n";
//            }

            String error = "<h3>We could not find a profile matching this combination.</h3><br><p>Please check that you have entered the details correctly.For help, please contact the Self-Service Support Centre on 0860 60 65 00,Mon-Fri 08:00-18:00, Sat 08:00-13:00 (Call +27 21 503 1710 from outside South Africa), or help-secure@oldmutual.com.</p>";
            AlertDialogHelper.showAlertDialogHtml(error, this);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(GET_COUNTRY_CODES)) {
            //show technical error thing

            mBinding.technicalError.setVisibility(View.VISIBLE);

            mBinding.goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }
}
