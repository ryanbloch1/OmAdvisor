package com.cobi.puresurveyandroid.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.PipelineProposalBinding;
import com.cobi.puresurveyandroid.model.PipelineProduct;
import com.cobi.puresurveyandroid.model.Requirement;
import com.cobi.puresurveyandroid.model.RequirementGroup;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

public class PipelineProductsAdapter extends RecyclerView.Adapter<PipelineProductsAdapter.MyViewHolder> {

    List<PipelineProduct> products;
    Context context;

    public PipelineProductsAdapter(List<PipelineProduct> products, Context context) {
        this.products = products;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        PipelineProposalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.pipeline_proposal, parent, false);


        return new PipelineProductsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PipelineProductsAdapter.MyViewHolder myViewHolder, int i) {
        final PipelineProduct product;

        product = products.get(i);

        PipelineProposalBinding binding = myViewHolder.getProductBinding();

        Typeface typefaceMontB = ResourceHelper.getFont(R.font.montserrat_bold);
        Typeface typefaceMontR = ResourceHelper.getFont(R.font.montserrat_regular);

        if (binding.outstandingLayout.getChildCount() > 0) {
            binding.outstandingLayout.removeAllViews();
        }

        if (product.getContractNumber() == null && product.getProposalNumber() == null) {

            binding.setNumber(context.getString(R.string.not_available));

        } else {
            if (product.getProposalNumber() != null && product.getContractNumber() != null) {
                binding.setNumber(product.getContractNumber().toString());
            } else if (product.getContractNumber() != null) {
                binding.setNumber(product.getContractNumber().toString());
            } else {
                binding.setNumber(product.getProposalNumber());
            }
        }


        List<RequirementGroup> orderedList = new ArrayList<>();

        for(int in = 0 ; in< product.getRequirementGroup().size(); in++){

            for(int j = 0 ; j< product.getRequirementGroup().size(); j++){
                if(in == 0){
                    if(product.getRequirementGroup().get(j).getName().contains("documents")){

                        orderedList.add(product.getRequirementGroup().get(j));

                        break;


                    }
                }
                else if(in == 1){
                    if(product.getRequirementGroup().get(j).getName().contains("underwriting")){


                        orderedList.add(product.getRequirementGroup().get(j));

                        break;


                    }
                }
                else if (in == 2){
                    if(!product.getRequirementGroup().get(j).getName().contains("underwriting") && !product.getRequirementGroup().get(j).getName().contains("documents")){
                        orderedList.add(product.getRequirementGroup().get(j));

                    }

                }
                else{
                    break;
                }

            }

        }


        for (RequirementGroup requirementGroup : orderedList) {

            TextView requirementHeading = new TextView(context);

            requirementHeading.setTypeface(typefaceMontB);

            requirementHeading.setText(requirementGroup.getName() + "~");

            requirementHeading.setTextSize(13);

            requirementHeading.setTextColor(ResourceHelper.getColour(R.color.black));

            binding.outstandingLayout.addView(requirementHeading);

            if (requirementGroup.getRequirements() != null) {


                for (Requirement requirement : requirementGroup.getRequirements()) {


                    TextView reqText = new TextView(context);

                    reqText.setTextSize(13);

                    reqText.setTextColor(ResourceHelper.getColour(R.color.black));

                    reqText.setTypeface(typefaceMontR);

                    if (TextUtils.isEmpty(requirement.getReason())) {
                        reqText.setText(requirement.getName() + " - ");
                    } else {
                        reqText.setText(requirement.getName() + " - " + requirement.getReason());
                    }

                    binding.outstandingLayout.addView(reqText);

                }
            }
            else{
                TextView reqText = new TextView(context);

                reqText.setTextSize(13);

                reqText.setTextColor(ResourceHelper.getColour(R.color.black));

                reqText.setTypeface(typefaceMontR);

                reqText.setText("No Requirements");

                binding.outstandingLayout.addView(reqText);
            }

        }

        myViewHolder.bind(product);

    }

    @Override
    public int getItemCount() {
        if (products != null) {
            return products.size();
        } else {
            return 0;
        }
    }

    public void clearItems() {
        products.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final PipelineProposalBinding mBinding;

        public MyViewHolder(PipelineProposalBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(PipelineProduct product) {
            this.mBinding.setData(product);
            mBinding.executePendingBindings();
        }

        public PipelineProposalBinding getProductBinding() {
            return mBinding;
        }

    }
}
