package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PipelineResponse {

    @SerializedName("Message")
    String message;
    List<Pipeline> pipelines;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPipelines(List<Pipeline> pipelines) {
        this.pipelines = pipelines;
    }

    public String getMessage() {
        return message;
    }

    public PipelineResponse(List<Pipeline> pipelines) {
        this.pipelines = pipelines;
    }

    public PipelineResponse() {

    }

    public List<Pipeline> getPipelines() {
        return pipelines;
    }
}
