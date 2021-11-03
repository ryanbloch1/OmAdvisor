package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.model.Party;

import java.util.List;

public class OutstandingRequirementRoleToClient {


    String role;

    List<Party> clients;

    public OutstandingRequirementRoleToClient(String role, List<Party> clients) {
        this.role = role;
        this.clients = clients;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Party> getClients() {
        return clients;
    }

    public void setClients(List<Party> clients) {
        this.clients = clients;
    }
}
