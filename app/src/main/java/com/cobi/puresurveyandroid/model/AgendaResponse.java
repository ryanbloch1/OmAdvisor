package com.cobi.puresurveyandroid.model;

import java.util.List;

public class AgendaResponse {

    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    List<Agenda> agendaList;
}
