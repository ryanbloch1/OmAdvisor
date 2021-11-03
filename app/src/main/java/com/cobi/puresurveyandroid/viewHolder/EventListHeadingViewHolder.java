package com.cobi.puresurveyandroid.viewHolder;

import android.content.Context;
import android.view.View;

import com.cobi.puresurveyandroid.databinding.EventsSectionHeadingBinding;

import androidx.recyclerview.widget.RecyclerView;

public class EventListHeadingViewHolder extends RecyclerView.ViewHolder {

    EventsSectionHeadingBinding mBinding;

    public EventListHeadingViewHolder(EventsSectionHeadingBinding binding) {
        super(binding.getRoot());
        mBinding = binding;


    }

//    public void bind(Help help, int count) {
//        mBinding.setData(help);
//
////        if (getAdapterPosition() == count - 1) {
////            mBinding.view.setVisibility(View.GONE);
////        }
//    }


}


