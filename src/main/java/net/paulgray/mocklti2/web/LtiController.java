package net.paulgray.mocklti2.web;

import net.paulgray.mocklti2.gradebook.Gradebook;
import net.paulgray.mocklti2.gradebook.GradebookCell;
import net.paulgray.mocklti2.gradebook.GradebookLineItem;
import net.paulgray.mocklti2.gradebook.GradebookService;
import net.paulgray.lti.launch.LtiSigner;
import net.paulgray.lti.launch.LtiSigningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by paul on 3/27/16.
 */
@Controller
public class LtiController {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    GradebookService gradebookService;

    @Autowired
    LtiSigner ltiSigner;

    @RequestMapping(value = "/api/signedLaunch", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> getSignedLaunch(
            @RequestBody UnsignedLtiLaunchRequest request) throws LtiSigningException {

        // get or create gb
        Gradebook gb = getGradebookForReq(request);

        // get or create lineitem for resource
        GradebookLineItem lineItem = getLineItemForGradebook(gb, request);

        // get or create cell
        GradebookCell cell = getCellForLineItem(lineItem, request);

        // uncomment this to generate test gradebook data
        // generateRandomColumns(gb);

        final String lisResultSourcedId = gb.getContext() + ":~:" + lineItem.getResourceLinkId() + ":~:" + request.getLaunchParameters().get("user_id");

        log.info("Created gradebook info: " + lisResultSourcedId);

        //  get gradebook
        request.getLaunchParameters().put("lis_result_sourcedid", lisResultSourcedId);

        Map<String, String> unsigned = request.getLaunchParameters();

        Map<String, String> params = ltiSigner.signParameters(
            unsigned.entrySet(),
            request.getKey(),
            request.getSecret(),
            request.getUrl(),
            request.getMethod()
        );

        return new ResponseEntity(new LtiLaunchContext(params, gb, lineItem, cell), HttpStatus.ACCEPTED);
    }

    private GradebookCell getCellForLineItem(GradebookLineItem lineItem, UnsignedLtiLaunchRequest request) {
        String studentId = request.getLaunchParameters().get("user_id");
        return gradebookService.getOrCreateGradebookCell(lineItem.getId(), studentId, null);
    }

    private GradebookLineItem getLineItemForGradebook(Gradebook gb, UnsignedLtiLaunchRequest request) {
        String resourceId = request.getLaunchParameters().get("resource_link_id");
        String resourceTitle = request.getLaunchParameters().get("resource_link_title");
        GradebookLineItem lineItem = gradebookService.getOrCreateGradebookLineItemByResourceLinkId(gb.getId(), resourceId, null);
        lineItem.setTitle(resourceTitle);
        return gradebookService.updateLineItem(lineItem);
    }

    public Gradebook getGradebookForReq(UnsignedLtiLaunchRequest request){
        String contextId = request.getLaunchParameters().get("context_id");
        return gradebookService.getOrCreateGradebook(contextId);
    }

    private List<GradebookLineItem> generateRandomColumns(Gradebook gb) {
        List<GradebookLineItem> assignments = new LinkedList<>();
        for (int i = 1; i<31; i++) {
            GradebookLineItem assignment = gradebookService.getOrCreateGradebookLineItemByResourceLinkId(gb.getId(), "assignment_" + i, null);
            assignment.setTitle("Assignment #" + i);
            gradebookService.updateLineItem(assignment);
            assignments.add(assignment);
            generateRandomCellsForLineItems(assignment);
        }
        return assignments;
    }

    private void generateRandomCellsForLineItems(GradebookLineItem lineItem) {
        for (int i = 1; i<11; i++) {
            Random r = new Random();
            int Low = 0;
            int High = 200;
            int result = r.nextInt(High-Low) + Low;

            GradebookCell cell = gradebookService.getOrCreateGradebookCell(lineItem.getId(), "student" + i, null);
            if(result < 101) {
                cell.setGrade(Integer.toString(result));
            }
            gradebookService.updateGradebookCell(cell);
        }
    }

}
