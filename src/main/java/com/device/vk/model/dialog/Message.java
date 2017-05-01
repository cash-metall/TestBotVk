package com.device.vk.model.dialog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Message {

    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("body")
    private String body;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("date")
    private Long date;

    @JsonProperty("read_state")
    private int readState;


    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }


}
