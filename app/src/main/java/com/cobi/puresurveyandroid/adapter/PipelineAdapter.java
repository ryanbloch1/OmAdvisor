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

import java.util.List;

public class PipelineAdapter extends RecyclerView.Adapter<PipelineAdapter.PipelineViewHolder> {


    private List<String> contractingParties;
    Context context;
    private LayoutInflater inflater;
    private OnPipelineSelected listener;

    public PipelineAdapter(List<String> contractingParties, Context context, OnPipelineSelected listener) {
        this.contractingParties = contractingParties;
        this.context = context;
        this.listener = listener;
    }

    public interface OnPipelineSelected{
        void onPipelineSelected(String contractingParty);
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

        String contractingParty = contractingParties.get(position);

        pipelineViewHolder.bind(contractingParty);
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

        public void bind(final String contractingParty) {
            this.binding.setData(contractingParty);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPipelineSelected(contractingParty);
                }
            });
        }

        public PipelineCustomerRowBinding
        getPipelineBinding(){
            return binding;
        }
    }
}