package net.paulgray.mocklti2.web;

import net.paulgray.mocklti2.gradebook.Gradebook;
import net.paulgray.mocklti2.gradebook.GradebookCell;
import net.paulgray.mocklti2.gradebook.GradebookLineItem;
import net.paulgray.mocklti2.gradebook.GradebookService;
import org.apache.commons.io.IOUtils;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by paul on 10/24/16.
 */
@Controller
public class GradebookController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    @RequestMapping(value = "outcomes/v2.0/gradebook/{contextId}/lineitems")
    public ResponseEntity<LineItem> createLineItems(@PathVariable String contextId, @RequestBody LineItem lineItem, HttpServletRequest req) {
        Gradebook gb = gradebookService.getOrCreateGradebook(contextId);

        String title = lineItem.getLabel().get("@value").textValue();
        String activityId = lineItem.getActivity().get("@id").textValue();

        GradebookLineItem newLineItem = gradebookService.getOrCreateGradebookLineItemByResourceId(gb.getId(), UUID.randomUUID().toString());

        newLineItem.setTitle(title);
        newLineItem.setActivityId(activityId);

        gradebookService.updateLineItem(newLineItem);

        String origin = HttpUtils.getOrigin(req).orElse("");
        String resultsUrl = origin + "/outcomes/v2.0/gradebook/" + contextId + "/lineitems/" + newLineItem.getResourceLinkId();

        lineItem.setResultsUrl(Optional.of(resultsUrl));

        log.info("constructed results url: " + resultsUrl);

        return new ResponseEntity<>(lineItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/outcomes/v1.1/gradebook", method = RequestMethod.POST)
    public ResponseEntity<String> handleOutcomes1(HttpServletRequest request) throws Exception {

        //log.info("Got Lti Outcomes 1 message: \n" + IOUtils.toString(request.getInputStream()));

        Optional<Outcomes1Request> outcomes1Request = readOutcomes1Request(request.getInputStream());

        outcomes1Request.ifPresent(out -> {
            System.out.println("Got Outcomes request: \n" +
                    "  Student: " + out.studentId + "\n" +
                    "  Course: " + out.contextId + "\n" +
                    "  Resource: " + out.resourceId
            );

            Gradebook gb = gradebookService.getOrCreateGradebook(out.contextId);

            GradebookLineItem lineItem =
                gradebookService.getOrCreateGradebookLineItemByResourceId(gb.getId(), out.resourceId);

            GradebookCell cell =
                gradebookService.getOrCreateGradebookCell(lineItem.getId(), out.studentId);

            //update the student's grade
            cell.setGrade(out.grade);
            gradebookService.updateGradebookCell(cell);

        });

        return new ResponseEntity("", HttpStatus.ACCEPTED);
    }

    public Optional<Outcomes1Request> readOutcomes1Request(InputStream is){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            NodeList sourceList = doc.getElementsByTagName("sourcedId");
            log.info("got: ", sourceList.getLength());
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
