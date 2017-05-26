package net.paulgray.mocklti2.tools;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.tools.Tool;

/**
 * @author pgray
 */
@Entity
@Table(name = "tool_registration_requests")
public class ToolRegistrationRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @Column(name = "reg_key")
    String key;

    @Column(name = "reg_secret")
    String secret;

    @Column(name = "guid")
    String guid;

    @Column(name = "registration_url")
    String registrationUrl;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "registrationRequest")
    private LtiToolProxy toolProxy;

    public ToolRegistrationRequest() {
    }

    public ToolRegistrationRequest(String key, String secret, String guid) {
        this.key = key;
        this.secret = secret;
        this.guid = guid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getRegistrationUrl() {
        return registrationUrl;
    }

    public void setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
    }

    public LtiToolProxy getToolProxy() {
        return toolProxy;
    }

    public void setToolProxy(LtiToolProxy toolProxy) {
        this.toolProxy = toolProxy;
    }
}
