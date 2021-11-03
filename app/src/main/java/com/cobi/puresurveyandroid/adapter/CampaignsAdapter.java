package com.cobi.puresurveyandroid.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.CampaignsHeaderBinding;
import com.cobi.puresurveyandroid.databinding.RowItemCampaignBinding;
import com.cobi.puresurveyandroid.fragment.LeadsFragment;
import com.cobi.puresurveyandroid.model.Campaign;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.FragmentUtility;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.util.List;

public class CampaignsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Campaign> mLeads;
    FragmentUtility fragmentUtility = new FragmentUtility();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    ClientViewModel clientViewModel;

    public CampaignsAdapter(@NonNull Context context, List<Campaign> campaigns) {
        mContext = context;
        mLeads = campaigns;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        clientViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(ClientViewModel .class);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        RowItemCampaignBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_item_campaign, null, false);
        CampaignsHeaderBinding headerBinding = DataBindingUtil.inflate(inflater, R.layout.campaigns_header, null, false);

        if (viewType == TYPE_ITEM) {
            return new CampaignViewHolder(mBinding);
        } else if (viewType == TYPE_HEADER) {

            return new CampaignViewHolder(headerBinding);
        }
        else{
            return new CampaignViewHolder(mBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        CampaignViewHolder itemHolder = (CampaignViewHolder) holder;

        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                if(mLeads.get(position - 1).getPriority().equals("Hot")){

                    itemHolder.mBinding.getRoot().setBackgroundColor(ResourceHelper.getColour(R.color.graphEmpty));

                    itemHolder.mBinding.pTv.setTextColor(ResourceHelper.getColour(R.color.red));

                }
                else if(mLeads.get(position - 1).getPriority().equals("High")){
                    itemHolder.mBinding.pTv.setTextColor(ResourceHelper.getColour(R.color.red));
                }
                else if(mLeads.get(position - 1).getPriority().equals("Medium")){
                    itemHolder.mBinding.pTv.setTextColor(ResourceHelper.getColour(R.color.yellow));
                }
                else{
                    itemHolder.mBinding.pTv.setTextColor(ResourceHelper.getColour(R.color.forestGreen));
                }

                itemHolder.mBinding.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreferencesHelper.setCampaignId(mContext, mLeads.get(position - 1).getCampaignId());

                        if(mLeads.get(position - 1).getPriority().equals("Hot")){
                            PreferencesHelper.setIsHotLead(mContext, true);
                        }
                        else {
                            PreferencesHelper.setIsHotLead(mContext, false);
                        }

                        clientViewModel.setSelectedCampaign(mLeads.get(position - 1));

                        //need to set the campaign object somewhere.....set in viewmodel?
                        
                        fragmentUtility.addFragment((Activity) mContext, new LeadsFragment(),R.id.fragment_container, true, "LeadsFragment");
                    }
                });

                itemHolder.bindCampaign(mLeads.get(position - 1));

                break;
            case  TYPE_HEADER:
                if(mLeads != null){
                    itemHolder.bindHeader(getTotalLeads(mLeads));
                }
                else {
                    itemHolder.bindHeader(0);
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }else if (position > 0) {
            return TYPE_ITEM;
        }
        return TYPE_ITEM;
    }

    private int getTotalLeads(List<Campaign> list) {
        int totalLeads = 0;
        for (Campaign leads : list) {
            totalLeads += leads.getLoadedLeads();
        }

        return totalLeads;
    }

    @Override
    public int getItemCount() {
        return mLeads == null ? 1 : mLeads.size() + 1;
    }

    public interface LeadsAdapterOnClickHandler {
        void onClick(Campaign campaign);
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {

        public RowItemCampaignBinding mBinding;
        public CampaignsHeaderBinding headerBinding;


        public CampaignViewHolder(RowItemCampaignBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public CampaignViewHolder(CampaignsHeaderBinding binding) {
            super(binding.getRoot());
            headerBinding = binding;
        }


        public void bindCampaign(Campaign campaign) {
            mBinding.setData(campaign);
        }

        public void bindHeader(int totalLeads) {
            headerBinding.setTotalLead(totalLeads);
        }
    }
}