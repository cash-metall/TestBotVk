package com.device.vk.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class User {


    @JsonProperty("first_name")
    public String firstName;
    @JsonProperty("last_name")
    public String lastName;
    @JsonProperty("sex")
    public Long sex;
    @JsonProperty("invited_by")
    public Long invitedBy;

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Long getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Long invitedBy) {
        this.invitedBy = invitedBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
