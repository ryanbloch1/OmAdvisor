package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ProfileItemBinding;
import com.cobi.puresurveyandroid.model.ProfileItem;
import com.cobi.puresurveyandroid.model.ProfileItem;

import java.util.List;

public class CustomerProfileAdapter extends RecyclerView.Adapter<CustomerProfileAdapter.CustomerProfileViewHolder> {

    private ProfileItem[] items;
    Context context;
    private CustomerProfileAdapter.onItemSelected listener;
    private boolean showFull;


    public interface onItemSelected {
        void onItemSelected(ProfileItem pItem);
    }



    public CustomerProfileAdapter(ProfileItem[] items, Context context, onItemSelected listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setShowFull(boolean showFull) {
        this.showFull = showFull;
    }

    @NonNull
    @Override
    public CustomerProfileAdapter.CustomerProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        ProfileItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.profile_item, viewGroup, false);

        return new CustomerProfileAdapter.CustomerProfileViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomerProfileViewHolder holder, final int position) {

        final ProfileItem profileItem;

        profileItem = items[position];



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemSelected(profileItem);
            }
        });

        holder.bind(profileItem, position);
    }

    @Override
    public int getItemCount() {

        if(showFull){
            return items.length;
        }
        else {
            return 4;
        }


    }

    public class CustomerProfileViewHolder extends RecyclerView.ViewHolder {

        ProfileItemBinding mBinding;
        ProfileItem profileItem;
        int position;

        public CustomerProfileViewHolder(ProfileItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final ProfileItem profileItem, final int position) {

            this.profileItem = profileItem;

            this.position = position;

            mBinding.image.setImageResource(profileItem.getImage());

            mBinding.title.setText(profileItem.getTitle());


        }


    }




}
