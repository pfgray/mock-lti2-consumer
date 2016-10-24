package net.paulgray.mocklti2.gradebook;

import java.util.Optional;

/**
 * Created by paul on 10/23/16.
 */
public interface GradebookService {

    Optional<Gradebook> getGradebook(String contextId);

    Gradebook addGradebook(String contextId);

    GradebookLineItem addLineItem(GradebookLineItem lineItem);

    GradebookCell addCell(GradebookCell cell);

    Gradebook updateGradebookCell(Integer contextId, String resultSourcedId, String grade);

}
