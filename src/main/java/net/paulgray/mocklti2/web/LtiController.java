package net.paulgray.mocklti2.web;

import net.paulgray.mocklti2.gradebook.Gradebook;
import net.paulgray.mocklti2.gradebook.GradebookCell;
import net.paulgray.mocklti2.gradebook.GradebookLineItem;
import net.paulgray.mocklti2.gradebook.GradebookService;
import org.imsglobal.lti.launch.LtiSigner;
import org.imsglobal.lti.launch.LtiSigningException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.Optional;

/**
 * Created by paul on 3/27/16.
 */
@Controller
public class LtiController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

        final String lisResultSourcedId = gb.getContext() + ":~:" + lineItem.getResourceLinkId() + ":~:" + request.getLaunchParameters().get("user_id");

        log.info("Created gradebook info: " + lisResultSourcedId);

        //  get gradebook
        request.getLaunchParameters().put("lis_result_sourcedid", lisResultSourcedId);

        Map<String, String> params = ltiSigner.signParameters(
            request.getLaunchParameters(),
            request.getKey(),
            request.getSecret(),
            request.getUrl(),
            request.getMethod()
        );

        return new ResponseEntity(new LtiLaunchContext(params, gb, lineItem, cell), HttpStatus.ACCEPTED);
    }

    private GradebookCell getCellForLineItem(GradebookLineItem lineItem, UnsignedLtiLaunchRequest request) {
        GradebookCell cell = new GradebookCell(lineItem.getId(), request.getLaunchParameters().get("lis_result_sourcedid"), null);
        return gradebookService.addCell(cell);
    }

    private GradebookLineItem getLineItemForGradebook(Gradebook gb, UnsignedLtiLaunchRequest request) {
        GradebookLineItem newLineItem = new GradebookLineItem(gb.getId(), request.getLaunchParameters().get("resource_link_id"));
        return gradebookService.addLineItem(newLineItem);
    }

    public Gradebook getGradebookForReq(UnsignedLtiLaunchRequest request){
        String contextId = request.getLaunchParameters().get("context_id");
        // create this gradebook, if it doesn't exist, and
        // map the result_sourcedid to the user for the request
        Optional<Gradebook> gradebook = gradebookService.getGradebook(contextId);

        return gradebook.orElseGet(() -> gradebookService.addGradebook(contextId));
    }

}
