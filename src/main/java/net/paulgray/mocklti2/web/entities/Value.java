package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nicole on 11/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    @JsonProperty("@value")
    private String value;

    public Value() {
    }

    public Value(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
