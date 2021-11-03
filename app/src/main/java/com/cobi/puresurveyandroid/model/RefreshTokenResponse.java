package com.cobi.puresurveyandroid.model;

import com.cobi.puresurveyandroid.webservice.ApiResponse.TokenResponse;

public class RefreshTokenResponse {

    private TokenResponse  tokenResponse;

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public RefreshTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }
}
