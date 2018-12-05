package net.paulgray.mocklti2.gradebook;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by paul on 10/23/16.
 */
public interface GradebookService {

    Optional<Gradebook> getGradebook(String contextId);

    Gradebook getOrCreateGradebook(String contextId);

    Gradebook addGradebook(String contextId);

    Optional<List<GradebookLineItem>> getGradebookLineItems(Integer contextId);

    Optional<GradebookLineItem> getGradebookLineItemByResourceId(Integer gradebookId, String resourceId);

    Optional<GradebookLineItem> getGradebookLineItemById(Integer gradebookId, Integer lineItemId);

    GradebookLineItem getOrCreateGradebookLineItemByResourceLinkId(Integer gradebookId, String resourceLinkId, String source);

    GradebookLineItem updateLineItem(GradebookLineItem lineItem);

    void deleteLineItem(GradebookLineItem lineItem);

    GradebookLineItem addLineItem(GradebookLineItem lineItem);

    Map<Integer, List<GradebookCell>> getGradebookCells(List<Integer> columnIds);

    Optional<GradebookCell> getGradebookCell(Integer lineItemId, String resultSourcedId);

    GradebookCell getOrCreateGradebookCell(Integer lineItemId, String resultSourcedId, String source);

    GradebookCell addCell(GradebookCell cell);

    GradebookCell updateGradebookCell(GradebookCell gradebookCell);

}
