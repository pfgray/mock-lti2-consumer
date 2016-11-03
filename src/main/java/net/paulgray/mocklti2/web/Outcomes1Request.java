package net.paulgray.mocklti2.web;

/**
 * Created by paul on 10/24/16.
 */
public class Outcomes1Request {

    public String grade;
    public String studentId;
    public String resourceId;
    public String contextId;

    public Outcomes1Request(String grade, String studentId, String resourceId, String contextId) {
        this.grade = grade;
        this.studentId = studentId;
        this.resourceId = resourceId;
        this.contextId = contextId;
    }
}
