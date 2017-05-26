package net.paulgray.mocklti2.tools;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by pgray on 8/31/14.
 */
@Entity
@Table(name = "tool_proxies")
public class LtiToolProxy {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "active")
    private Boolean active;

    @JoinColumn(name = "tool")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private LtiTool tool;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "toolProxy")
    Set<LtiToolResourceHandler> resourceHandlers;

    @JoinColumn(name = "reg_request")
    @OneToOne(fetch = FetchType.EAGER)
    private ToolRegistrationRequest registrationRequest;

    @Column(name = "secure_url")
    private String secureUrl;

    @Column(name = "default_url")
    private String defaultUrl;

    @Column(name = "lti_key")
    private String key;

    @Column(name = "lti_secret")
    private String secret;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LtiTool getTool() {
        return tool;
    }

    public void setTool(LtiTool tool) {
        this.tool = tool;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
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

    public Set<LtiToolResourceHandler> getResourceHandlers() {
        return resourceHandlers;
    }

    public void setResourceHandlers(Set<LtiToolResourceHandler> resourceHandlers) {
        this.resourceHandlers = resourceHandlers;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ToolRegistrationRequest getRegistrationRequest() {
        return registrationRequest;
    }

    public void setRegistrationRequest(ToolRegistrationRequest registrationRequest) {
        this.registrationRequest = registrationRequest;
    }
}
