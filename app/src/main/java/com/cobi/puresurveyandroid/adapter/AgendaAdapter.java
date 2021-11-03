package com.cobi.puresurveyandroid.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.AgendaRowBinding;
import com.cobi.puresurveyandroid.model.Agenda;
import com.cobi.puresurveyandroid.model.SingleEvent;

import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {

    private List<Agenda> agendas;
    Context context;
    private SingleEvent event;
    private rateSpeaker listener;

    public AgendaAdapter(List<Agenda> agendas, Context context, SingleEvent event, rateSpeaker listener) {
        this.agendas = agendas;
        this.context = context;
        this.event = event;
        this.listener = listener;
    }

    public void clearItems() {
        agendas.clear();
        notifyDataSetChanged();
    }

    public interface rateSpeaker {
        void onAgendaRatingSelected(Agenda agenda);
    }

    @NonNull
    @Override
    public AgendaAdapter.AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        final AgendaRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.agenda_row, viewGroup, false);

        return new AgendaAdapter.AgendaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaAdapter.AgendaViewHolder agendaViewHolder, final int position) {

        final Agenda agenda = agendas.get(position);

        AgendaRowBinding binding = agendaViewHolder.getAgendaBinding();

        agendaViewHolder.bind(agenda, event);

        agendaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean expanded = agenda.isExpanded();

                agenda.setExpanded(!expanded);

                notifyItemChanged(position);
            }
        });

        if (position == getItemCount() - 1) {
            binding.leftBorder.setVisibility(View.GONE);
            binding.divider.setVisibility(View.GONE);
        } else {
            binding.leftBorder.setVisibility(View.VISIBLE);
            binding.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (agendas != null) {
            return agendas.size();
        } else {
            return 0;
        }
    }

    public class AgendaViewHolder extends RecyclerView.ViewHolder {

        private final AgendaRowBinding binding;

        public AgendaViewHolder(AgendaRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Agenda agenda, SingleEvent event) {

            boolean expanded = agenda.isExpanded();

            binding.detailExtension.setVisibility(expanded ? View.VISIBLE : View.GONE);

            this.binding.setAgendaData(agenda);

            this.binding.setEventData(event);

            if (agenda.isStarted() && event.isLive() || event.isEndedButActive()) {
                this.binding.rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onAgendaRatingSelected(agenda);
                    }
                });
            }
        }

        public AgendaRowBinding getAgendaBinding() {
            return binding;
        }
    }
}