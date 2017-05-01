package com.device.vk.model.dialog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class MessageResponse {

    @JsonProperty("unread")
    private Long unread;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @JsonProperty("message")
    private Message message;



    public Long getUnread() {
        return unread;
    }

    public void setUnread(Long unread) {
        this.unread = unread;
    }



}
