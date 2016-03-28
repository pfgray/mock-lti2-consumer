package net.paulgray.mocklti2.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by paul on 3/27/16.
 */
public class UnsignedLtiLaunchRequest {

    @JsonProperty
    private Map<String, String> launchParameters;
    @JsonProperty
    private String url;
    @JsonProperty
    private String method;
    @JsonProperty
    private String key;
    @JsonProperty
    private String secret;

    public UnsignedLtiLaunchRequest() {
    }

    public Map<String, String> getLaunchParameters() {
        return launchParameters;
    }

    public void setLaunchParameters(Map<String, String> launchParameters) {
        this.launchParameters = launchParameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
