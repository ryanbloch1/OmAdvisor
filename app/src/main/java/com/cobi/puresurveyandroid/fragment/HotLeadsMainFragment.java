package com.cobi.puresurveyandroid.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentHotLeadsMainBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.RatingRequestAnalytics;
import com.cobi.puresurveyandroid.model.CountryCode;
import com.cobi.puresurveyandroid.model.DeclineReason;
import com.cobi.puresurveyandroid.model.HotLeadActions;
import com.cobi.puresurveyandroid.model.Lead;
import com.cobi.puresurveyandroid.model.SendUsernameResponse;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.HotLeadsController;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class HotLeadsMainFragment extends BaseFragment implements View.OnClickListener {

    FragmentHotLeadsMainBinding mBinding;
    private ClientViewModel clientViewModel;
    private Spinner spinner;
    private Lead mLead;
    PrettyTime prettyTime = new PrettyTime();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot_leads_main, container, false);

        mBinding.refer.setOnClickListener(this);
        mBinding.accept.setOnClickListener(this);
        mBinding.reject.setOnClickListener(this);

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        if (clientViewModel.getSelectedCampaign().getValue() != null) {
            mBinding.setLeadDesc(clientViewModel.getSelectedCampaign().getValue().getCampaignName());
        }

        if (clientViewModel.getSelectedLead().getValue() != null) {

            Lead lead = clientViewModel.getSelectedLead().getValue();
            mBinding.setLeadName(lead.getFullName());


            if(!lead.getLeadExpiryDate().equals("")){


                String text = prettyTime.format(DateHelper.StringToDate(lead.getLeadExpiryDate()));

                try {
                    mBinding.setExpiration(text.replace("from now", ""));
                }catch (Exception e){

                }
            }

            mLead = clientViewModel.getSelectedLead().getValue();
        }

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterEventBus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAcceptResponse(String leadAccepted){

        if(leadAccepted.equals("Lead Accepted.")){
            showNestedFragment(new ContactDetailsFragment2());
        }
    }

    private void populateSpinner(final List<DeclineReason> reasons){

        reasons.add(new DeclineReason(0 ,"--Please select--"));

        ArrayAdapter dataAdapter = new ArrayAdapter<DeclineReason>(getContext(), R.layout.spinner_item_layout, reasons) {

        @Override
        public int getCount() {
            return reasons.size() -1;
        }

        @Override
        public DeclineReason getItem(int position) {
            return reasons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            TextView label = (TextView) super.getView(position, convertView, parent);
            //                label.setTextColor(Color.BLACK);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            label.setText(reasons.get(position).getDescription());

            // And finally return your dynamic (or custom) view for each spinner item
            return label;
        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView label = (TextView) super.getDropDownView(position, convertView, parent);
            //                label.setTextColor(Color.BLACK);
            label.setText(reasons.get(position).getDescription());

            return label;
        }
    };

        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        if(spinner != null){
            spinner.setAdapter(dataAdapter);
            spinner.setSelection(dataAdapter.getCount());
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeclineResponse(List<DeclineReason> response) {
        populateSpinner(response);
    }



    public void showPopup(final Context mContext, String title, String sub, boolean reject, final int LeadActions) {

        //if reject call api to fetch reasons and populate spinner...

        final List<String> list = new ArrayList<String>();

        list.add("Production");
        list.add("Training");
        list.add(getText(R.string.choose_spinner_option).toString());

        final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.hot_leads_popup, null);

        ImageView close = mView.findViewById(R.id.close);
        TextView submit = mView.findViewById(R.id.submit);
        final TextView topTitle = mView.findViewById(R.id.topText);
        spinner = mView.findViewById(R.id.spinner);
        spinner.setPrompt("Please choose");
        final TextView subTitle = mView.findViewById(R.id.subTitle);

        final LinearLayout rejectLayout = mView.findViewById(R.id.rejected_layout);

        topTitle.setText(title);
        subTitle.setText(sub);

        if (reject) {

            HotLeadsController.getDeclineReasons(getContext());

            rejectLayout.setVisibility(View.VISIBLE);

        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(LeadActions == HotLeadActions.Reject.getNumVal()){

                            String x = spinner.getSelectedItem().toString();


                            DeclineReason d =   (DeclineReason) spinner.getSelectedItem();

                            if(d.getId() == 0){
                                //show error please select a reason why you are declining this lead

                                ((TextView) spinner.getSelectedView()).setError("Please choose option");

                            }
                            else{
                                if(mLead != null){

                                    HotLeadsController.rejectLead(getContext(),PreferencesHelper.getSalesCode(getContext()), mLead.getLeadId(), ((DeclineReason) spinner.getSelectedItem()).getId());

                                    AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LeadDeclined.getNumVal(), generateHotLeadsJson(String.valueOf(mLead.getLeadId()), PreferencesHelper.getSalesCode(getContext()))));

                                    dialog.dismiss();

                                    clearBackStack();

                                    showNestedFragment(new CampaignsFragment());



                                }
                            }


                        }
                    }
                }, 1000);



            }
        });

        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(mView);
        dialog.show();
    }


    private  void changeButtonStyle(final TextView view){
        view.setTextColor(ResourceHelper.getColour(R.color.white));
        view.setBackground(ResourceHelper.getDrawable(R.drawable.gradient_button_background));

        final Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                view.setTextColor(ResourceHelper.getColour(R.color.bottomNavigationSelected));
                view.setBackground(ResourceHelper.getDrawable(R.drawable.events_button_background));
            }
        }, 1000);
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.refer:

                fragmentUtility.replaceFragment((Activity) getContext(), new HotLeadsFragment(),R.id.fragment_container, true, "HotLeadsFragment");

                changeButtonStyle(mBinding.refer);

                break;
            case R.id.reject:




                showPopup(getContext(), "Rejected", "Thank you for your information, please\n" + "give us a reason why you are rejecting\n" + "this Hot Lead.", true, HotLeadActions.Reject.getNumVal());


                changeButtonStyle(mBinding.reject);

                break;
            case R.id.accept:

                showPopup(getContext(), "Accepted", "Thank you for accepting the Hot Lead", false, HotLeadActions.Accept.getNumVal());

                changeButtonStyle(mBinding.accept);

                HotLeadsController.acceptLead(getContext(),PreferencesHelper.getSalesCode(getContext()), mLead.getLeadId());

                AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LeadAccepted.getNumVal(), generateHotLeadsJson(String.valueOf(mLead.getLeadId()), PreferencesHelper.getSalesCode(getContext()))));


                break;
        }
    }
}
