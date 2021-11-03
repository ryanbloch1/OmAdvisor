
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartyPerson {

    @SerializedName("Person")
    @Expose
    private List<PersonObjectParty> person = null;

    public List<PersonObjectParty> getPerson() {
        return person;
    }

    public void setPerson(List<PersonObjectParty> person) {
        this.person = person;
    }

}
