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
import com.cobi.puresurveyandroid.model.Notification;
import com.cobi.puresurveyandroid.model.NotificationOption;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    Context context;
    private NotificationAdapter.onNotificationOptionChecked listener;

    public NotificationAdapter(List<Notification> notifications, Context context, NotificationAdapter.onNotificationOptionChecked listener) {
        this.notifications = notifications;
        this.context = context;
        this.listener = listener;
    }


    public interface onNotificationOptionChecked {
        void onNotificationOptionChecked(Notification option, boolean checked);
    }

    public interface onMoreInfoSelected {
        void onMoreInfoSelected(String info, View view);
    }

    public void clearItems() {
        notifications.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        NotificationOptionRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.notification_option_row, viewGroup, false);

        return new NotificationAdapter.NotificationViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, final int position) {
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

        NotificationOptionRowBinding mBinding;
        Notification option;
        int position;

        public NotificationViewHolder(NotificationOptionRowBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Notification option, final int position) {

            this.option = option;

            this.position = position;

            mBinding.setData(option);

            mBinding.notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    listener.onNotificationOptionChecked(notifications.get(position), b);
                }
            });

        }
    }
}
