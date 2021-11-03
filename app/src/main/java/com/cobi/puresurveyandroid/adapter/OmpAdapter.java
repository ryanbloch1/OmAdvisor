package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.PipelineCustomerRowBinding;
import com.cobi.puresurveyandroid.model.Party;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class OmpAdapter extends RecyclerView.Adapter<OmpAdapter.PipelineViewHolder> {


    private List<Party> contractingParties;
    Context context;
    private OnOmpItemSelected listener;

    public OmpAdapter(List<Party> contractingParties, Context context, OnOmpItemSelected listener) {
        this.contractingParties = contractingParties;
        this.context = context;
        this.listener = listener;
    }

    public interface OnOmpItemSelected{
        void OnOmpItemSelected(Party contractingParty);
    }

    public void clearItems(){
        contractingParties.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PipelineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(this.context);

        PipelineCustomerRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.pipeline_customer_row, viewGroup, false);

        return new PipelineViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PipelineViewHolder pipelineViewHolder, int position) {

        pipelineViewHolder.bind(contractingParties.get(position));
    }

    @Override
    public int getItemCount() {
        if(contractingParties!=null){
            return contractingParties.size();
        }
        else{
            return 0;
        }
    }

    public class PipelineViewHolder extends RecyclerView.ViewHolder {

        private final PipelineCustomerRowBinding binding;

        public PipelineViewHolder(PipelineCustomerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Party partyClient) {

            this.binding.setData(WordUtils.capitalize(partyClient.getLastName().toLowerCase()) +", " +  WordUtils.capitalize(partyClient.getFirstName().toLowerCase()));


            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnOmpItemSelected(partyClient);
                }
            });
        }

        public PipelineCustomerRowBinding
        getPipelineBinding(){
            return binding;
        }
    }
}