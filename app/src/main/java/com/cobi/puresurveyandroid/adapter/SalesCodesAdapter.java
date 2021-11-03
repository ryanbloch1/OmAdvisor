package com.cobi.puresurveyandroid.adapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.SalesCodeModel;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.util.ArrayList;

public class SalesCodesAdapter extends RecyclerView.Adapter<SalesCodesAdapter.MyViewHolder> {

    private ArrayList<SalesCodeModel> salesCode;
    public SalesCodeSelectedListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sales_code, segmentChannel;
        public CardView rootItem;


        public MyViewHolder(View view) {
            super(view);
            sales_code = (TextView) view.findViewById(R.id.sales_code);
            segmentChannel = (TextView) view.findViewById(R.id.segment_channel);
            rootItem = (CardView) view.findViewById(R.id.layoutItem);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.listener = null;
    }

    public SalesCodesAdapter(ArrayList<SalesCodeModel> salesCode, SalesCodeSelectedListener listener) {
        this.salesCode = salesCode;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_codes_list_row, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder( final MyViewHolder holder, final int position) {

        holder.sales_code.setText(salesCode.get(position).getSalesCodeName());
        holder.segmentChannel.setText(salesCode.get(position).getSegmentChannelName());

        if(salesCode.size() == 0){
           listener.salesCodeSelected("");
        }

        if(salesCode.size() <= 1)
        {
            listener.salesCodeSelected(salesCode.get(position).getSalesCodeName());
        }
        else
        {
            holder.rootItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.salesCodeSelected(salesCode.get(position).getSalesCodeName());
                    holder.rootItem.setCardBackgroundColor(ResourceHelper.getColour(R.color.bottomNavigationSelected));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if(salesCode != null){
            return salesCode.size();
        }
        else return 0;

    }

   public interface SalesCodeSelectedListener{
        void salesCodeSelected(String code);
   }
}
