package com.cobi.puresurveyandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by admin on 2017/11/07.
 */

public class InstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ShortcutBadger.removeCount(context);
        Log.d("InstallReceiver", "onReceive: Removed Badger");
    }
}
