package net.paulgray.mocklti2.web;

import net.paulgray.mocklti2.gradebook.GradebookCell;
import net.paulgray.mocklti2.gradebook.GradebookLineItem;

import java.util.List;

/**
 * Created by nicole on 11/3/16.
 */
public class ColumnInfo {
    GradebookLineItem column;
    List<GradebookCell> cells;

    public ColumnInfo(GradebookLineItem column, List<GradebookCell> cells) {
        this.column = column;
        this.cells = cells;
    }

    public GradebookLineItem getColumn() {
        return column;
    }

    public void setColumn(GradebookLineItem column) {
        this.column = column;
    }

    public List<GradebookCell> getCells() {
        return cells;
    }

    public void setCells(List<GradebookCell> cells) {
        this.cells = cells;
    }
}
