package net.paulgray.mocklti2.gradebook;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "source")
    private String source;

    @Column(name = "activity_id")
    private String activityId;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "tag")
    private String tag;

    @Column(name = "score_maximum")
    private BigDecimal scoreMaximum;

    public GradebookLineItem() {
    }

    public GradebookLineItem(Integer gradebookId, String resourceLinkId, String source) {
        this.gradebookId = gradebookId;
        this.resourceLinkId = resourceLinkId;
        this.title = null;
        this.activityId = null;
        this.source = source;
    }

    public GradebookLineItem(Integer gradebookId, String resourceLinkId, String title, String activityId, String source) {
        this.gradebookId = gradebookId;
        this.resourceLinkId = resourceLinkId;
        this.title = title;
        this.activityId = activityId;
        this.source = source;
    }

    public GradebookLineItem(Integer id, Integer gradebookId, String resourceLinkId, String title, String source, String activityId, String resourceId, String tag, BigDecimal scoreMaximum) {
        this.id = id;
        this.gradebookId = gradebookId;
        this.resourceLinkId = resourceLinkId;
        this.title = title;
        this.source = source;
        this.activityId = activityId;
        this.resourceId = resourceId;
        this.tag = tag;
        this.scoreMaximum = scoreMaximum;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public BigDecimal getScoreMaximum() {
        return scoreMaximum;
    }

    public void setScoreMaximum(BigDecimal scoreMaximum) {
        this.scoreMaximum = scoreMaximum;
    }
}
