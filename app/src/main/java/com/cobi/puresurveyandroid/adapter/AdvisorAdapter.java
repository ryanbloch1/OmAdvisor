package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.AdvisorRowBinding;
import com.cobi.puresurveyandroid.databinding.EventRowBinding;
import com.cobi.puresurveyandroid.databinding.EventsSectionHeadingBinding;
import com.cobi.puresurveyandroid.model.Advisor;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AdvisorAdapter extends RecyclerView.Adapter<AdvisorAdapter.AdvisorViewHolder> {

    private List<Advisor> advisors;
    Context context;
    private onAdvisorSelected listener;
    int rowIndex = 0;


    public AdvisorAdapter(List<Advisor> advisors, Context context, AdvisorAdapter.onAdvisorSelected listener) {
        this.advisors = advisors;
        this.context = context;
        this.listener = listener;
    }


    public interface onAdvisorSelected {
        void onAdvisorSelected(Advisor advisor);
    }

    public void clearItems() {
        advisors.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdvisorAdapter.AdvisorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        AdvisorRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.advisor_row, viewGroup, false);

        return new AdvisorAdapter.AdvisorViewHolder(binding);

    }




    @Override
    public void onBindViewHolder(@NonNull AdvisorAdapter.AdvisorViewHolder holder, final int position) {
        final Advisor advisor;

        advisor = advisors.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onAdvisorSelected(advisor);

                int oldPos;

                if(rowIndex == 0){
                   oldPos = rowIndex;
                }else{
                    oldPos = rowIndex -1;
                }



                rowIndex = position +1;

                notifyItemChanged(position);

                notifyItemChanged(oldPos);

            }
        });

        if(rowIndex == 0){
            holder.itemView.setBackgroundColor(ResourceHelper.getColour(R.color.white));
        }
        else{
            if(rowIndex==position +1){
                holder.itemView.setBackgroundColor(ResourceHelper.getColour(R.color.graphEmpty));
            }
            else
            {
                holder.itemView.setBackgroundColor(ResourceHelper.getColour(R.color.white));
            }
        }







        holder.bind(advisor, position);
    }

    @Override
    public int getItemCount() {
        if (advisors != null && !advisors.isEmpty()) {

            return advisors.size();
        } else {
            return 0;
        }
    }



    public class AdvisorViewHolder extends RecyclerView.ViewHolder {

        AdvisorRowBinding mBinding;
        Advisor advisor;
        int position;

        public AdvisorViewHolder(AdvisorRowBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Advisor advisor, final int position) {

            this.advisor = advisor;

            this.position = position;

            mBinding.setData(advisor);


        }


    }
}
