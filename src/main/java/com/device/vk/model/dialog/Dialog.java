package com.device.vk.model.dialog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dialog {

    @JsonProperty("count")
    private Long count;

    @JsonProperty("items")
    private List<MessageResponse> messages = new ArrayList<MessageResponse>();



    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }


}