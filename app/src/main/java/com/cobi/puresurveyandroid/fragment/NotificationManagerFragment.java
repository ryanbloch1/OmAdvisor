package com.cobi.puresurveyandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.NotificationAdapter;
import com.cobi.puresurveyandroid.adapter.NotificationUnchangeableAdapter;
import com.cobi.puresurveyandroid.adapter.PipelineAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentNotificationManagerBinding;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Notification;
import com.cobi.puresurveyandroid.model.NotificationOption;
import com.cobi.puresurveyandroid.model.NotificationStatusRequest;
import com.cobi.puresurveyandroid.model.NotificationsResponse;
import com.cobi.puresurveyandroid.model.UserCustomerEventsResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.NotificationManagerController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;


public class NotificationManagerFragment extends BaseFragment implements NotificationAdapter.onNotificationOptionChecked {

    FragmentNotificationManagerBinding mBinding;
    NotificationAdapter NChangeableAdapter;
    NotificationUnchangeableAdapter NUnchangeableAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification_manager, container, false);


        NotificationManagerController.getNotifications(getContext(), PreferencesHelper.getImedToken(getContext()), PreferencesHelper.getStaffId(getContext()));


        mBinding.extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mBinding.unchageable.getVisibility() == View.GONE) {
                    mBinding.setIsExtended(true);
                } else {
                    mBinding.setIsExtended(false);

                }

                toggleVis(mBinding.unchageable);

                mBinding.unchageable.getParent().requestChildFocus(mBinding.unchageable, mBinding.unchageable);
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterEventBus();
    }

    @Override
    public void onResume() {
        super.onResume();

        registerEventBus();
    }

    private void setAdapters(NotificationsResponse response) {


        ArrayList<Notification> cN = (ArrayList<Notification>) response.getChangeable();
        ArrayList<Notification> nonC = (ArrayList<Notification>) response.getNonchangeable();


        // divide respons e into 2 lists and apply to 2 lists in view

        NChangeableAdapter = new NotificationAdapter(cN, getContext(), this);
        mBinding.changeable.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.changeable.setHasFixedSize(true);
        mBinding.changeable.setAdapter(NChangeableAdapter);


        NUnchangeableAdapter = new NotificationUnchangeableAdapter(nonC, getContext());
        mBinding.unchageable.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.unchageable.setHasFixedSize(true);
        mBinding.unchageable.setAdapter(NUnchangeableAdapter);

    }

    @Override
    public void onNotificationOptionChecked(Notification notification, boolean checked) {
        // CALL API FOR NOTIFICATION SELECTION

        NotificationStatusRequest request = new NotificationStatusRequest(PreferencesHelper.getStaffId(getContext()), notification.getNotificationId(), checked);

        NotificationManagerController.statusChange(getContext(), PreferencesHelper.getImedToken(getContext()), request);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(NotificationsResponse response) {

        //save response and apply to 2 recycler views in view..

        setAdapters(response);
    }

}


