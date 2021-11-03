package com.cobi.puresurveyandroid.viewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.cobi.puresurveyandroid.adapter.HelpAdapter;
import com.cobi.puresurveyandroid.databinding.RowItemHelpBinding;
import com.cobi.puresurveyandroid.model.sales.Help;

public class HelpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RowItemHelpBinding mBinding;
    private HelpAdapter.HelpAdapterOnClickHandler mClickHandler;

    public HelpViewHolder(RowItemHelpBinding binding, HelpAdapter.HelpAdapterOnClickHandler onClickHandler) {
        super(binding.getRoot());
        mBinding = binding;
        mClickHandler = onClickHandler;
        mBinding.getRoot().setOnClickListener(this);
    }

    public void bind(Help help, int count) {
        mBinding.setData(help);

        if (getAdapterPosition() == count - 1) {
            mBinding.view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (mClickHandler != null) {
            mClickHandler.onClick(mBinding.getData());
        }
    }
}