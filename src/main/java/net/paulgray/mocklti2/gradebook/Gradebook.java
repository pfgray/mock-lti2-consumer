package net.paulgray.mocklti2.gradebook;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "context", unique = true)
    private String context;
    @Column(name = "created")
    private Date created;
    @Column(name = "last_updated")
    private Date lastUpdated;

    public Gradebook() {
    }

    public Gradebook(Integer id) {
        this.id = id;
    }

    public Gradebook(String context) {
        this.context = context;
        this.created = new Date();
        this.lastUpdated = new Date();
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
