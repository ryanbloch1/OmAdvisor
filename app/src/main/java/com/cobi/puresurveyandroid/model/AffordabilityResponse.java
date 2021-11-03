package com.cobi.puresurveyandroid.model;

public class AffordabilityResponse {
    String ib;
    String invb;
    String gl;
    String mx;


    public AffordabilityResponse(String ib, String invb, String gl, String mx) {
        this.ib = ib;
        this.invb = invb;
        this.gl = gl;
        this.mx = mx;
    }

    public String getIb() {
        return ib;
    }

    public String getInvb() {
        return invb;
    }

    public String getGl() {
        return gl;
    }

    public String getMx() {
        return mx;
    }
}
