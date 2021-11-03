
package com.cobi.puresurveyandroid.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.fragment.ContactDetailsFragment2;
import com.cobi.puresurveyandroid.fragment.HotLeadsFragment;
import com.cobi.puresurveyandroid.fragment.HotLeadsMainFragment;
import com.cobi.puresurveyandroid.model.Lead;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.FragmentUtility;
import com.cobi.puresurveyandroid.util.PreferencesHelper;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.util.List;

public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.MyViewHolder> {

  private List<Lead> leads;
  private Context context;
  private FragmentUtility fragmentUtility = new FragmentUtility();
  private static final int TYPE_HEADER = 0;
  private static final int TYPE_ITEM = 1;
  ClientViewModel clientViewModel;
  PrettyTime prettyTime = new PrettyTime();

  public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView lead;
    public RelativeLayout rootItem;
    public LinearLayout header;
    public TextView leadState;

    public MyViewHolder(View view) {
      super(view);
      lead = view.findViewById(R.id.leadName);
      rootItem = view.findViewById(R.id.layoutCustomerRowLead);
      header = view.findViewById(R.id.leads_header);
      leadState = view.findViewById(R.id.hotLeadInfo);
    }
  }

  public LeadsAdapter(List<Lead> leads, Context context) {
    this.leads = leads;
    this.context = context;
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return TYPE_HEADER;
    } else if (position > 0) {
      return TYPE_ITEM;
    }
    return TYPE_ITEM;
  }

  @Override
  public LeadsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    clientViewModel = ViewModelProviders.of((FragmentActivity) context).get(ClientViewModel.class);

    if (viewType == TYPE_ITEM) {
      View itemView = LayoutInflater.from(context).inflate(R.layout.row_item_lead, parent, false);
      return new LeadsAdapter.MyViewHolder(itemView);
    } else if (viewType == TYPE_HEADER) {
      View itemView = LayoutInflater.from(context).inflate(R.layout.leads_header, parent, false);

      if (PreferencesHelper.getIsHotLead(context)) {
        TextView view = itemView.findViewById(R.id.text1);
        view.setText("My Hot Leads");
      }

      return new LeadsAdapter.MyViewHolder(itemView);
    } else {
      return new LeadsAdapter.MyViewHolder(null);
    }
  }

  @Override
  public void onBindViewHolder(LeadsAdapter.MyViewHolder holder, final int position) {

    switch (getItemViewType(position)) {
      case TYPE_ITEM:
        //bind item data
        holder.lead.setText(leads.get(position - 1).getFullName());

      if (PreferencesHelper.getIsHotLead(context)) {

        holder.leadState.setVisibility(View.VISIBLE);

        if(leads.get(position -1).getCurrentState().equals("Accepted")){

          holder.leadState.setText("Accepted");

        }
        else{
          if(!leads.get(position -1).getLeadExpiryDate().equals("")){
            String text = prettyTime.format(DateHelper.StringToDate(leads.get(position -1).getLeadExpiryDate()));

            try {

              String htmlText = "Expires in " +"<b>" +text.replace("from now", "")+ "</b>";


              holder.leadState.setText(Html.fromHtml(htmlText));
            }catch (Exception e){

            }
          }
          else {
            holder.leadState.setText("No expiry date");
          }
        }

      }

        holder.rootItem.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            //select lead in viewmodel

            clientViewModel.setSelectedLead(leads.get(position - 1));

//            PreferencesHelper.setGcsId(context, leads.get(position - 1).getGCSId());

            PreferencesHelper.setCustomerFullName(context, leads.get(position - 1).getFullName());

            Fragment fragment = new ContactDetailsFragment2();
            fragmentUtility.replaceFragment((Activity) context, PreferencesHelper.getIsHotLead(context) && !leads.get(position - 1).getCurrentState().equals("Accepted") ? new HotLeadsMainFragment() :fragment, R.id.fragment_container, true, PreferencesHelper.getIsHotLead(context) ? "HotLeadsFragmentMain" : "ContactDetailsFragment");
            if (context instanceof BaseActivity) {
              ((BaseActivity) context).decideWhetherToShowBackButton(fragment);
            }
          }
        });
        break;
    }
  }

  private static float convertPixelsToDp(float px, Context context){
    return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
  }

  @Override
  public int getItemCount() {
    if (leads != null) {
      return leads.size() + 1;
    } else {
      return 0;
    }
  }
}
