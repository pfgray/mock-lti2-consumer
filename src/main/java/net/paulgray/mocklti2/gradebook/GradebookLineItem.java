package net.paulgray.mocklti2.gradebook;

import javax.persistence.*;

/**
 * Created by paul on 10/24/16.
 */
@Entity
@Table(name = "gradebook_lineitems")
public class GradebookLineItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "gradebook_id")
    private Integer gradebookId;

    @Column(name = "resource_link_id")
    private String resourceLinkId;

    public GradebookLineItem(Integer gradebookId, String resourceLinkId) {
        this.gradebookId = gradebookId;
        this.resourceLinkId = resourceLinkId;
    }

    public String getResourceLinkId() {
        return resourceLinkId;
    }

    public void setResourceLinkId(String resourceLinkId) {
        this.resourceLinkId = resourceLinkId;
    }

    public Integer getGradebookId() {
        return gradebookId;
    }

    public void setGradebookId(Integer gradebookId) {
        this.gradebookId = gradebookId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
