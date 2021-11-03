package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.MaturityRowBinding;
import com.cobi.puresurveyandroid.model.Maturity;
import com.cobi.puresurveyandroid.model.ReProduct;

import java.util.List;


public class MaturitiesAdapter extends RecyclerView.Adapter<MaturitiesAdapter.MaturitiesViewHolder> {

    private List<Maturity> maturities;
    Context context;


    public MaturitiesAdapter(List<Maturity> maturities, Context context) {
        this.maturities = maturities;
        this.context = context;
    }


    public void clearItems() {
        maturities.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MaturitiesAdapter.MaturitiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        MaturityRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.maturity_row, viewGroup, false);

        return new MaturitiesAdapter.MaturitiesViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(@NonNull MaturitiesAdapter.MaturitiesViewHolder holder, final int position) {
        final Maturity maturity;

        maturity = maturities.get(position);

        holder.bind(maturity, position);
    }

    @Override
    public int getItemCount() {
        if (maturities != null && !maturities.isEmpty()) {

            return maturities.size();
        } else {
            return 0;
        }
    }


    public class MaturitiesViewHolder extends RecyclerView.ViewHolder {

        MaturityRowBinding mBinding;

        public MaturitiesViewHolder(MaturityRowBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Maturity maturity, final int position) {

            mBinding.setData(maturity);

        }
    }
}
