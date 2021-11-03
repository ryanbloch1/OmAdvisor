package com.cobi.puresurveyandroid.model;

public enum RolesEnum {

    POLICYOWNER("POLICY OWNER"),
    PREMIUMPAYER("PREMIUM PAYER"),
    INSUREDPERSON("INSURED PERSON"),
    BUSINESSENTITY("BUSINESS ENTITY");



    private String name;

    RolesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}