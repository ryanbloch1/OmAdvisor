package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.ReIntermediary;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.FragmentUtility;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

public class ReinterLeadsAdapter extends RecyclerView.Adapter<ReinterLeadsAdapter.MyViewHolder> {

    private List<ReIntermediary> leads;
    private Context context;
    ClientViewModel clientViewModel;
    private onLeadSelected listener;
    PrettyTime prettyTime = new PrettyTime();


    public ReinterLeadsAdapter(List<ReIntermediary> leads, Context context, onLeadSelected listener) {
        this.leads = leads;
        this.context = context;
        this.listener = listener;
    }

    public interface onLeadSelected {
        void onLeadSelected(ReIntermediary lead);
    }


    @NonNull
    @Override
    public ReinterLeadsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        clientViewModel = ViewModelProviders.of((FragmentActivity) context).get(ClientViewModel.class);

        View itemView = LayoutInflater.from(context).inflate(R.layout.row_item_lead, parent, false);
        return new ReinterLeadsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReinterLeadsAdapter.MyViewHolder holder, final int position) {

        holder.lead.setText(leads.get(position).getFirstName()+ " "+leads.get(position).getLastName());

        holder.leadState.setVisibility(View.VISIBLE);

        if (leads.get(position).getAction() != 0) {

            holder.leadState.setText("Accepted");
            holder.rootItem.setBackgroundColor(ResourceHelper.getColour(R.color.white));

        } else {


            if(leads.get(position).getActionDate() != null){
//                String text = prettyTime.format(DateHelper.StringToDate(leads.get(position).getActionDate()));

                try {
                    holder.leadState.setText("Not yet actioned");
                }catch (Exception e){
                    holder.leadState.setText("Not yet actioned");
                }
            }

            holder.leadState.setText("Not yet actioned");


            holder.rootItem.setBackgroundColor(ResourceHelper.getColour(R.color.graphEmpty));

        }


        holder.rootItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onLeadSelected(leads.get(position));


            }
        });

    }

    @Override
    public int getItemCount() {
        return leads.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lead;
        public RelativeLayout rootItem;
        public TextView leadState;

        public MyViewHolder(View view) {
            super(view);
            lead = view.findViewById(R.id.leadName);
            rootItem = view.findViewById(R.id.layoutCustomerRowLead);
            leadState = view.findViewById(R.id.hotLeadInfo);
        }
    }
}
