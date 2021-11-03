package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.RowItemHelpBinding;
import com.cobi.puresurveyandroid.model.sales.Help;
import com.cobi.puresurveyandroid.viewHolder.HelpViewHolder;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Help> mHelp;
    private HelpAdapterOnClickHandler mClickHandler;

    public HelpAdapter(@NonNull Context context, List<Help> helps, HelpAdapterOnClickHandler clickHandler) {
        mContext = context;
        mHelp = helps;
        mClickHandler = clickHandler;
    }

    public void setClickHandler(HelpAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        RowItemHelpBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_item_help, null, false);

        return new HelpViewHolder(mBinding, mClickHandler);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        HelpViewHolder itemHolder = (HelpViewHolder) holder;
        itemHolder.bind(mHelp.get(holder.getAdapterPosition()), getItemCount());
    }

    @Override
    public int getItemCount() {
        return mHelp == null ? 0 : mHelp.size();
    }

    public interface HelpAdapterOnClickHandler {
        void onClick(Help help);
    }
}