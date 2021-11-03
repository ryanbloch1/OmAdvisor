
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequirementGroup {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("requirements")
    @Expose
    private List<Requirement> requirements = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

}
