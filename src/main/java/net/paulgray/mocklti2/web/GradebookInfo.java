package net.paulgray.mocklti2.web;

import net.paulgray.mocklti2.gradebook.Gradebook;

import java.util.List;

/**
 * Created by nicole on 11/3/16.
 */
public class GradebookInfo {
    Gradebook gradebook;
    List<ColumnInfo> columns;

    public GradebookInfo(Gradebook gradebook, List<ColumnInfo> columns) {
        this.gradebook = gradebook;
        this.columns = columns;
    }

    public Gradebook getGradebook() {
        return gradebook;
    }

    public void setGradebook(Gradebook gradebook) {
        this.gradebook = gradebook;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }
}
