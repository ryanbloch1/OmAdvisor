package com.cobi.puresurveyandroid.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.AgendaAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentAgendaBinding;
import com.cobi.puresurveyandroid.model.Agenda;
import com.cobi.puresurveyandroid.model.AgendaResponse;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.model.ViewModel.SharedViewModelEventDetails;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class AgendaFragment extends BaseFragment implements AgendaAdapter.rateSpeaker {

    private FragmentAgendaBinding mBinding;
    private AgendaAdapter mAdapter;
    private SharedViewModelEventDetails sharedViewModelEventDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_agenda, container, false);

        OMEventsApiController.getAgenda(getContext(), PreferencesHelper.getEventId(getContext()));

        sharedViewModelEventDetails = ViewModelProviders.of(getActivity()).get(SharedViewModelEventDetails.class);

        sharedViewModelEventDetails.getEvent().observe(getViewLifecycleOwner(), new Observer<SingleEvent>() {
            @Override
            public void onChanged(SingleEvent event) {
                mBinding.setEvent(event);

                if (sharedViewModelEventDetails.getAgendaList() != null && !sharedViewModelEventDetails.getAgendaList().isEmpty() && sharedViewModelEventDetails.getEvent() != null) {
                    populateAdapter(sharedViewModelEventDetails.getAgendaList(), event);
                }
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        registerEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterEventBus();
    }

    public void populateAdapter(List<Agenda> result, SingleEvent event) {
        mAdapter = new AgendaAdapter(result, getContext(), event, this);
        mBinding.agendaList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.agendaList.setHasFixedSize(false);
        mBinding.agendaList.setAdapter(mAdapter);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(final AgendaResponse response) {

        if (response instanceof AgendaResponse) {

            final List<Agenda> resultList = response.getAgendaList();

            if (!resultList.isEmpty()) {
                sharedViewModelEventDetails.setAgendaList(resultList);

                String speakers = "";

                for (int i = 0; i < resultList.size(); i++) {

                    if (i < resultList.size() - 1) {

                        speakers += resultList.get(i).getName() + ", ";
                    } else {
                        speakers += resultList.get(i).getName();
                    }
                }

                mBinding.setSpeakers(speakers);

                if (sharedViewModelEventDetails != null && sharedViewModelEventDetails.getEvent() != null) {

                    sharedViewModelEventDetails.getEvent().observe(getViewLifecycleOwner(), new Observer<SingleEvent>() {
                        @Override
                        public void onChanged(SingleEvent event) {
                            populateAdapter(resultList, event);

                            mBinding.setEvent(event);
                        }
                    });
                }
            } else {

                if(mAdapter != null){
                    mAdapter.clearItems();
                }

                mBinding.setSpeakers("No speakers yet for this event");
            }
        }
    }

    @Override
    public void onAgendaRatingSelected(Agenda agenda) {
        //open rate speaker fragment...setting a speaker id...
        sharedViewModelEventDetails.setAgenda(agenda);
        PreferencesHelper.setSpeakerId(getContext(), agenda.getId());
        PreferencesHelper.setRatingSpeaker(getContext(), true);
        showNestedFragmentCustom(new RateSessionFragment(), true);
    }
}