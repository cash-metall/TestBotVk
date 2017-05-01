package com.device.vk.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class UserResponse {

    @JsonProperty("response")
    private List<User> response = new ArrayList<User>();

    public List<User> getResponse() {
        return response;
    }

    public void setResponse(List<User> response) {
        this.response = response;
    }
}
