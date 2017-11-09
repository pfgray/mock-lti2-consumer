package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Membership {

    @JsonProperty("status")
    String status;
    @JsonProperty("member")
    Person member;

    // todo: wat
    @JsonProperty("message")
    Object message;
    @JsonProperty("role")
    String role;

    public Membership(String status, String role, Person member) {
        this.status = status;
        this.role = role;
        this.member = member;
    }
}
