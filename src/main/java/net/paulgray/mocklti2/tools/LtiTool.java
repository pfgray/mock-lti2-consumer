/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.paulgray.mocklti2.tools;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author paul
 */
@Entity
@Table(name = "tools")
public class LtiTool {

    public enum State {
        added, registered, failed
    };

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "label")
    private String label;
    @Column(name = "launch_url")
    private String launchUrl;
    @Column(name = "register_url")
    private String registerUrl;
    @Column(name = "tool_state")
    @Enumerated(EnumType.STRING)
    private State state;
    
    @JsonProperty
    public Integer getId() {
        return id;
    }
    
    @JsonIgnore
    public void setId(Integer id) {
        this.id = id;
    }
    
    @JsonProperty
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty
    public String getLaunchUrl() {
        return launchUrl;
    }

    @JsonIgnore
    public void setLaunchUrl(String launchUrl) {
        this.launchUrl = launchUrl;
    }

    @JsonProperty
    public State getState() {
        return state;
    }

    @JsonIgnore
    public void setState(State state) {
        this.state = state;
    }

    @JsonProperty
    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

}
