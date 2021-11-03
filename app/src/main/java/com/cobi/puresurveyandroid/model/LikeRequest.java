package com.cobi.puresurveyandroid.model;

public class LikeRequest {

    public String DigitalId;
    public String MessageId;

    public LikeRequest(String digitalId, String messageId) {
        DigitalId = digitalId;
        MessageId = messageId;
    }

    public String getDigitalId() {
        return DigitalId;
    }

    public void setDigitalId(String digitalId) {
        DigitalId = digitalId;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }
}
