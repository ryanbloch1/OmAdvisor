package com.cobi.puresurveyandroid.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.SalesActivity;
import com.cobi.puresurveyandroid.adapter.CustomerProfileAdapter;
import com.cobi.puresurveyandroid.adapter.MaturitiesAdapter;
import com.cobi.puresurveyandroid.databinding.AffordabilityLayoutBinding;
import com.cobi.puresurveyandroid.databinding.CampaignRenterBinding;
import com.cobi.puresurveyandroid.databinding.CorpClientLayoutBinding;
import com.cobi.puresurveyandroid.databinding.CurrentHoldingsBinding;
import com.cobi.puresurveyandroid.databinding.CustomerEngagementBinding;
import com.cobi.puresurveyandroid.databinding.DemographicLayoutBinding;
import com.cobi.puresurveyandroid.databinding.FragmentCustomerProfileBinding;
import com.cobi.puresurveyandroid.databinding.NeedsMetAndOpBinding;
import com.cobi.puresurveyandroid.databinding.PeopleLikeYouBinding;
import com.cobi.puresurveyandroid.databinding.PopupContactReinterBinding;
import com.cobi.puresurveyandroid.databinding.RecommendedProductBinding;
import com.cobi.puresurveyandroid.databinding.ReinterProductLayoutBinding;
import com.cobi.puresurveyandroid.databinding.UpcomingMaturtiesBinding;
import com.cobi.puresurveyandroid.model.Affordability;
import com.cobi.puresurveyandroid.model.Campaigns;
import com.cobi.puresurveyandroid.model.Corporate;
import com.cobi.puresurveyandroid.model.Customer;
import com.cobi.puresurveyandroid.model.CustomerProfileEnum;
import com.cobi.puresurveyandroid.model.Engagement;
import com.cobi.puresurveyandroid.model.Holdings;
import com.cobi.puresurveyandroid.model.LikeYou;
import com.cobi.puresurveyandroid.model.Maturity;
import com.cobi.puresurveyandroid.model.Needs;
import com.cobi.puresurveyandroid.model.ProfileItem;
import com.cobi.puresurveyandroid.model.ReProduct;
import com.cobi.puresurveyandroid.model.Demographic;
import com.cobi.puresurveyandroid.model.ReIntermediary;
import com.cobi.puresurveyandroid.model.RecommendedProduct;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.ReinterApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerProfileFragment extends BaseFragment implements CustomerProfileAdapter.onItemSelected, View.OnClickListener {

    FragmentCustomerProfileBinding mBinding;
    private ClientViewModel clientViewModel;
    private ReIntermediary mLead;
    CustomerProfileAdapter mAdapter;
    ViewDataBinding childBinding = null;
    PopupContactReinterBinding contactBinding;
    Customer mCustomer;

    ProfileItem[] mData =
            {new ProfileItem(CustomerProfileEnum.Demographic, R.drawable.demographic, "Demographic"),
                    new ProfileItem(CustomerProfileEnum.Affordability, R.drawable.afford, "Affordability"),
                    new ProfileItem(CustomerProfileEnum.UpcomingMaturities, R.drawable.upcoming, "Upcoming Maturities"),
                    new ProfileItem(CustomerProfileEnum.CurrentHoldings, R.drawable.money, "Current Holdings"),
                    new ProfileItem(CustomerProfileEnum.CorpClient, R.drawable.corp, "Corporate Client"),
                    new ProfileItem(CustomerProfileEnum.NeedsAndOpportunities, R.drawable.needs, "Needs and Opportunities"),
                    new ProfileItem(CustomerProfileEnum.CustomerEngagement, R.drawable.cust_engage, "Customer Engagement"),
                    new ProfileItem(CustomerProfileEnum.Products, R.drawable.products, "Products"),
                    new ProfileItem(CustomerProfileEnum.RecommendedNextProduct, R.drawable.products, "Suggested Next Product"),
                    new ProfileItem(CustomerProfileEnum.Campaigns, R.drawable.campaigns, "Campaigns"),
                    new ProfileItem(CustomerProfileEnum.PeopleLikeYou, R.drawable.team, "People Like You"),};

    private void acceptLead() {
        ReinterApiController.PostAccept(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));
        mAdapter.setShowFull(true);
        mAdapter.notifyDataSetChanged();
        mBinding.acceptSection.setVisibility(View.GONE);
        mBinding.contactDet.setVisibility(View.VISIBLE);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_profile, container, false);
        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);




        mBinding.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReinterApiController.PostDecline(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                ReinterApiController.GetReIntermediaries(getContext(), PreferencesHelper.getSalesCode(getContext()));

                getActivity().onBackPressed();


            }
        });




        //call contact details api as well..
        mAdapter = new CustomerProfileAdapter(mData, getContext(), this);
        mAdapter.setShowFull(false);
        mBinding.grid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mBinding.grid.setHasFixedSize(true);
        mBinding.grid.setAdapter(mAdapter);


        if (clientViewModel.getSelectedReLead().getValue() != null) {
            ReIntermediary lead = clientViewModel.getSelectedReLead().getValue();
            mBinding.setData(lead);
            mLead = lead;

            if (mLead.getAction() != 0) {
                mBinding.acceptSection.setVisibility(View.GONE);
                mBinding.contactDet.setVisibility(View.VISIBLE);
                mAdapter.setShowFull(true);
            }

        }


        mBinding.contactDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ReinterApiController.GetCustomer(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                showContactDetails(container);

            }
        });


        mBinding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showThankYouPopUp();

            }
        });

        return mBinding.getRoot();
    }


    private void showContactDetails(ViewGroup container) {
        final Dialog dialog = new Dialog(getContext(), R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        contactBinding = DataBindingUtil.inflate(inflater, R.layout.popup_contact_reinter, container, false);

        contactBinding.setData(mCustomer);

        contactBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        contactBinding.layoutSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms(mCustomer.getCellNumber(), false, null);
            }
        });

        contactBinding.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsappConversation(mCustomer.getCellNumber(), "");

            }
        });


        contactBinding.homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(mCustomer.getHomeNumber(), null);
            }
        });

        contactBinding.cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(mCustomer.getCellNumber(), null);
            }
        });

        contactBinding.worknoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(mCustomer.getBusinessNumber(), null);
            }
        });


        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(contactBinding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 1000);


    }


    private void showThankYouPopUp() {

        final Dialog dialog = new Dialog(getContext(), R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        RelativeLayout popUp = (RelativeLayout) inflater.inflate(R.layout.thank_you_popup, null);

        ImageView close = popUp.findViewById(R.id.close);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptLead();
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(popUp);
        dialog.show();

    }


    public ViewDataBinding getLayout(int id, View view) {

        ViewDataBinding binding;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        binding = DataBindingUtil.inflate(inflater, id, (LinearLayout) view, false);


        return binding;
    }

    private void populateMaturitiessAdapter(List<Maturity> result, UpcomingMaturtiesBinding binding) {
        MaturitiesAdapter adapter = new MaturitiesAdapter(result, getContext());
        binding.list.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        binding.list.setAdapter(adapter);
    }


    private void showPopup(ProfileItem pItem) {

        final Dialog dialog = new Dialog(getContext(), R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout popUp = (LinearLayout) inflater.inflate(R.layout.reintermediation_popup, null);
        TextView title = popUp.findViewById(R.id.title);
        ImageView icon = popUp.findViewById(R.id.icon);

        ImageView close = popUp.findViewById(R.id.close);


        switch (pItem.getId()) {

            case Demographic:

                //call api to load data into popUp
                //for test

                childBinding = (DemographicLayoutBinding) getLayout(R.layout.demographic_layout, popUp);

                title.setText("Demographic");
                icon.setImageResource(R.drawable.demographic);


                ReinterApiController.GetDemographics(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));


                break;

            case Affordability:

                //call api to load data into popUp
                //for test

                childBinding = (AffordabilityLayoutBinding) getLayout(R.layout.affordability_layout, popUp);


                ReinterApiController.GetAffordability(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));


                title.setText("Affordability");
                icon.setImageResource(R.drawable.afford);


                break;

            case UpcomingMaturities:

                //call api to load data into popUp
                //for test
                childBinding = (UpcomingMaturtiesBinding) getLayout(R.layout.upcoming_maturties, popUp);


                ReinterApiController.GetMaturities(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));


                title.setText("Upcoming Maturities");
                icon.setImageResource(R.drawable.upcoming);

                break;

            case CurrentHoldings:

                //call api to load data into popUp
                //for test
                childBinding = (CurrentHoldingsBinding) getLayout(R.layout.current_holdings, popUp);

                ReinterApiController.GetHoldings(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));


                title.setText("Current Holdings");
                icon.setImageResource(R.drawable.money);

                break;


            case CorpClient:

                //call api to load data into popUp
                //for test
                childBinding = (CorpClientLayoutBinding) getLayout(R.layout.corp_client_layout, popUp);

                ReinterApiController.GetCorporate(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                title.setText("Corporate Client");
                icon.setImageResource(R.drawable.corp);

                break;

            case NeedsAndOpportunities:

                //call api to load data into popUp
                //for test
                childBinding = (NeedsMetAndOpBinding) getLayout(R.layout.needs_met_and_op, popUp);


                ReinterApiController.GetNeeds(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                title.setText("Needs and Opportunities");
                icon.setImageResource(R.drawable.needs);

                break;

            case CustomerEngagement:

                //call api to load data into popUp
                //for test
                childBinding = (CustomerEngagementBinding) getLayout(R.layout.customer_engagement, popUp);

                ReinterApiController.GetEngagement(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                title.setText("Customer Engagement");
                icon.setImageResource(R.drawable.cust_engage);

                break;

            case Products:

                //call api to load data into popUp
                //for test
                childBinding = (ReinterProductLayoutBinding) getLayout(R.layout.reinter_product_layout, popUp);


                ReinterProductLayoutBinding binding = (ReinterProductLayoutBinding) childBinding;


                ReinterApiController.GetProducts(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));


                binding.life.setOnClickListener(this);
                binding.funeral.setOnClickListener(this);
                binding.illness.setOnClickListener(this);
                binding.disability.setOnClickListener(this);
                binding.retirement.setOnClickListener(this);


                title.setText("Products");
                icon.setImageResource(R.drawable.products);

                break;

            case RecommendedNextProduct:

                //call api to load data into popUp
                //for test
                childBinding = (RecommendedProductBinding) getLayout(R.layout.recommended_product, popUp);

                ReinterApiController.GetRecommended(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                title.setText("Suggested Next\nProduct");
                icon.setImageResource(R.drawable.products);

                break;

            case Campaigns:

                //call api to load data into popUp
                //for test
                childBinding = (CampaignRenterBinding) getLayout(R.layout.campaign_renter, popUp);

                ReinterApiController.GetCampaigns(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                title.setText("Campaigns");
                icon.setImageResource(R.drawable.campaigns);

                break;


            case PeopleLikeYou:

                //call api to load data into popUp
                //for test
                childBinding = (PeopleLikeYouBinding) getLayout(R.layout.people_like_you, popUp);

                ReinterApiController.GetLikeYou(getContext(), mLead.getId(), PreferencesHelper.getSalesCode(getContext()));

                title.setText("People Like You");
                icon.setImageResource(R.drawable.team);

                break;


        }


        if (childBinding != null) {
            popUp.addView(childBinding.getRoot());
        }


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(popUp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 1000);



    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponseProd(List<ReProduct> response) {

         if (response.get(0) instanceof ReProduct) {
            ((ReinterProductLayoutBinding) childBinding).setData((ReProduct) response.get(0));
        }


    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(Object response) {

        if (response instanceof Demographic) {

            ((DemographicLayoutBinding) childBinding).setData((Demographic) response);
        } else if (response instanceof Affordability) {
            ((AffordabilityLayoutBinding) childBinding).setData((Affordability) response);
        } else if (response instanceof Engagement) {
            ((CustomerEngagementBinding) childBinding).setData((Engagement) response);
        } else if (response instanceof LikeYou) {
            ((PeopleLikeYouBinding) childBinding).setData((LikeYou) response);
        } else if (response instanceof Campaigns) {
            ((CampaignRenterBinding) childBinding).setData((Campaigns) response);
        } else if (response instanceof Holdings) {
            ((CurrentHoldingsBinding) childBinding).setData((Holdings) response);
        } else if (response instanceof Needs) {
            ((NeedsMetAndOpBinding) childBinding).setData((Needs) response);
        } else if (response instanceof Corporate) {
            ((CorpClientLayoutBinding) childBinding).setData((Corporate) response);
        } else if (response instanceof RecommendedProduct) {
            ((RecommendedProductBinding) childBinding).setData((RecommendedProduct) response);
        }  else if (response instanceof Customer) {
            contactBinding.setData((Customer) response);

            mCustomer = (Customer) response;

        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponseMat(List<Maturity> response) {

        if (response.get(0) instanceof Maturity) {


            ArrayList<Maturity> updated = new ArrayList<>();

            for(Maturity m : response){

                if(m.getProductType() !=null){
                    if(!m.getProductType().equals("")){
                        updated.add(m);
                    }
                }

            }

            populateMaturitiessAdapter(updated, (UpcomingMaturtiesBinding) childBinding);
        }

    }


    @Override
    public void onItemSelected(ProfileItem pItem) {
        //show popups depending on item
        showPopup(pItem);
    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.life:
                toggleVis(((ReinterProductLayoutBinding) childBinding).hiddenL);
                break;

            case R.id.funeral:
                toggleVis(((ReinterProductLayoutBinding) childBinding).hiddenF);
                break;

            case R.id.illness:
                toggleVis(((ReinterProductLayoutBinding) childBinding).hiddenIll);
                break;

            case R.id.retirement:
                toggleVis(((ReinterProductLayoutBinding) childBinding).hiddenRe);
                break;

            case R.id.disability:
                toggleVis(((ReinterProductLayoutBinding) childBinding).hiddenDis);
                break;

        }


    }
}
