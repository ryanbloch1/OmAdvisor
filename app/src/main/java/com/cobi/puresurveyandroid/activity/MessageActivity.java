package com.cobi.puresurveyandroid.activity;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityMessageBinding;
import com.cobi.puresurveyandroid.fragment.EventFragment;
import com.cobi.puresurveyandroid.model.Dialog;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEvent;
import com.cobi.puresurveyandroid.model.ReplyResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEventsResponse;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.view.PureSurveyDateHeaderMessageViewHolder;
import com.cobi.puresurveyandroid.view.PureSurveyIncomingMessageViewHolder;
import com.cobi.puresurveyandroid.view.PureSurveyOutgoingMessageViewHolder;
import com.cobi.puresurveyandroid.webservice.controller.EventsApiController;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cobi.puresurveyandroid.PureSurveyFirebaseMessagingService.MESSAGE_ID_KEY;

/**
 * Created by admin on 2017/10/09.
 */

public class MessageActivity extends BaseActivity implements View.OnClickListener {
    public static final String DIALOG_ID_KEY = "dialog_id_key";

    private Dialog dialog;
    private MessagesListAdapter<UserCustomerEvent> adapter;
    private UserCustomerEvent pendingMessage;
    private String dialogId;

    private ActivityMessageBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);


//        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        MessageHolders holders = new MessageHolders();

        holders.setIncomingTextConfig(PureSurveyIncomingMessageViewHolder.class, R.layout.pure_survey_incoming_text_message);
        holders.setOutcomingTextConfig(PureSurveyOutgoingMessageViewHolder.class, R.layout.pure_survey_outgoing_text_message);
        holders.setDateHeaderHolder(PureSurveyDateHeaderMessageViewHolder.class);

        adapter = new MessagesListAdapter<>(PreferencesHelper.getFullName(this), holders, null);



        if(!TextUtils.isEmpty(getIntent().getExtras().getString(MESSAGE_ID_KEY))){
            dialogId = getIntent().getExtras().getString(MESSAGE_ID_KEY);
        }
        else{
            dialogId = getIntent().getExtras().getString(DIALOG_ID_KEY);
        }


        mBinding.swipeContainer.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                EventsApiController.events(getApplicationContext(), PreferencesHelper.getSince(getParent()));

            }
        });

//        dialogId = getIntent().getExtras().getString(DIALOG_ID_KEY);

        boolean init = initAdapter();
        if (init) {

            mBinding.messagesList.setAdapter(adapter);

            if (dialog.getMessages().size() > 0) {
                UserCustomerEvent event = dialog.getMessages().get(0);
                if (event.getType().equals("message") && !event.isReply()) {
                    mBinding.input.setVisibility(View.GONE);
                }
            }

            mBinding.input.setInputListener(new MessageInput.InputListener() {
                @Override
                public boolean onSubmit(CharSequence input) {
                    //validate and send message
                    if (input != null && !input.toString().trim().isEmpty()) {
                        mBinding.textViewPending.setVisibility(View.VISIBLE);
                        UserCustomerEvent replyTo = dialog.getMessages().get(dialog.getMessages().size() - 1);
                        pendingMessage = new UserCustomerEvent();
                        pendingMessage.setFrom(PreferencesHelper.getFullName(MessageActivity.this));
                        pendingMessage.setTime(DateHelper.dateToISO8601(new Date()));
                        pendingMessage.setBody(input.toString());
                        pendingMessage.setReplyToMessageId(replyTo.getMessageId());
                        pendingMessage.setSubject(replyTo.getSubject());
                        pendingMessage.setType("reply");
                        adapter.addToStart(pendingMessage, true);
                        PreferencesHelper.setPendingMessage(MessageActivity.this, pendingMessage);
                        sendMessage(pendingMessage);
                        return true;
                    }
                    return false;
                }
            });

            initToolbar();

            mBinding.messagesList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, getString(R.string.message_screen), null /* class override */);
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.action_matrix:
//
//                gotoDestinationActivity(MatrixSelectionActivity.class);
//
//                break;
//            case R.id.action_events:
//
//
//                mBinding.messsageSection.setVisibility(View.GONE);
//                showFragment(R.id.fragment_container, new EventFragment());
//                mBinding.fragmentContainer.setVisibility(View.VISIBLE);
//
//
//                mBinding.includeToolbar.titleLabel.setText("");
//                mBinding.includeToolbar.subtitleLabel.setText("");
//
//                break;
//            default:
//                return false;
//        }
//
//        return true;
//    }


    private void initToolbar() {
        setSupportActionBar(mBinding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        showBackButton(true);

        mBinding.includeToolbar.titleLabel.setText(dialog.getMessages().get(dialog.getMessages().size() - 1).getFrom().trim());
        mBinding.includeToolbar.subtitleLabel.setText(dialog.getDialogName().trim());

        initToolbarMenu();
    }

    private boolean initAdapter() {

        try{
            dialog = Dialog.getDialogForId(this, dialogId);
            PureSurveyApplication.messagesInThread = dialog.getMessages().size();

            if (dialog == null || dialog.getMessages().size() == 0) {
                MessageActivity.this.finish();
                return false;
            }

            markMessagesAsRead();

            adapter.clear();
            adapter.addToEnd(dialog.getMessages(), false);

            pendingMessage = PreferencesHelper.getPendingMessage(this);
            if (pendingMessage != null) {
                adapter.addToStart(pendingMessage, true);
            }

            return  true;
        }catch (Exception e){
            return  false;
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(ReplyResponse response) {
        if (response != null && response.getData() != null) {
            UserCustomerEvent event = PreferencesHelper.getPendingMessage(this);
            event.setMessageId(response.getData().getMessageId());
            PreferencesHelper.setPendingMessage(this, null);
            mBinding.textViewPending.setVisibility(View.GONE);
        }
    }

    @Override
    protected void handlePushNotification(Intent intent) {
        super.handlePushNotification(intent);
        initAdapter();
    }

    private void sendMessage(UserCustomerEvent message) {
        UserCustomerEvent replyTo = dialog.getMessages().get(dialog.getMessages().size() - 1);
        EventsApiController.reply(MessageActivity.this, replyTo.getMessageId(), message.getBody());
        mBinding.input.setEnabled(false);
    }

    private void markMessagesAsRead() {

        List<UserCustomerEvent> messages = dialog.getMessages();
        List<String> messageIds = new ArrayList<>();

        for (UserCustomerEvent event : messages) {
            if (!event.isRead()) {
                event.setRead(true);
                messageIds.add(event.getMessageId());
            }
        }

        ModelAdapter<UserCustomerEvent> adapter = FlowManager.getModelAdapter(UserCustomerEvent.class);
        adapter.saveAll(messages);

        if (messageIds.size() > 0) {
            EventsApiController.readReceipt(this, messageIds);
        }

        updateBadgeCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if (errorResponse.getRequest().equals(EventsApiController.REPLY)) {
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle(getString(R.string.error)).setCancelable(false).setMessage(errorResponse.getMessage()).setNeutralButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sendMessage(pendingMessage);
                }
            }).setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create();

            dialog.show();
        }

        mBinding.swipeContainer.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        if (PreferencesHelper.getPendingMessage(MessageActivity.this) == null) {
            this.finish();
        } else {
            mBinding.textViewPending.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(UserCustomerEventsResponse response) {
        super.onSuccessResponse(response);

        initAdapter();

        mBinding.swipeContainer.setRefreshing(false);

    }
}
