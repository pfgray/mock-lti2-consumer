package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MembershipSubject {

    @JsonProperty("@type")
    String _type;
    @JsonProperty("contextId")
    String contextId;
    @JsonProperty("membership")
    List<Membership> membership;

    public MembershipSubject(String contextId, List<Membership> membership) {
        this._type = "Context";
        this.contextId = contextId;
        this.membership = membership;
    }
}
