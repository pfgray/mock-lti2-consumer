package net.paulgray.mocklti2.gradebook;

import javax.persistence.*;

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

    public Gradebook(Integer id) {
        this.id = id;
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
