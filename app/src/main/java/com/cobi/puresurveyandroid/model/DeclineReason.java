package com.cobi.puresurveyandroid.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeclineReason {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Description")
    @Expose
    private String description;


    public DeclineReason(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
