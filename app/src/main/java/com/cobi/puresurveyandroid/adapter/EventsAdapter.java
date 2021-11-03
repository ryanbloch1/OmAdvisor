package com.cobi.puresurveyandroid.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.EventRowBinding;
import com.cobi.puresurveyandroid.databinding.EventsSectionHeadingBinding;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.util.DateHelper;

import java.util.Calendar;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Event> events;
    Context context;
    private OnEventSelected listener;
    int h2Pos;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    int liveSize;
    int secondSectionSize;

    public EventsAdapter(List<Event> events, Context context, OnEventSelected listener, int h2Pos, int liveSize, int secondSectionSize) {
        this.events = events;
        this.context = context;
        this.listener = listener;
        this.h2Pos = h2Pos;
        this.liveSize = liveSize;
        this.secondSectionSize = secondSectionSize;
    }

    public EventsAdapter(List<Event> events, Context context, OnEventSelected listener, int h2Pos) { //no second section/ header h2pos will be 0
        this.events = events;
        this.context = context;
        this.listener = listener;
        this.h2Pos = h2Pos;
    }

    public interface OnEventSelected {
        void onEventSelected(Event event, Boolean openQr);
    }

    public void clearItems() {
        events.clear();
        notifyDataSetChanged();
}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        if (i == TYPE_ITEM) {
            EventRowBinding rowBinding = DataBindingUtil.inflate(inflater, R.layout.event_row, viewGroup, false);

            return new EventsViewHolder(rowBinding);
        } else if (i == TYPE_HEADER) {

            EventsSectionHeadingBinding binding = DataBindingUtil.inflate(inflater, R.layout.events_section_heading, viewGroup, false);

            return new EventListHeadingViewHolder(binding);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof EventListHeadingViewHolder) {

            if (position == 0) {
                if (!events.isEmpty()) {
                    if (events.get(position).isLive()) {

                        if (h2Pos == 0) {
                            ((EventListHeadingViewHolder) holder).bind(events.get(position), events.size());
                        } else {
                            ((EventListHeadingViewHolder) holder).bind(events.get(position), liveSize);
                        }
                    } else {
                        if (h2Pos == 0) {
                            ((EventListHeadingViewHolder) holder).bind(events.get(position), events.size());
                        } else {
                            ((EventListHeadingViewHolder) holder).bind(events.get(position), secondSectionSize);
                        }
                    }
                }
            } else {
                ((EventListHeadingViewHolder) holder).bind(events.get(position - 1), secondSectionSize);
            }
        } else if (holder instanceof EventsViewHolder) {
            ((EventsViewHolder) holder).bind(getItem(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        if (position == 0) {
            return true;
        } else if (h2Pos != 0) {
            if (position == h2Pos) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private Event getItem(int position) {

        if (events != null && !events.isEmpty()) {
            if (h2Pos != 0 && position > h2Pos) {
                return events.get(position - 2);
            } else {
                return events.get(position - 1);
            }
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (events != null && !events.isEmpty()) {

            if (h2Pos != 0) {
                return events.size() + 2;
            } else {
                return events.size() + 1;
            }
        } else {
            return 0;
        }
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        private final EventRowBinding binding;

        public EventsViewHolder(EventRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Event event) {
            this.binding.setData(event);
            binding.executePendingBindings();

            if (!event.getMedia().isEmpty()) {
                Glide.with(context).load(event.getMedia().get(0).getUrl()).into(this.binding.eventPic);
            }

            binding.checkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventSelected(event, true);
                }
            });

            binding.eventBanner.setOnClickListener(new View.OnClickListener() {    //when clicking on the whole event
                @Override
                public void onClick(View v) {
                    listener.onEventSelected(event, false);
                }
            });
        }
    }

    public class EventListHeadingViewHolder extends RecyclerView.ViewHolder {

        EventsSectionHeadingBinding mBinding;

        public EventListHeadingViewHolder(EventsSectionHeadingBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Event event, int eventCount) {
            mBinding.setEvent(event);
            mBinding.setEventCount(eventCount);

            if (event.isLive()) {
                mBinding.headingText.setText("Live Event(s)");
            } else if (event.isPassed()) {
                mBinding.headingText.setText("Passed Event(s)");
            } else {
                mBinding.headingText.setText("Upcoming Event(s)");
            }
        }
    }
}