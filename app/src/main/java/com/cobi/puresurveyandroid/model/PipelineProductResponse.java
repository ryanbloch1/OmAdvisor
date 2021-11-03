package com.cobi.puresurveyandroid.model;

import java.util.List;

public class PipelineProductResponse {

    public void setPipelineProducts(List<PipelineProduct> pipelineProducts) {
        this.pipelineProducts = pipelineProducts;
    }

    List<PipelineProduct> pipelineProducts;

    public PipelineProductResponse(List<PipelineProduct> pipelineProducts) {
        this.pipelineProducts = pipelineProducts;
    }

    public PipelineProductResponse() {

    }



    public List<PipelineProduct> getPipelineProducts() {
        return pipelineProducts;
    }
}
