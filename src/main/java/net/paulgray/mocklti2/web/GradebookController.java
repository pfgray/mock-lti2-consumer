package net.paulgray.mocklti2.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.paulgray.mocklti2.MockLti2App;
import net.paulgray.mocklti2.gradebook.Gradebook;
import net.paulgray.mocklti2.gradebook.GradebookCell;
import net.paulgray.mocklti2.gradebook.GradebookLineItem;
import net.paulgray.mocklti2.gradebook.GradebookService;
import net.paulgray.mocklti2.web.entities.LineItem;
import net.paulgray.mocklti2.web.entities.Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by paul on 10/24/16.
 */
@Controller
public class GradebookController {

    //TODO: make sure these requests are signed, lol

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    GradebookService gradebookService;

    @RequestMapping(value = "/outcomes/gradebooks/{gradebookId}", method = RequestMethod.GET)
    public ResponseEntity<GradebookInfo> getGradebook(@PathVariable String gradebookId) throws Exception {
        Optional<GradebookInfo> gbOp = gradebookService.getGradebook(gradebookId).flatMap(gb ->
            gradebookService.getGradebookLineItems(gb.getId()).map(columns -> {
                List<Integer> columnIds = columns.stream().map(GradebookLineItem::getId).collect(Collectors.toList());
                Map<Integer, List<GradebookCell>> cells = gradebookService.getGradebookCells(columnIds);

                List<ColumnInfo> columnInfos = columns.stream().map(col -> new ColumnInfo(col, cells.get(col.getId()))).collect(Collectors.toList());

                return new GradebookInfo(gb, columnInfos);
            })
        );
        return gbOp
            .map(gb -> new ResponseEntity<GradebookInfo>(gb, HttpStatus.ACCEPTED))
            .orElseGet(() -> new ResponseEntity<GradebookInfo>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/outcomes/v2.0/gradebook/{contextId}/lineitems", method = RequestMethod.POST)
    public ResponseEntity<LineItem> createLineItems(@PathVariable String contextId, HttpServletRequest req) {

        ObjectMapper mapper = MockLti2App.standardMapper();
        String body = null;
        LineItem lineItem = null;
        try {
            body = IOUtils.toString(req.getInputStream());
            lineItem = mapper.readValue(body, LineItem.class);
        } catch (Exception e) {
            log.warn("Not able to read line item source:", e);
            e.printStackTrace();
        }

        Gradebook gb = gradebookService.getOrCreateGradebook(contextId);

        String title = lineItem.getLabel().get("@value").textValue();
        String activityId = lineItem.getActivity().get("@id").textValue();

        GradebookLineItem newLineItem = gradebookService.getOrCreateGradebookLineItemByResourceLinkId(gb.getId(), UUID.randomUUID().toString(), body);

        newLineItem.setTitle(title);
        newLineItem.setActivityId(activityId);

        gradebookService.updateLineItem(newLineItem);

        String origin = HttpUtils.getOrigin(req).orElse("");
        String resultsUrl = origin + "/outcomes/v2.0/gradebook/" + contextId + "/lineitems/" + newLineItem.getResourceLinkId();

        lineItem.setResultsUrl(Optional.of(resultsUrl));

        log.info("constructed results url: " + resultsUrl);

        return new ResponseEntity<>(lineItem, HttpStatus.OK);
    }

    @RequestMapping(value = "outcomes/v2.0/gradebook/{contextId}/lineitems/{lineItemId}", method = {RequestMethod.POST})
    public ResponseEntity<Result> createResults(
            @PathVariable String contextId,
            @PathVariable String lineItemId,
            HttpServletRequest req
    ) throws Exception {

        Gradebook gb = gradebookService.getOrCreateGradebook(contextId);

        Optional<GradebookLineItem> lineItem = gradebookService.getGradebookLineItemByResourceId(gb.getId(), lineItemId);

        if(lineItem.isPresent()){
            ObjectMapper mapper = MockLti2App.standardMapper();
            String body = null;
            Result result = null;
            try {
                body = IOUtils.toString(req.getInputStream());
                result = mapper.readValue(body, Result.class);
            } catch (Exception e) {
                log.warn("Not able to read line item source:", e);
                e.printStackTrace();
            }

            String userId = result.getStudent().getUserid().getValue();
            log.info("Got result for student: <" + userId + "> and score: " + result.getTotalScore().getValue());

            log.info("Now attempting to insert grade into gradebook: " + contextId + " into column: " + lineItemId);
            GradebookCell cell = gradebookService.getOrCreateGradebookCell(lineItem.get().getId(), userId, body);
            cell.setGrade(result.getTotalScore().getValue());
            GradebookCell updatedCell = gradebookService.updateGradebookCell(cell);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            log.info("Could not save gradebook cell, since the line item referenced was not found ");

            return new ResponseEntity<Result>((Result) null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/outcomes/v1.1/gradebook", method = RequestMethod.POST)
    public ResponseEntity<String> handleOutcomes1(HttpServletRequest request) throws Exception {

        log.info("Got Lti Outcomes 1 message: \n" + IOUtils.toString(request.getInputStream()));

        String body = IOUtils.toString(request.getInputStream());

        Optional<Outcomes1Request> outcomes1Request = readOutcomes1Request(body);

        outcomes1Request.ifPresent(out -> {
            log.info("Got Outcomes request: \n" +
                    "  Student: " + out.studentId + "\n" +
                    "  Course: " + out.contextId + "\n" +
                    "  Resource: " + out.resourceId
            );

            Gradebook gb = gradebookService.getOrCreateGradebook(out.contextId);

            GradebookLineItem lineItem =
                gradebookService.getOrCreateGradebookLineItemByResourceLinkId(gb.getId(), out.resourceId, null);

            GradebookCell cell =
                gradebookService.getOrCreateGradebookCell(lineItem.getId(), out.studentId, body);

            //update the student's grade
            cell.setGrade(out.grade);
            gradebookService.updateGradebookCell(cell);

        });

        return new ResponseEntity("", HttpStatus.OK);
    }

    public Optional<Outcomes1Request> readOutcomes1Request(String is){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            NodeList sourceList = doc.getElementsByTagName("sourcedId");
            log.info("got: " + sourceList.getLength());
            Node sourceNode = sourceList.item(0);

            log.info("sourceNode: " + sourceNode.getTextContent());

            Optional<String> sourcedid = Optional.ofNullable(sourceNode.getTextContent());

            return sourcedid
                .map(s -> Arrays.asList(s.split(":~:")))
                .flatMap(values -> {
                    log.info("Found values: " + values.get(0));
                    Optional<String> grade =
                        Optional.ofNullable(doc.getElementsByTagName("resultScore").item(0))
                        .map(res -> ((Element) res).getElementsByTagName("textString").item(0).getTextContent());

                    return grade.map(g -> new Outcomes1Request(g, values.get(2), values.get(1), values.get(0)));
                });
        } catch (ParserConfigurationException|SAXException|IOException e) {
            return Optional.empty();
        }
    }

}
