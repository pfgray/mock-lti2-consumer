package net.paulgray.mocklti2.gradebook;

import net.paulgray.mocklti2.tools.LtiToolProxy;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import java.util.List;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.imsglobal.lti2.objects.provider.ToolProxy;

import java.util.List;

/**
 *
 * @author paul
 */
@Entity
@Table(name = "gradebooks")
public class Gradebook {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "context")
    private String context;

    public Gradebook() {
    }

    public Gradebook(String context) {
        this.context = context;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
