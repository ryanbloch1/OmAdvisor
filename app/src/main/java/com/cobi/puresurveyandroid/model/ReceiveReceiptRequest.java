package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2017/10/05.
 */

public class ReceiveReceiptRequest {

    @SerializedName("message_ids")
    private List<String> messageIds;

    public List<String> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<String> messageIds) {
        this.messageIds = messageIds;
    }
}
