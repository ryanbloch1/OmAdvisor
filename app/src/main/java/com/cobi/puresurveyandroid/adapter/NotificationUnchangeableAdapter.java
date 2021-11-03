package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.NotificationOptionRowBinding;
import com.cobi.puresurveyandroid.databinding.UnchageableNotificationBinding;
import com.cobi.puresurveyandroid.model.Notification;

import java.util.List;

public class NotificationUnchangeableAdapter extends RecyclerView.Adapter<NotificationUnchangeableAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    Context context;


    public NotificationUnchangeableAdapter(List<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;

    }

    public void clearItems() {
        notifications.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationUnchangeableAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        UnchageableNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.unchageable_notification, viewGroup, false);

        return new NotificationUnchangeableAdapter.NotificationViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationUnchangeableAdapter.NotificationViewHolder holder, final int position) {
        final Notification option;

        option = notifications.get(position);

        holder.bind(option, position);

        //changebackground color on notification and hide the triggers or create another view/ create another view or bind data..
    }

    @Override
    public int getItemCount() {
        if (notifications != null && !notifications.isEmpty()) {

            return notifications.size();
        } else {
            return 0;
        }
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        UnchageableNotificationBinding mBinding;
        Notification option;
        int position;

        public NotificationViewHolder(UnchageableNotificationBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Notification option, final int position) {

            this.option = option;

            this.position = position;

            mBinding.setData(option);


        }
    }
}
