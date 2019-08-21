package net.paulgray.mocklti2.gradebook;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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

    @Column(name = "title")
    private String title;

    @Column(name = "source", columnDefinition = "TEXT")
    private String source;

    @Column(name = "score_maximum")
    private BigDecimal scoreMaximum;

    @Column(name = "created")
    private Date created;

    @Column(name = "last_updated")
    private Date lastUpdated;

    public GradebookLineItem() {
    }

    public GradebookLineItem(Integer gradebookId, String resourceLinkId, String source) {
        this.gradebookId = gradebookId;
        this.resourceLinkId = resourceLinkId;
        this.title = null;
        this.source = source;
        this.created = new Date();
        this.lastUpdated = new Date();
    }

    public GradebookLineItem(Integer gradebookId, String resourceLinkId, String title, String activityId, String source) {
        this.gradebookId = gradebookId;
        this.resourceLinkId = resourceLinkId;
        this.title = title;
        this.source = source;
        this.created = new Date();
        this.lastUpdated = new Date();
    }

    public GradebookLineItem(Integer id, Integer gradebookId, String resourceLinkId, String title, String source, String activityId, String resourceId, String tag, BigDecimal scoreMaximum) {
        this.id = id;
        this.gradebookId = gradebookId;
        this.resourceLinkId = resourceLinkId;
        this.title = title;
        this.source = source;
        this.scoreMaximum = scoreMaximum;
        this.created = new Date();
        this.lastUpdated = new Date();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getScoreMaximum() {
        return scoreMaximum;
    }

    public void setScoreMaximum(BigDecimal scoreMaximum) {
        this.scoreMaximum = scoreMaximum;
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
