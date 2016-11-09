package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nicole on 11/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Agent {

    @JsonProperty("http://purl.imsglobal.org/vocab/lis/v2/pm#userId")
    private Value userid;

    public Agent() {
    }

    public Value getUserid() {
        return userid;
    }

    public void setUserid(Value userid) {
        this.userid = userid;
    }
}
