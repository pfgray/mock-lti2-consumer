package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MembershipContainer {

    @JsonProperty("@type")
    String _type;
    @JsonProperty("membership")
    MembershipSubject membershipSubject;

    public MembershipContainer(MembershipSubject membershipSubject) {
        this._type = "LISMembershipContainer";
        this.membershipSubject = membershipSubject;
    }
}
