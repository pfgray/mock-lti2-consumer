package net.paulgray.mocklti2.web;

import net.paulgray.mocklti2.gradebook.Gradebook;
import net.paulgray.mocklti2.gradebook.GradebookCell;
import net.paulgray.mocklti2.gradebook.GradebookLineItem;

import java.util.Map;

/**
 * Created by paul on 10/24/16.
 */
public class LtiLaunchContext {
    public Map<String, String> launch;
    public Gradebook gradebook;
    public GradebookLineItem lineItem;
    public GradebookCell gradebookCell;

    public LtiLaunchContext(Map<String, String> launch, Gradebook gradebook, GradebookLineItem lineItem, GradebookCell gradebookCell) {
        this.launch = launch;
        this.gradebook = gradebook;
        this.lineItem = lineItem;
        this.gradebookCell = gradebookCell;
    }
}
