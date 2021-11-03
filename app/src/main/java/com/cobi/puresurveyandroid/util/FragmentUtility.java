package com.cobi.puresurveyandroid.util;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;

public class FragmentUtility {

    private static final String TAG = "FragmentsManager";
    private static FragmentTransaction transaction;

    private static FragmentTransaction getTransaction(Activity activity) {

        return getFragmentManager(activity).beginTransaction();
    }

    private static FragmentManager getFragmentManager(Activity activity) {
        return ((BaseActivity) activity).getSupportFragmentManager();
    }

    public static void addFragment(Activity activity, Fragment fragment, int id, boolean isAddedToBackstack, String tag) {
        transaction = getTransaction(activity);
        transaction.add(id, fragment, fragment.getClass().getName());
        if (isAddedToBackstack)
            transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
        getFragmentManager(activity).executePendingTransactions();
    }

    public static void replaceFragment(Activity activity, Fragment fragment, int id, boolean isAddedToBackstack, String tag) {

        Fragment check_Fragment = getFragmentManager(activity).findFragmentByTag(fragment.getClass().getName());
        if (check_Fragment == null)
        {
            transaction = getTransaction(activity)
                    .replace(id, fragment, fragment.getClass().getName());
            if (isAddedToBackstack)
                transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        } else {
            transaction = getTransaction(activity);
            transaction.replace(id, check_Fragment, check_Fragment.getClass().getName())
                    .addToBackStack(null)
                    .commit();
        }
        getFragmentManager(activity).executePendingTransactions();
    }

    public static void replaceFragmentSlide(Activity activity, Fragment fragment, int id, boolean isAddedToBackstack, String tag) {

        Fragment check_Fragment = getFragmentManager(activity).findFragmentByTag(fragment.getClass().getName());
        if (check_Fragment == null) {
            transaction = getTransaction(activity).setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left)
                    .replace(id, fragment, fragment.getClass().getName());
            if (isAddedToBackstack)
                transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        } else {
            transaction = getTransaction(activity);
            transaction.replace(id, check_Fragment, check_Fragment.getClass().getName())
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left)
                    .addToBackStack(null)
                    .commit();
        }

        getFragmentManager(activity).executePendingTransactions();
    }

    public static void removeFragment(Fragment fragment){
       transaction.remove(fragment);
    }

}