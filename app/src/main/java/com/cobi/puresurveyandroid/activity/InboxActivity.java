package com.cobi.puresurveyandroid.activity;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityInboxBinding;
import com.cobi.puresurveyandroid.fragment.EventFragment;
import com.cobi.puresurveyandroid.model.Dialog;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEventsResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.view.PureSurveyDialogViewHolder;
import com.cobi.puresurveyandroid.webservice.controller.SnapappAuthenticationApiController;
import com.cobi.puresurveyandroid.webservice.controller.EventsApiController;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;



/**
 * Created by admin on 2017/10/09.
 */

public class InboxActivity extends BaseActivity implements DialogsListAdapter.OnDialogClickListener<Dialog>, DialogsListAdapter.OnDialogLongClickListener<Dialog>, DateFormatter.Formatter, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String SCROLL_POSITION = "scroll_position";
    protected List<DialogsListAdapter<Dialog>> dialogsListAdapterList;
    private List<DialogsList> dialogsListList;
    private int scrollPos = 0;
    private Call<UserCustomerEventsResponse> webserviceCall;

    private ActivityInboxBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_inbox);



        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEvents();

            }
        });


        if (savedInstanceState != null) {
            scrollPos = savedInstanceState.getInt(SCROLL_POSITION, 0);
        }

        SnapappAuthenticationApiController.updateDevice(this);

        initToolbar();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_matrix:

                gotoDestinationActivity(MatrixSelectionActivity.class);

                break;
            case R.id.action_events:

                mBinding.dialogsListScrollView.setVisibility(View.GONE);
                showFragment(R.id.fragment_container, new EventFragment());
                mBinding.fragmentContainer.setVisibility(View.VISIBLE);

                break;
            default:
                return false;
        }

        return true;
    }


    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mBinding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initToolbarMenu();
    }

    @Override
    public void onBackPressed() {
        gotoDashboard(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, "Inbox Screen", null /* class override */);
        }


        getEvents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webserviceCall != null) {
            webserviceCall.cancel();
        }
        webserviceCall = null;
        findViewById(R.id.progress).setVisibility(View.GONE);
    }

    private void getEvents() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        webserviceCall = EventsApiController.events(this, PreferencesHelper.getSince(this));
    }

    @Override
    public String format(Date date) {
        return DateFormatter.format(date, "hh:mma").toLowerCase();
    }

    public String formatHeader(Date date) {

        if (DateFormatter.isToday(date)) {
            return getString(R.string.date_header_today);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else if (DateFormatter.isCurrentYear(date)) {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    private void initAdapters() {
        List<List<Dialog>> dialogsSeperatedByDay = Dialog.createDialogsFromEvents(this);
        dialogsListList = new ArrayList<>();
        dialogsListAdapterList = new ArrayList<>();
        mBinding.dialogsListContainer.removeAllViews();

        for (int i = 0; i < dialogsSeperatedByDay.size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(InboxActivity.this);
            TextView header = (TextView) inflater.inflate(R.layout.content_inbox_date_banner, mBinding.dialogsListContainer, false);
            header.setText(formatHeader(dialogsSeperatedByDay.get(i).get(0).getLastMessage().getCreatedAt()));


            DialogsList dialogsList = (DialogsList) getLayoutInflater().inflate(R.layout.pure_survey_dialogs_list, null);
            DialogsListAdapter<Dialog> dialogsAdapter = new DialogsListAdapter<>(R.layout.inbox_item, PureSurveyDialogViewHolder.class, null);

            dialogsAdapter.setItems(dialogsSeperatedByDay.get(i));
            dialogsAdapter.setOnDialogClickListener(this);
            dialogsAdapter.setOnDialogLongClickListener(this);
            dialogsAdapter.setDatesFormatter(this);
            dialogsList.setAdapter(dialogsAdapter);

            dialogsListAdapterList.add(dialogsAdapter);
            dialogsListList.add(dialogsList);
            mBinding.dialogsListContainer.addView(header);
            mBinding.dialogsListContainer.addView(dialogsList);
        }

        mBinding.dialogsListScrollView.post(new Runnable() {
            public void run() {
                if (scrollPos == 0) {
                    mBinding.dialogsListScrollView.fullScroll(View.FOCUS_UP);
                } else {
                    mBinding.dialogsListScrollView.setScrollY(scrollPos);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mBinding.dialogsListScrollView != null) {
            scrollPos = mBinding.dialogsListScrollView.getScrollY();
            outState.putInt(SCROLL_POSITION, scrollPos);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mBinding.dialogsListScrollView != null) {
            int scroll = savedInstanceState.getInt(SCROLL_POSITION, 0);
            mBinding.dialogsListScrollView.setScrollY(scroll);
        }
    }

    @Override
    protected void handlePushNotification(Intent intent) {
        super.handlePushNotification(intent);
        initAdapters();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(MessageActivity.DIALOG_ID_KEY, dialog.getId());
        startActivity(intent);
    }

    @Override
    public void onDialogLongClick(Dialog dialog) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(UserCustomerEventsResponse response) {
        super.onSuccessResponse(response);
        webserviceCall = null;

        mBinding.swipeContainer.setRefreshing(false);

        findViewById(R.id.progress).setVisibility(View.GONE);

        initAdapters();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        System.out.print("");
        webserviceCall = null;

        mBinding.swipeContainer.setRefreshing(false);

        findViewById(R.id.progress).setVisibility(View.GONE);
        showConnectionDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getEvents();
            }
        });
    }
}
