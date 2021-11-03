package com.cobi.puresurveyandroid.fragment;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.adapter.OmpAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentCustomerContactNoRichpathBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.MyCustomerCounts;

import android.app.Activity;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.activity.SalesActivity;
import com.cobi.puresurveyandroid.databinding.FragmentCustomerContactBinding;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.OmpClientRequest;
import com.cobi.puresurveyandroid.model.OmpClientsResponse;
import com.cobi.puresurveyandroid.model.Party;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.OmpController;
import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerContactFragment extends ViewPagerFragment {
    private FragmentCustomerContactBinding mBinding;
    private ClientViewModel clientViewModel;

    private double green;
    private double red;
    private double orange;
    private double lightGreen;

    private double pipeline = 0;
    private double missedPremiums = 0;
    private double campaigns = 0;
    private double birthdays = 0;

    private boolean hasAttached = false;

    public CustomerContactFragment() {
        // Required empty public constructor
    }

    public static CustomerContactFragment newInstance() {
        return new CustomerContactFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        clientViewModel.initHotl();

        try {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_contact, container, false);


            if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
                FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(getContext());
                instanceFA.setCurrentScreen(getActivity(), "Customer Contact Screen", this.getClass().getSimpleName());
            }
//            mBinding.scrollView.getViewTreeObserver()
//                    .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                        @Override
//                        public void onScrollChanged() {
//                            if (mBinding.scrollView.getChildAt(0).getBottom()
//                                    <= (mBinding.scrollView.getHeight() + mBinding.scrollView.getScrollY())) {
//                                //scroll view is at bottom
//
//
//                                if (!PreferencesHelper.hasShownOverlay(getContext())) {
//                                    new BubbleShowCaseSequence()
//                                            .addShowCase(getShowcase2())
//                                            .addShowCase(getShowcase3())//
//                                            // This one will be showed when firstShowCase is dismissed
//                                            .show();
//
//
//
//                                   new  BubbleShowCaseBuilder(getActivity()) //Activity instance
//                                            .title("foo") //Any title for the bubble view
//                                            .targetView(view) //View to point out
//                                            .show() /
//                                    PreferencesHelper.setHasShownOverlay(getContext(), true);
//                                }
//
//
//                            }
//                        }
//                    });


            mBinding.topLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity instanceof SalesActivity) {
                        ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);
                        ((SalesActivity) activity).showBirthday();
                    }
                }
            });
            mBinding.topRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        if (!(activity instanceof CommissionFragment.OnSelectedListener)) {
                            throw new ClassCastException(getActivity().toString() + " must implement OnSelectedListener");
                        } else {
                            ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);
                            showNestedFragment(new PipelineFragment());
                        }
                    }
                }
            });
            mBinding.bottomLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        if (!(activity instanceof CommissionFragment.OnSelectedListener)) {
                            throw new ClassCastException(getActivity().toString() + " must implement OnSelectedListener");
                        } else {
                            ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);
                            showNestedFragment(new CampaignsFragment());
                        }
                    }
                }
            });

            mBinding.bottomRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        if (!(activity instanceof CommissionFragment.OnSelectedListener)) {
                            throw new ClassCastException(getActivity().toString() + " must implement OnSelectedListener");
                        } else {
                            ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);

                            showNestedFragment(new MissedPremiumsFragment());
                        }
                    }
                }
            });

            return mBinding.getRoot();
        } catch (Exception e) {

            FragmentCustomerContactNoRichpathBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_contact_no_richpath, container, false);

            binding.topLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity instanceof SalesActivity) {
                        ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);
                        ((SalesActivity) activity).showBirthday();
                    }
                }
            });
            binding.topRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        if (!(activity instanceof CommissionFragment.OnSelectedListener)) {
                            throw new ClassCastException(getActivity().toString() + " must implement OnSelectedListener");
                        } else {
                            ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);
                            showNestedFragment(new PipelineFragment());
                        }
                    }
                }
            });
            binding.bottomLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        if (!(activity instanceof CommissionFragment.OnSelectedListener)) {
                            throw new ClassCastException(getActivity().toString() + " must implement OnSelectedListener");
                        } else {
                            ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);
                            showNestedFragment(new CampaignsFragment());
                        }
                    }
                }
            });

            binding.bottomRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        if (!(activity instanceof CommissionFragment.OnSelectedListener)) {
                            throw new ClassCastException(getActivity().toString() + " must implement OnSelectedListener");
                        } else {
                            ((SalesActivity) activity).onSelected(SalesActivity.Selected.MY_CUSTOMER);
                            showNestedFragment(new MissedPremiumsFragment());
                        }
                    }
                }
            });

            return binding.getRoot();
        }
    }

    private void showProgress(boolean progress) {
        if (mBinding != null) {
            if (progress) {
                mBinding.progress.setVisibility(View.VISIBLE);
                mBinding.scrollView.setEnabled(false);
            } else {

                mBinding.progress.setVisibility(View.GONE);
                mBinding.scrollView.setEnabled(true);
            }
        }
    }

    @Override
    public void onBecommingVisible(boolean visible) {
        super.onBecommingVisible(visible);
        if (visible && getContext() != null) {
            AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.MyCustomerAccessed.getNumVal(), ""));
            fetchData();
        }
    }

    private void fetchData() {
        Context context = getContext();
        if (context != null) {
            String salesCode = PreferencesHelper.getSalesCode(context);
            if (salesCode != null && !salesCode.isEmpty()) {
                showProgress(true);


                OmpController.getClients(getContext(), new OmpClientRequest(PreferencesHelper.getSalesCode(getContext()), PreferencesHelper.getRole(getContext())));

            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(List<OmpClientsResponse> response) {


        ArrayList<Party> ompList = new ArrayList<>();






        for (OmpClientsResponse client : response) {

            for (Party p : client.getAdviserSubmit().getParty()) {

                if(p.getRoles().contains("POLICY OWNER")){
                    p.setApplicationNumber(client.getAdviserSubmit().getApplicationInstance());

                    ompList.add(p);

                }

            }

        }


        for (int i = 0; i < ompList.size(); i++) {


            if (ompList.get(i).getFirstName() == null && ompList.get(i).getLastName() == null) {

                if (ompList.get(i).getPartyType().equals("Organization")) {

                    if(ompList.get(i).getLegalStructure() != null || ompList.get(i).getName() != null){

                        ompList.get(i).setLastName(ompList.get(i).getLegalStructure());
                        ompList.get(i).setFirstName(ompList.get(i).getName());
                    }
                    else{
                        ompList.remove(i);
                    }

                }
                else{
                    ompList.remove(i);
                }

            }
        }

        //sort alphabetically..

        //get Omplistsize add it to pipelines

        pipeline = ompList.size();

        CSIApiController.getCountsForLandingPage(getContext(), PreferencesHelper.getSalesCode(getContext()));




    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLandingPageCountsResponse(MyCustomerCounts response) {

        showProgress(false);

        if (response instanceof MyCustomerCounts) {

            pipeline = pipeline + response.getContractingParties();
            missedPremiums = response.getMissedPremiums();
            campaigns = response.getActiveLeads();
            birthdays = response.getBirthdaysThisMonth();
            calculatePercentages();
        }
    }

    private void calculatePercentages() {

        double maxVal = Math.max(campaigns, Math.max(pipeline, Math.max(birthdays, missedPremiums)));
        double most = maxVal + (maxVal * 0.75);

        if (most > 0) {
            green = missedPremiums / most;
            orange = pipeline / most;
            lightGreen = birthdays / most;
            red = campaigns / most;
        } else {
            green = 0;
            orange = 0;
            lightGreen = 0;
            red = 0;
        }
        showValues();
        animateGraph();
    }


    private void showValues() {
        DecimalFormat format = new DecimalFormat("#");
        mBinding.lightGreenNumber.setText(format.format(birthdays));
        mBinding.greenNumber.setText(format.format(missedPremiums));
        mBinding.redNumber.setText(format.format(campaigns));
        mBinding.orangeNumber.setText(format.format(pipeline));
    }

    private void animateGraph() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RichPathView view = mBinding.graph;
            RichPath greenPath = view.findFirstRichPath();
            RichPath redPath = view.findRichPathByIndex(1);
            RichPath lightGreenPath = view.findRichPathByIndex(2);
            RichPath orangePath = view.findRichPathByIndex(3);
            RichPathAnimator.animate(greenPath).duration(1000).interpolator(new DecelerateInterpolator()).pathData(calculateNewPath(0, green)).andAnimate(redPath).duration(1000).interpolator(new DecelerateInterpolator()).pathData(calculateNewPath(1, red)).andAnimate(lightGreenPath).duration(1000).interpolator(new DecelerateInterpolator()).pathData(calculateNewPath(2, lightGreen)).andAnimate(orangePath).duration(1000).interpolator(new DecelerateInterpolator()).pathData(calculateNewPath(3, orange)).start();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        //Dont show errors, just show 0
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_GET_LANDING_PAGE_COUNTS)) {
            //show error message....

        }
        showProgress(false);
        calculatePercentages();
    }

    @Override
    public void onResume() {
        super.onResume();
        //        BaseActivity activity = (BaseActivity) getActivity();
        //        if (activity != null) {
        //            activity.showBackButton(true);
        //        }
    }

    private String calculateNewPath(int pathIndex, double percentFull) {
        int L0 = 0, L1 = 0, Z0 = 0, Z1 = 0;
        int percentageAsValue = Double.valueOf(percentFull * 100D).intValue() + 150;
        switch (pathIndex) {
            case 0:
                L0 = 250 + percentageAsValue;
                L1 = 250;
                Z0 = 250;
                Z1 = 250 + percentageAsValue;
                break;
            case 1:
                L0 = 250;
                L1 = 250 + percentageAsValue;
                Z0 = 250 - percentageAsValue;
                Z1 = 250;
                break;
            case 2:
                L0 = 250 - percentageAsValue;
                L1 = 250;
                Z0 = 250;
                Z1 = 250 - percentageAsValue;
                break;
            case 3:
                L0 = 250;
                L1 = 250 - percentageAsValue;
                Z0 = 250 + percentageAsValue;
                Z1 = 250;
                break;
            case 4:
                throw new IllegalArgumentException("path index must be < 4");
        }

        String result = "M250,250 L" + L0 + "," + L1 + " A" + percentageAsValue + "," + percentageAsValue + " 0 0,1 " + Z0 + "," + Z1 + " z";

        return result;
        // "M250,250 L250,350 A250,250 0 0,1 350,250  z"
    }





}
