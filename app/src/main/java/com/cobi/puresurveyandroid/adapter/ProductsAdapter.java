package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.MissedPremiumProductBinding;
import com.cobi.puresurveyandroid.model.MissedPremium;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    List<MissedPremium> products;
    Context context;

    public ProductsAdapter(List<MissedPremium> products, Context context) {
        this.products = products;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        MissedPremiumProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.missed_premium_product, parent, false);


        return new ProductsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.MyViewHolder myViewHolder, int i) {
        final MissedPremium product;

        product = products.get(i);

        myViewHolder.bind(product);

        MissedPremiumProductBinding dataBinding = myViewHolder.getProductBinding();

        if(i == products.size() - 1){
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) dataBinding.innerLayout.getLayoutParams();
            p.setMargins(0,0,0,0);
            dataBinding.innerLayout.setLayoutParams(p);
        }

        if(product != null ){
            if(products.get(0).getCurrency().equals("ZAR")){
                dataBinding.setCurrency("R");
            }
            else if(products.get(0).getCurrency().equals("NAD")){
                dataBinding.setCurrency("$N");
            }
            else{
                dataBinding.setCurrency("R");
            }
        }
        else{
            dataBinding.setCurrency("R");
        }

    }

    @Override
    public int getItemCount() {
        if(products!=null){
            return products.size();
        }
        else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final MissedPremiumProductBinding mBinding;

        public MyViewHolder(MissedPremiumProductBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(MissedPremium product){
            this.mBinding.setData(product);
            mBinding.executePendingBindings();
        }

        public MissedPremiumProductBinding getProductBinding() {return mBinding;}

    }
}
