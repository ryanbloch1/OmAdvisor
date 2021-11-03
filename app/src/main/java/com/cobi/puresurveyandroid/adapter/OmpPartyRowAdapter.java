package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.OmpPartyNameRowBinding;
import com.cobi.puresurveyandroid.databinding.OmpPartyNameRowBinding;
import com.cobi.puresurveyandroid.model.Party;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class OmpPartyRowAdapter  extends RecyclerView.Adapter<OmpPartyRowAdapter.OrReqViewHolder> {

    List<Party> items;
    Context context;
    private OnClientSelected listener;
    String role;


    public OmpPartyRowAdapter(List<Party> items, Context context,OnClientSelected listener, String role) {
        this.items = items;
        this.context = context;
        this.listener = listener;
        this.role = role;
    }


    public interface OnClientSelected{
        void OnClientSelected(Party p, String r);
    }





    @NonNull
    @Override
    public OmpPartyRowAdapter.OrReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        LayoutInflater inflater = LayoutInflater.from(this.context);

        OmpPartyNameRowBinding binding  = DataBindingUtil.inflate(inflater, R.layout.omp_party_name_row, parent, false);


        return new OmpPartyRowAdapter.OrReqViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OmpPartyRowAdapter.OrReqViewHolder myViewHolder, int i) {


        Party obj = items.get(i);

        myViewHolder.bind(obj);



    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }





    public class OrReqViewHolder extends RecyclerView.ViewHolder {

        private final OmpPartyNameRowBinding mBinding;

        public OrReqViewHolder(OmpPartyNameRowBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public  void bind(Party obj) {



            mBinding.setData(WordUtils.capitalize(obj.getLastName().toLowerCase()) +", " +  WordUtils.capitalize(obj.getFirstName().toLowerCase()));


            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listener.OnClientSelected(obj, role);

                }
            });




            mBinding.executePendingBindings();
        }

        public OmpPartyNameRowBinding getProductBinding() {
            return mBinding;
        }

    }
}
