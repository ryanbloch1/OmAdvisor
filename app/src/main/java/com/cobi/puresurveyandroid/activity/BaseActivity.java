package com.cobi.puresurveyandroid.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.cobi.puresurveyandroid.BuildConfig;
import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.PureSurveyFirebaseMessagingService;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.drawer.CustomDrawerAdapter;
import com.cobi.puresurveyandroid.drawer.DrawerItem;
import com.cobi.puresurveyandroid.fragment.DeferredFragmentTransaction;
import com.cobi.puresurveyandroid.fragment.HelpFragment;
import com.cobi.puresurveyandroid.fragment.LogIssueAppFragment;
import com.cobi.puresurveyandroid.fragment.LogIssueFragment;
import com.cobi.puresurveyandroid.fragment.LoginFragment;
import com.cobi.puresurveyandroid.fragment.MyCpdPointsFragment;
import com.cobi.puresurveyandroid.fragment.NotificationManagerFragment;
import com.cobi.puresurveyandroid.fragment.RateAppFragment;
import com.cobi.puresurveyandroid.fragment.SalesCodesFragment;
import com.cobi.puresurveyandroid.fragment.TermsConditionsFragment;
import com.cobi.puresurveyandroid.fragment.ViewPagerFragment;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.DeviceRequest;
import com.cobi.puresurveyandroid.model.Birthday;
import com.cobi.puresurveyandroid.model.ChannelRole;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.model.MenuEnum;
import com.cobi.puresurveyandroid.model.UserCustomerEvent;
import com.cobi.puresurveyandroid.model.UserCustomerEvent_Table;
import com.cobi.puresurveyandroid.model.UserCustomerEventsResponse;
import com.cobi.puresurveyandroid.model.ValidateResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.FragmentUtility;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.ImedAuthenticationController;
import com.cobi.puresurveyandroid.webservice.controller.NotificationManagerController;
import com.cobi.puresurveyandroid.webservice.controller.SnapappAuthenticationApiController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.EventsApiController;
import org.xms.f.analytics.ExtensionAnalytics;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMEI;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMSI;
import static com.cobi.puresurveyandroid.PureSurveyApplication.MANUFACTURER;
import static com.cobi.puresurveyandroid.PureSurveyApplication.MODEL;


/**
 * Created by admin on 2017/10/09.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    protected static final String EXTRA_KEY = "EXTRA_KEY";
    protected static final String EXTRA_ONBOARDING = "EXTRA_ONBOARDING";
    protected static final String EXTRA_BYPASS_DEFAULT = "EXTRA_BYPASS_DEFAULT";
    public static final String CRED_KEY = "key";
    public String[] numberPrefixes = {"060", "061", "062", "063", "064", "065", "066", "070", "071", "072", "073", "074", "076", "077", "078", "079", "081", "082", "083", "084", "085", "086"};
    public static final int REQUESTING_PHONE_STATE = 1987;
    public AlertDialog dialog;
    private android.app.AlertDialog pDialog;
    protected Toolbar mToolbar;
    private BroadcastReceiver mPushNotificationReceiver;
    public FragmentUtility fragmentUtility;
    protected ExtensionAnalytics mFirebaseAnalytics;
    public static List<Birthday> b;
    public static Handler UIHandler;
    public String versionName;
    public ArrayList<DrawerItem> dataList;

    private String messageIntentTitle = "";

    private Intent messageIntent;

    private boolean isRunning;
    private Queue<DeferredFragmentTransaction> mDeferredFragmentTransactions = new ArrayDeque<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (!BuildConfig.IS_STAGING) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }


//        checkPhoneDetails();

        UIHandler = new Handler(Looper.getMainLooper());

        mFirebaseAnalytics = ExtensionAnalytics.getInstance(this);

//        Fabric.with(this, new Crashlytics());

        fragmentUtility = new FragmentUtility();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        while (!mDeferredFragmentTransactions.isEmpty()) {
            mDeferredFragmentTransactions.remove().commit();
        }
    }

    public boolean validateSouthAfricanCellNumber(String number) {

        if (!TextUtils.isEmpty(number) && number.length() == 10) {

            String prefix = number.substring(0, 3);

            boolean prefixMatch = false;

            for (String p : numberPrefixes) {
                if (prefix.equals(p)) {
                    prefixMatch = true;
                    continue;
                }
            }
            return prefixMatch;
        } else {
            return false;
        }
    }

    public void showProgressDialog(Context context) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.progress_layout, null, false);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setView(dialogView);

        pDialog = builder.create();
        pDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        unregisterEventBus();
        unregisterPushNotificationReceiver();

        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    protected void updateBadgeCount() {
        if (ShortcutBadger.isBadgeCounterSupported(this)) {
            int unreadEvents = SQLite.select().from(UserCustomerEvent.class).where(UserCustomerEvent_Table.type.eq("message")).and(UserCustomerEvent_Table.isRead.eq(false)).queryList().size();
            ShortcutBadger.applyCount(this, unreadEvents);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        registerEventBus();
        registerPushNotificationReceiver();

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String test = intent.getStringExtra(PureSurveyFirebaseMessagingService.TYPE_KEY);
            if (test != null) {
                handlePushNotification(intent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkDialogState();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void navigateToActivity(IMEDDetails userDetails) {
        try {
            if (userDetails.getIntermediaryDetails() != null && !userDetails.getIntermediaryDetails().isEmpty()) {
                //                List<IntermediaryAgreement> result = response.getIntermediaryDetails().get(0).getIntermediaryAgreement();

                List<ChannelRole> channels = userDetails.getIntermediaryDetails().get(0).getPerson().get(0).getChannelRole();

                if (channels != null && !channels.isEmpty()) {

                    if (channels.size() > 1) { //user has multple sakles codes
                        gotoDestinationActivity(SalesCodesActivity.class);
                        return;
                    } else {

                        //get token


                        String salesCode = channels.get(0).getExternalReference();

                        PreferencesHelper.setSalesCode(this, salesCode);

                        if (channels.get(0).getMarketSegment().get(0).getExternalReference().equals("MFC-ZA")) {  //do check here for Retail mass....snap ap

                            SnapappAuthenticationApiController.validate(this, salesCode);
                        } else {
                            gotoDestinationActivity(SalesActivity.class);
                        }

                        return;
                    }
                } else {  //user doesn't have a sales code
                    PreferencesHelper.setSalesCode(this, null);


                    gotoDestinationActivity(SalesActivity.class);
                }
            } else {

                PreferencesHelper.setSalesCode(this, null);

                gotoDestinationActivity(SalesActivity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();

            PreferencesHelper.setSalesCode(this, null);

            gotoDestinationActivity(SalesActivity.class);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onValidationSuccess(ValidateResponse response) {
        if (response instanceof ValidateResponse) {
            gotoDestinationActivity(MatrixSelectionActivity.class);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {

        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_INTERMEDIARY_DETAILS)) {
            PreferencesHelper.setSalesCode(this, null);
            gotoDestinationActivity(SalesActivity.class);
        }

        if (errorResponse.getRequest().equals(SnapappAuthenticationApiController.VALIDATE)) {
            PreferencesHelper.setSalesCode(this, null);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_button) {
            onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        boolean hasMultipleSalesCodes = PreferencesHelper.hasMultipleSalesCodes(this);

        int menuItemId = dataList.get(position).id;

        if (menuItemId == MenuEnum.SWITCH_USER_POSITION.getNumVal()) {
            gotToSalesCodes();
        } else if (menuItemId == MenuEnum.HELP_POSITION.getNumVal()) {
            showDrawerFragment(new HelpFragment());
        } else if (menuItemId == MenuEnum.OMTC_POSITION.getNumVal()) {
            showDrawerFragment(new TermsConditionsFragment());
        } else if (menuItemId == MenuEnum.LOG_ISSUE_POS.getNumVal()) {
            showDrawerFragment(new LogIssueFragment());
        } else if (menuItemId == MenuEnum.CPD_POINTS_POSITION.getNumVal()) {
            showDrawerFragment(new MyCpdPointsFragment());
        } else if (menuItemId == MenuEnum.RATE_APP.getNumVal()) {
            showDrawerFragment(new RateAppFragment());
        } else if (menuItemId == MenuEnum.LOG_ISSUE_POS_BASIC.getNumVal()) {
            showDrawerFragment(new LogIssueAppFragment());
        } else if (menuItemId == MenuEnum.NOTIFICATION_MANAGER.getNumVal()) {

            if(PreferencesHelper.getStaffId(this) !=null){
                showDrawerFragment(new NotificationManagerFragment());
            }
            else{
                ImedAuthenticationController.register(this, PreferencesHelper.getSalesCode(this), PreferencesHelper.getCommonName(this), PreferencesHelper.getClientEmail(this), PreferencesHelper.getChannel(this), PreferencesHelper.getRegion(this), PreferencesHelper.getTeam(this),PreferencesHelper.getRole(this), PreferencesHelper.getSegment(this), PreferencesHelper.getFirstName(this), PreferencesHelper.getLastName(this), PreferencesHelper.getImedToken(this), PreferencesHelper.getArea(this) );
            }

        } else if (menuItemId == MenuEnum.LOGOUT_POSITION.getNumVal()) {
            logout(true);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
    }

    public void shareApp() {

        AnalyticsController.PostAction(this, new ActionRequest(PreferencesHelper.getDeviceId(this), ActionTypes.AppShare.getNumVal(), ""));

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Here is the link to download the Old Mutual Imed App: https://omadviserapp.co.za");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "SHARE THE APP");
        startActivity(shareIntent);
    }

    public void gotToSalesCodes() {

        gotoDestinationActivity(SalesCodesActivity.class);
        //        showFragment(R.id.fragment_container, new SalesCodesFragment());
    }

    public void showDrawerFragment(Fragment fragment) {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            showFragment(R.id.fragment_container, fragment);
        } else {
            fragmentUtility.addFragment(this, fragment, R.id.fragment_container, true, fragment.getClass().getSimpleName());
        }
    }

    public static void showDialog(final Context context, final String errorMessage) {
        //show nothing, if no activity has focus
        if (context == null) {
            return;
        }

        runOnUI(new Runnable() {
            @Override
            public void run() {
                AlertDialogHelper.showAlertDialog(errorMessage, context);
            }
        });
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
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

            dataList.add(new DrawerItem(MenuEnum.PROFILE_POSITION.getNumVal(), !TextUtils.isEmpty(PreferencesHelper.getSalesCode(BaseActivity.this)) ? PreferencesHelper.getSalesCode(BaseActivity.this) : "N/A", false, false, DrawerItem.DrawerType.TITLE, null));
            dataList.add(new DrawerItem(MenuEnum.SEGMENT_POSITION.getNumVal(), !TextUtils.isEmpty(PreferencesHelper.getSegment(this)) ? PreferencesHelper.getSegment(this) : "N/A", false, false, DrawerItem.DrawerType.TITLE, null));

            boolean hasMultipleSalesCodes = PreferencesHelper.hasMultipleSalesCodes(this);
            if (hasMultipleSalesCodes) {
                dataList.add(new DrawerItem(MenuEnum.SWITCH_USER_POSITION.getNumVal(), getString(R.string.menu_switch_user), true, false, DrawerItem.DrawerType.TITLE, null));
            }


            if (PreferencesHelper.getSegment(this) != null && !PreferencesHelper.getSegment(this).equals("MFC-ZA")) {
                dataList.add(new DrawerItem(MenuEnum.LOG_ISSUE_POS.getNumVal(), "LOG AN OM PROTECT ISSUE", true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_log_issue, null)));
                dataList.add(new DrawerItem(MenuEnum.LOG_ISSUE_POS_BASIC.getNumVal(), "LOG AN ISSUE", true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_log_issue, null)));
                dataList.add(new DrawerItem(MenuEnum.RATE_APP.getNumVal(), "RATE MY APP OR REQUEST ENHANCEMENTS", true, true, DrawerItem.DrawerType.TITLE, null));

                dataList.add(new DrawerItem(MenuEnum.NOTIFICATION_MANAGER.getNumVal(), "NOTIFICATION MANAGER", true, false, DrawerItem.DrawerType.TITLE, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_log_issue, null)));
                dataList.add(new DrawerItem(MenuEnum.CPD_POINTS_POSITION.getNumVal(), "MY CPD POINTS", true, false, DrawerItem.DrawerType.TITLE, null));
            }



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

    public void showFragment(int contentFrameId, Fragment replacingFragment) {
        replaceFragment(contentFrameId, replacingFragment);
    }

    private void checkDialogState() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showBackButton(boolean show) {
        if (findViewById(R.id.back_button) != null) {
            findViewById(R.id.back_button).setVisibility(show ? View.VISIBLE : View.GONE);
            findViewById(R.id.back_button).setOnClickListener(show ? this : null);
        }
    }

    protected void showNavTitle(boolean show) {
        if (findViewById(R.id.nav_title) != null) {
            findViewById(R.id.nav_title).setVisibility(show ? View.VISIBLE : View.GONE);
            findViewById(R.id.nav_title).setOnClickListener(show ? this : null);
        }
    }

    protected void setNavTitle(String title) {
        TextView navTitle = findViewById(R.id.nav_title);
        if (navTitle != null) {
            navTitle.setText(title);
        }
    }

    protected void startOnboardingActivity() {
        Intent intent = new Intent(this, OnboardingActivity.class);
        startActivity(intent);
    }

    protected void checkPhoneDetails() {
        int MyVersion = Build.VERSION.SDK_INT;
        if (IMEI == null && MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUESTING_PHONE_STATE);
            } else {
                checkPhoneIMSI();
            }
        } else if (IMSI == null) {
            checkPhoneIMSI();
        }
    }

    @SuppressLint("HardwareIds")
    protected void checkPhoneIMSI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (telephonyManager != null) {
//                IMEI = telephonyManager.getDeviceId();
//                IMSI = telephonyManager.getSubscriberId();


                //                if (PureSurveyApplication.IMSI == null) {
                //                    promptUserToInsertSim();
                //                }
            }
        } catch (Exception e) {
            //            promptUserToInsertSim();
        }
    }

    protected void promptUserToInsertSim() {
        checkDialogState();
        if (!BuildConfig.DEBUG) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Insert Sim").
                    setCancelable(false).setMessage("You must have a sim card inserted in order to use this app. Please insert one before continuing").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkPhoneDetails();
                }
            });
            dialog = builder.create();
            if (!isFinishing()) {
                dialog.show();
            }
        }
    }

    protected void promptUserToEnablePermissionInSettings() {
        checkDialogState();
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Permissions").
                setCancelable(false).setMessage("Please enable Phone permissions in the app settings before continuing").setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkPhoneDetails();
            }
        });
        dialog = builder.create();
        if (!isFinishing()) {
            dialog.show();
        }
    }

    public void decideWhetherToShowBackButton(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        if (fragment != null) {
            if (fragment instanceof ViewPagerFragment) {
                showBackButton(false);

            } else if (manager != null && getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                showBackButton(true);
            } else if (fragment instanceof HelpFragment || fragment instanceof TermsConditionsFragment) {
                showBackButton(true);
            } else if (fragment instanceof LoginFragment || fragment instanceof SalesCodesFragment) {
                showBackButton(false);
            } else if (fragment instanceof SupportRequestManagerFragment) {
                showBackButton(false);
            } else {
                showBackButton(true);
            }
        } else {
            showBackButton(false);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        decideWhetherToShowBackButton(fragment);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        decideWhetherToShowBackButton(fragment);
    }

    public void contactPressed(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.contact_email)});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body));

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.send_mail_title)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.no_email_clients_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard() {
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null)) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void replaceFragment(int contentFrameId, Fragment replacingFragment) {

        if (!isRunning) {
            DeferredFragmentTransaction deferredFragmentTransaction = new DeferredFragmentTransaction() {
                @Override
                public void commit() {
                    replaceFragmentInternal(getContentFrameId(), getReplacingFragment());
                }
            };

            deferredFragmentTransaction.setContentFrameId(contentFrameId);
            deferredFragmentTransaction.setReplacingFragment(replacingFragment);

            mDeferredFragmentTransactions.add(deferredFragmentTransaction);
        } else {
            replaceFragmentInternal(contentFrameId, replacingFragment);
        }
    }

    private void replaceFragmentInternal(int contentFrameId, Fragment replacingFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left).replace(contentFrameId, replacingFragment).commit();
    }

    private void replaceFragmentInternalTest(int contentFrameId, Fragment replacingFragment, Boolean isAdded, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (isAdded) {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left).replace(contentFrameId, replacingFragment).addToBackStack(tag).commit();
        } else {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left).replace(contentFrameId, replacingFragment).commit();
        }
    }

    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    private void registerPushNotificationReceiver() {

        mPushNotificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handlePushNotification(intent);
            }
        };

        IntentFilter filter2 = new IntentFilter(PureSurveyFirebaseMessagingService.HANDLE_PUSH_NOTIFICATION);
        registerReceiver(mPushNotificationReceiver, filter2);
    }

    private void unregisterPushNotificationReceiver() {
        unregisterReceiver(mPushNotificationReceiver);
    }

    protected void handlePushNotification(Intent intent) {

        String type = intent.getStringExtra(PureSurveyFirebaseMessagingService.TYPE_KEY);

        if (type.toLowerCase().equals("register")) {

            handleRegister();
        } else if (type.toLowerCase().equals("recall")) {

            String messageId = intent.getStringExtra(PureSurveyFirebaseMessagingService.MESSAGE_ID_KEY);
            UserCustomerEvent.handleRecall(this, messageId);
        } else {

            EventsApiController.events(this, PreferencesHelper.getSince(this));
        }
    }

    public void logout(boolean show) {

        if (show) {
            showProgressDialog(this);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CSIApiController.logout(getApplicationContext());
            }
        }, 1000);
    }

    protected void handleRegister() {
        checkDialogState();
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Register").
                setCancelable(false).setMessage("You need to register again").setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout(false);
            }
        });
        dialog = builder.create();
        dialog = builder.create();
        if (!isFinishing()) {
            dialog.show();
        }
    }

    public static PendingIntent addBackStack(final Context context, final Intent intent) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context.getApplicationContext());
        stackBuilder.addNextIntentWithParentStack(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(UserCustomerEventsResponse response) {

        if (response instanceof UserCustomerEventsResponse) {
            List<UserCustomerEvent> events = response.getData().events;


            if (!PureSurveyApplication.hasRegistered) {
                for (UserCustomerEvent event : events) {
                    if (event.getType().toLowerCase().equals("register")) {
                        handleRegister();
                        break;
                    }
                }
            }
        }
    }

    protected void gotoDashboard() {
        Intent intent = new Intent(BaseActivity.this, MatrixSelectionActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void gotoDashboard(boolean bypassDefault) {
        Intent intent = new Intent(BaseActivity.this, MatrixSelectionActivity.class);
        intent.putExtra(EXTRA_BYPASS_DEFAULT, bypassDefault);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void gotoDestinationActivity(Class clazz) {
        Intent intent = new Intent(BaseActivity.this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    protected void showOnboardingActivity() {
        if (!PreferencesHelper.hasShowOnboarding(this)) {
            startOnboardingActivity();
        }
    }

    public void showConnectionDialog(DialogInterface.OnClickListener onClickListener) {
        checkDialogState();
        dialog = new AlertDialog.Builder(this).setCancelable(false).setTitle(getString(R.string.error_dialog_title)).setMessage(R.string.unable_to_connect).setPositiveButton(getString(R.string.retry_button), onClickListener).create();
        if (!isFinishing()) {
            dialog.show();
        }
    }

    public void showCancelErrorDialog(DialogInterface.OnClickListener onClickListener) {
        checkDialogState();
        dialog = new AlertDialog.Builder(this).setCancelable(false).setTitle(getString(R.string.error_dialog_title)).setMessage(R.string.unable_to_connect).setPositiveButton(getString(R.string.retry_button), onClickListener).create();
        if (!isFinishing()) {
            dialog.show();
        }
    }

    public void showDeviceNotSupportedDialog(DialogInterface.OnClickListener onClickListener) {
        checkDialogState();
        dialog = new AlertDialog.Builder(this).setCancelable(false).setTitle(getString(R.string.error_dialog_title)).setMessage(R.string.device_not_supported).setPositiveButton(getString(R.string.ok_button_text), onClickListener).create();
        if (!isFinishing()) {
            dialog.show();
        }
    }

    public void showConnectionDialog(DialogInterface.OnClickListener onClickListenerPositive, DialogInterface.OnClickListener onClickListenerNegative) {
        checkDialogState();
        dialog = new AlertDialog.Builder(this).setTitle(getString(R.string.error_dialog_title)).setMessage(R.string.error_to_connect).setPositiveButton(getString(R.string.retry_button), onClickListenerPositive).setNegativeButton(getString(R.string.logout), onClickListenerNegative).create();
        if (!isFinishing()) {
            dialog.show();
        }
    }

    public void showFragmentTest(Fragment fragment, Boolean isAdded) {

        replaceFragmentInternalTest(R.id.fragment_container, fragment, isAdded, fragment.getClass().getSimpleName());
    }

    public void addFragment(Fragment fragment, int contentFrameId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(contentFrameId, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTING_PHONE_STATE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPhoneIMSI();
                } else {

                    if (permissions.length > 0 && ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                        checkPhoneDetails();
                    } else {
                        //set to never ask again
                        promptUserToEnablePermissionInSettings();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}