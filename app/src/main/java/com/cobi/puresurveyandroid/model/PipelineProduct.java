
package com.cobi.puresurveyandroid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PipelineProduct {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("contract_number")
    @Expose
    private Integer contractNumber;
    @SerializedName("proposal_number")
    @Expose
    private String proposalNumber;
    @SerializedName("intermediary_code")
    @Expose
    private Integer intermediaryCode;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status_date")
    @Expose
    private String statusDate;
    @SerializedName("requirement_group")
    @Expose
    private List<RequirementGroup> requirementGroup = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Integer contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public Integer getIntermediaryCode() {
        return intermediaryCode;
    }

    public void setIntermediaryCode(Integer intermediaryCode) {
        this.intermediaryCode = intermediaryCode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public List<RequirementGroup> getRequirementGroup() {
        return requirementGroup;
    }

    public void setRequirementGroup(List<RequirementGroup> requirementGroup) {
        this.requirementGroup = requirementGroup;
    }

}
