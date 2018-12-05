package net.paulgray.mocklti2.gradebook;

import javax.persistence.*;

/**
 * Created by paul on 10/23/16.
 */
@Entity
@Table(name = "gradebook_cells")
public class GradebookCell {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "gradebook_lineitem_id")
    private Integer gradebookLineItemId;
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "grade")
    private String grade;
    @Column(name = "source")
    private String source;


    public GradebookCell() {
    }

    public GradebookCell(Integer id, Integer gradebookLineItemId, String studentId, String grade, String source) {
        this.id = id;
        this.gradebookLineItemId = gradebookLineItemId;
        this.studentId = studentId;
        this.grade = grade;
        this.source = source;
    }

    public GradebookCell(Integer gradebookLineItemId, String studentId, String grade, String source) {
        this.gradebookLineItemId = gradebookLineItemId;
        this.studentId = studentId;
        this.grade = grade;
        this.source = source;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGradebookLineItemId() {
        return gradebookLineItemId;
    }

    public void setGradebookLineItemId(Integer gradebookLineItemId) {
        this.gradebookLineItemId = gradebookLineItemId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
