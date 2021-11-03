package com.cobi.puresurveyandroid.model;

/**
 * Created by admin on 2017/10/05.
 */

public class ReplyResponse extends BaseResponse {
    private ReplyResponseData data;

    public ReplyResponseData getData() {
        return data;
    }

    public void setData(ReplyResponseData data) {
        this.data = data;
    }
}
