package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.RowItemCustomerEventsBinding;
import com.cobi.puresurveyandroid.model.Birthday;
import com.cobi.puresurveyandroid.util.FragmentUtility;

import java.util.List;

public class CustomerEventsAdapter extends RecyclerView.Adapter<CustomerEventsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Birthday> birthdays;
    private OnBirthdaySelected listener;
    private Context context;
    FragmentUtility fragmentUtility = new FragmentUtility();

    public interface OnBirthdaySelected{
       void onBirthdaySelected(Birthday selectedBirthday);
    }

    public CustomerEventsAdapter(List<Birthday> birthdays, Context context, OnBirthdaySelected listener) {
        this.birthdays = birthdays;
        this.context = context;
        this.listener = listener;
    }

    public void clearItems(){
        birthdays.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final RowItemCustomerEventsBinding clientBinding;

        public MyViewHolder(RowItemCustomerEventsBinding clientBinding) {
            super(clientBinding.getRoot());
            this.clientBinding = clientBinding;
        }

        public void bind(final Birthday client) {
            this.clientBinding.setData(client);
            clientBinding.executePendingBindings();
            clientBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBirthdaySelected(client);
                }
            });
        }

        public RowItemCustomerEventsBinding getClientBinding(){
            return clientBinding;
        }
    }

    @Override
    public CustomerEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }

        RowItemCustomerEventsBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_item_customer_events, parent, false);

        return new CustomerEventsAdapter.MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(CustomerEventsAdapter.MyViewHolder holder, final int position) {

        Birthday clientBdayThisMonth;

        clientBdayThisMonth = birthdays.get(position);

        holder.bind(clientBdayThisMonth);

        RowItemCustomerEventsBinding dataBinding = holder.getClientBinding();

        dataBinding.setT(position);


    }

    @Override
    public int getItemCount() {
        if(birthdays!=null){
            return birthdays.size();
        }
        else{
            return 0;
        }

    }
}