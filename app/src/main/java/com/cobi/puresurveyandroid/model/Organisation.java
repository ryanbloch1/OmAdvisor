
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organisation {

    @SerializedName("UnstructuredName")
    @Expose
    private UnstructuredName unstructuredName;
    @SerializedName("Organisation")
    @Expose
    private List<Organisation> organisation = null;
    @SerializedName("externalReference")
    @Expose
    private String externalReference;
    @SerializedName("typeName")
    @Expose
    private String typeName;

    public UnstructuredName getUnstructuredName() {
        return unstructuredName;
    }

    public void setUnstructuredName(UnstructuredName unstructuredName) {
        this.unstructuredName = unstructuredName;
    }

    public List<Organisation> getOrganisation() {
        return organisation;
    }

    public void setOrganisation(List<Organisation> organisation) {
        this.organisation = organisation;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
