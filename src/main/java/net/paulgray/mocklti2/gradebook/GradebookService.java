package net.paulgray.mocklti2.gradebook;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by paul on 10/23/16.
 */
public interface GradebookService {

    Optional<Gradebook> getGradebook(String contextId);

    Gradebook addGradebook(String contextId);

    Optional<List<GradebookLineItem>> getGradebookLineItems(Integer contextId);

    GradebookLineItem addLineItem(GradebookLineItem lineItem);

    Map<Integer, List<GradebookCell>> getGradebookCells(List<Integer> columnIds);

    GradebookCell addCell(GradebookCell cell);

    Gradebook updateGradebookCell(Integer contextId, String resultSourcedId, String grade);

}
