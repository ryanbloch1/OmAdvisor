package com.cobi.puresurveyandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2017/10/05.
 */

public class ReadReceiptRequest {
    @SerializedName("message_ids")
    List<String> messageIds;

    public List<String> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<String> messageIds) {
        this.messageIds = messageIds;
    }
}
