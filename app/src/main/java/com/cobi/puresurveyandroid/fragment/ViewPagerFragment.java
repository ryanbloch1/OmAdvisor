package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;
import android.content.Context;

import com.cobi.puresurveyandroid.activity.BaseActivity;

public class ViewPagerFragment extends BaseFragment {

    private boolean becomingVisibleOnceAttached = false;

    public void onBecommingVisible(boolean visible) {
        if (visible) {
            if (getContext() == null) {
                becomingVisibleOnceAttached = true;
            } else {
                registerEventBus();
            }
        } else {
            unregisterEventBus();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (becomingVisibleOnceAttached) {
            becomingVisibleOnceAttached = false;
            onBecommingVisible(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unregisterEventBus();
    }
}
