package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.PartyRowBinding;
import com.cobi.puresurveyandroid.model.OutstandingRequirementRoleToClient;
import com.cobi.puresurveyandroid.model.Party;

import java.util.List;

public class OutstandingRequirementsAdapter extends RecyclerView.Adapter<OutstandingRequirementsAdapter.OrReqViewHolder> {

    List<OutstandingRequirementRoleToClient> items;
    Context context;
    private OmpPartyRowAdapter.OnClientSelected listener;


    public OutstandingRequirementsAdapter(List<OutstandingRequirementRoleToClient> items, Context context,OmpPartyRowAdapter.OnClientSelected listener ) {
        this.items = items;
        this.context = context;
        this.listener = listener;

    }


    @NonNull
    @Override
    public OrReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        LayoutInflater inflater = LayoutInflater.from(this.context);

        PartyRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.party_row, parent, false);


        return new OrReqViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrReqViewHolder myViewHolder, int i) {


        OutstandingRequirementRoleToClient obj = items.get(i);



        myViewHolder.bind(obj);

        



    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }



    public class OrReqViewHolder extends RecyclerView.ViewHolder {

        private final PartyRowBinding mBinding;

        public OrReqViewHolder(PartyRowBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public  void bind(OutstandingRequirementRoleToClient obj) {


            mBinding.role.setText(obj.getRole());



            OmpPartyRowAdapter adapter= new OmpPartyRowAdapter(obj.getClients(), context, listener, obj.getRole());
            mBinding.namesList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            mBinding.namesList.setHasFixedSize(true);
            mBinding.namesList.setAdapter(adapter);




            mBinding.executePendingBindings();
        }

        public PartyRowBinding getProductBinding() {
            return mBinding;
        }

    }
}
