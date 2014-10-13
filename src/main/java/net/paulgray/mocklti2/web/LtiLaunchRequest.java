package net.paulgray.mocklti2.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by pgray on 10/13/14.
 */
public class LtiLaunchRequest {
    @JsonProperty
    private Map<String, String> launchParameters;
    @JsonProperty
    private String url;
    @JsonProperty
    private String method;

    public LtiLaunchRequest(Map<String, String> launchParameters, String url, String method) {
        this.launchParameters = launchParameters;
        this.url = url;
        this.method = method;
    }

    public LtiLaunchRequest() {
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
}
