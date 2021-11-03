
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntermediaryDetail {

    @SerializedName("Person")
    @Expose
    private List<Person> person = null;
    @SerializedName("IntermediaryAgreement")
    @Expose
    private List<IntermediaryAgreement> intermediaryAgreement = null;

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    public List<IntermediaryAgreement> getIntermediaryAgreement() {
        return intermediaryAgreement;
    }

    public void setIntermediaryAgreement(List<IntermediaryAgreement> intermediaryAgreement) {
        this.intermediaryAgreement = intermediaryAgreement;
    }

}
