package net.paulgray.mocklti2.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nicole on 11/8/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@value")
    private String value;

    public Value() {
    }

    public Value(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
