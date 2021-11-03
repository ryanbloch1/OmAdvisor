/**
 * Copyright 2016 Goo
 * gle Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cobi.puresurveyandroid;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.cobi.puresurveyandroid.activity.BlockActivity;
import com.cobi.puresurveyandroid.activity.InboxActivity;
import com.cobi.puresurveyandroid.activity.MatrixSelectionActivity;
import com.cobi.puresurveyandroid.activity.MessageActivity;
import com.cobi.puresurveyandroid.activity.SalesActivity;
import com.cobi.puresurveyandroid.model.EventsResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEvent;
import com.cobi.puresurveyandroid.model.UserCustomerEventsResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.EventsApiController;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;
import org.xms.f.messaging.ExtensionMessagingService;
import org.xms.f.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;
import java.util.List;

import static android.app.Notification.FLAG_NO_CLEAR;
import static android.app.Notification.FLAG_ONGOING_EVENT;

public class PureSurveyFirebaseMessagingService extends ExtensionMessagingService {

    public static final String HANDLE_PUSH_NOTIFICATION = "com.cobi.puresurveyandroid.PUSH_NOTIFICATION";
    public static final String TYPE_KEY = "type";
    public static final String MESSAGE_ID_KEY = "message_id";
    public static final String DIALOG_ID_KEY = "dialog_id_key";
    private static final String TAG = "MessagingService";
    private int notificationId = 0;

    private String mId;
    private String title;

    private boolean isAppInForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();
        boolean isActivityFound = false;

        if (services.get(0).processName
                .equalsIgnoreCase(getPackageName()) && services.get(0).importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            isActivityFound = true;
        }

        return isActivityFound;
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        PreferencesHelper.setCloudMessagingRegistrationId(this, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String type = remoteMessage.getData().get(TYPE_KEY);

        mId = remoteMessage.getData().get(MESSAGE_ID_KEY);

        title = remoteMessage.getData().get("title");

        String body = remoteMessage.getData().get("body");

        if (!TextUtils.isEmpty(type) || !TextUtils.isEmpty(mId)) {

            //SNappapp Notifications

            Intent intent = new Intent();
            intent.setAction(HANDLE_PUSH_NOTIFICATION);

            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            if (type != null) {
                intent.putExtra(TYPE_KEY, type);
            }
            if (mId != null) {
                intent.putExtra(MESSAGE_ID_KEY, mId);
            }
            if (title != null) {
                intent.putExtra("title", title);
            }

            if (type.equals("message")) {

                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().register(this);

                }
                EventsApiController.events(getApplicationContext(), PreferencesHelper.getSince(getApplicationContext()));
            } else {
                sendBroadcast(intent);
            }
        } //snapapp notifications

        else {         //other notifications
            PendingIntent pendingIntent;
            Intent notificationIntent = new Intent(getApplicationContext(), SalesActivity.class);

            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

            //OM Notifications
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PureSurveyApplication.CHANNEL_2_ID).setContentTitle(title).setContentText(body).setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(body)).setSmallIcon(R.drawable.app_icon).setAutoCancel(true).setContentIntent(pendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int random = (int)System.currentTimeMillis();

            notificationManager.notify(random, notificationBuilder.build());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(UserCustomerEventsResponse response) {
        if (response != null && response.getData() != null) {
            Intent messageIntent = new Intent(this, MessageActivity.class);

            messageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            messageIntent.putExtra(DIALOG_ID_KEY, mId);

            messageIntent.putExtra("type", "message");

            Intent inboxIntent = new Intent(getApplicationContext(), InboxActivity.class);

            Intent matrixIntent = new Intent(getApplicationContext(), MatrixSelectionActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{matrixIntent, inboxIntent, messageIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PureSurveyApplication.CHANNEL_1_ID).setContentTitle(title).setSmallIcon(R.drawable.app_icon).setAutoCancel(true).setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

            EventBus.getDefault().unregister(this);
        }
    }
}


