package com.cobi.puresurveyandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import android.os.Build;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.cobi.puresurveyandroid.activity.SalesActivity;
import com.cobi.puresurveyandroid.database.PureSurveyDatabase;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.DeviceRequest;
import com.cobi.puresurveyandroid.model.Dialog;
import com.cobi.puresurveyandroid.listener.MyDistributeListener;
import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.ImedAuthenticationController;
import com.cobi.puresurveyandroid.webservice.controller.SnapappAuthenticationApiController;
import org.xms.g.common.ExtensionPlayServicesNotAvailableException;
import org.xms.g.common.ExtensionPlayServicesRepairableException;
import org.xms.g.security.ProviderInstaller;
import org.xms.f.analytics.ExtensionAnalytics;
import org.xms.f.crashlytics.ExtensionCrashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.jaredrummler.android.device.DeviceName;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.distribute.Distribute;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;


/**
 * Created by admin on 2017/10/05.
 */

public class PureSurveyApplication extends MultiDexApplication {

    public static String IMEI;
    public static String IMSI;

    public static String MANUFACTURER;
    public static String MODEL;
    public static boolean hasRegistered;
    public static int messagesInThread;
    private static Context mContext;
    private Map<String, Dialog> dialogs;
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    public static final String IMED_API_KEY = "MU0zZF9jM050cjRsLSR1dEguNVlzVDNtOlchdGgrIUQ9NDczOTYyMjFjMjZjNDEyNWJjN2JjMDUxZTkxMTY2ODA=";
    public static final String IMED_APP_ID = "889df92f-2ce6-4163-88c9-a51d27990f50";

    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        org.xms.g.utils.GlobalEnvSetting.init(this, null);
        org.xms.adapter.utils.XLoader.init(this);

        // OPTIONAL: If crash reporting has been explicitly disabled previously, add:
        ExtensionCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        if (!BuildConfig.IS_STAGING) {
            Distribute.setListener(new MyDistributeListener());
            AppCenter.start(this, "20302c68-6e34-42c0-91cd-fdedb80d506d", Analytics.class, Crashes.class, Distribute.class);
        }

        AndroidThreeTen.init(this);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (ExtensionPlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (ExtensionPlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        mContext = getApplicationContext();

//        Fabric.with(this, new Crashlytics());
        DeviceName.with(this).request(new DeviceName.Callback() {

            @Override
            public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                PureSurveyApplication.MANUFACTURER = info.manufacturer;
                PureSurveyApplication.MODEL = info.model;
            }
        });


        //

        //get Android id here...


        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.getDatabase(PureSurveyDatabase.NAME).getWritableDatabase();


        createNotificationChannels();

        ImedAuthenticationController.getToken(mContext, IMED_APP_ID, IMED_API_KEY, true);

    }

    public Map<String, Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(Map<String, Dialog> dialogs) {

        this.dialogs = dialogs;
    }


    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager manager = getSystemService(NotificationManager.class);

            NotificationChannel channel1 = new NotificationChannel(

                    CHANNEL_1_ID,
                    "channel 1", NotificationManager.IMPORTANCE_HIGH

            );

            channel1.setDescription("this is Channel 1");


            manager.createNotificationChannel(channel1);


            NotificationChannel channel2 = new NotificationChannel(

                    CHANNEL_2_ID,
                    "channel 2", NotificationManager.IMPORTANCE_HIGH

            );

            channel1.setDescription("this is Channel 2");

            manager.createNotificationChannel(channel2);

        }
    }




}
