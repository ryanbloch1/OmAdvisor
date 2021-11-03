package com.cobi.puresurveyandroid.model.ViewModel;

import com.cobi.puresurveyandroid.model.Agenda;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.model.SingleEvent;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModelEventDetails extends ViewModel {

    private final MutableLiveData<SingleEvent> event = new MutableLiveData<>();

    public MutableLiveData<Event> getEventMutableLiveData() {
        return eventMutableLiveData;
    }

    public void setEventMutableLiveData(Event event) {
        this.eventMutableLiveData.setValue(event);
    }

    private  MutableLiveData<Event> eventMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<Agenda> agenda = new MutableLiveData<>();

    private int selectedImage;

    public int getEventDetailsTabPosition() {
        return eventDetailsTabPosition;
    }

    public void setEventDetailsTabPosition(int eventDetailsTabPosition) {
        this.eventDetailsTabPosition = eventDetailsTabPosition;
    }

    private int eventDetailsTabPosition;

    private List<Agenda> agendaList;

    private List<Medium> media;

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public void setEvent(SingleEvent singleEvent) {
        this.event.setValue(singleEvent);
    }

    public MutableLiveData<SingleEvent> getEvent() {
        return event;
    }

    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    public MutableLiveData<Agenda> getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda.setValue(agenda);
    }

    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }
}
