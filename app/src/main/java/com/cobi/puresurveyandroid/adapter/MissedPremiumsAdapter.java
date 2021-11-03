package com.cobi.puresurveyandroid.adapter;

import android.app.Activity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.databinding.MissedPremiumCustomerRowBinding;
import com.cobi.puresurveyandroid.fragment.ContractDetailsFragment;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.util.FragmentUtility;
import com.cobi.puresurveyandroid.util.PreferencesHelper;

import java.util.List;

public class MissedPremiumsAdapter  extends RecyclerView.Adapter<MissedPremiumsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<MissedPremium> contractingParties;
    private Context context;
    FragmentUtility fragmentUtility = new FragmentUtility();
    ClientViewModel clientViewModel;

    public MissedPremiumsAdapter(List<MissedPremium> contractingParties, Context context) {
        this.contractingParties = contractingParties;
        this.context = context;
    }

    public void clearItems(){
        contractingParties.clear();
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final MissedPremiumCustomerRowBinding clientBinding;

        public MyViewHolder(MissedPremiumCustomerRowBinding clientBinding) {
            super(clientBinding.getRoot());
            this.clientBinding = clientBinding;
        }

        public void bind(MissedPremium client) {
            this.clientBinding.setData(client);
            clientBinding.executePendingBindings();
        }

        public MissedPremiumCustomerRowBinding getClientBinding(){
            return clientBinding;
        }
    }

    @Override
    public MissedPremiumsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        clientViewModel = ViewModelProviders.of((FragmentActivity) context).get(ClientViewModel .class);

        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }

        MissedPremiumCustomerRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.missed_premium_customer_row, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MissedPremiumsAdapter.MyViewHolder holder, final int position) {

        final MissedPremium contractingParty;

        contractingParty = contractingParties.get(position);

        holder.bind(contractingParty);

        MissedPremiumCustomerRowBinding dataBinding = holder.getClientBinding();

        if(contractingParties != null ){
            if(contractingParties.get(0).getCurrency().equals("ZAR")){
                dataBinding.setCurrency("R");
            }
            else if(contractingParties.get(0).getCurrency().equals("NAD")){
                dataBinding.setCurrency("$N");
            }
            else{
                dataBinding.setCurrency("R");
            }
        }
        else{
            dataBinding.setCurrency("R");
        }

        dataBinding.layoutCustomerRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clientViewModel.select(contractingParty);

                PreferencesHelper.setMissedPContractNo(context, contractingParty.getContractNumber());

                Fragment fragment = new ContractDetailsFragment();
                fragmentUtility.replaceFragment((Activity) context, fragment, R.id.fragment_container, true, "ContractDetailsFragment");
                if(context instanceof BaseActivity) {
                    ((BaseActivity) context).decideWhetherToShowBackButton(fragment);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        if(contractingParties!=null){
            return contractingParties.size();
        }
        else{
            return  0;
        }

    }
}
