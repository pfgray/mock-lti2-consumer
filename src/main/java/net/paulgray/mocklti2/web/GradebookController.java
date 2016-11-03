package net.paulgray.mocklti2.web;

import net.paulgray.mocklti2.gradebook.Gradebook;
import net.paulgray.mocklti2.gradebook.GradebookCell;
import net.paulgray.mocklti2.gradebook.GradebookLineItem;
import net.paulgray.mocklti2.gradebook.GradebookService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by paul on 10/24/16.
 */
@Controller
public class GradebookController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GradebookService gradebookService;

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

            Gradebook gb = gradebookService.addGradebook(out.contextId);
            GradebookLineItem lineItem = gradebookService.addLineItem(new GradebookLineItem(gb.getId(), out.resourceId));
            gradebookService.addCell(new GradebookCell(lineItem.getId(), out.studentId, out.grade));
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
                    Optional<String> grade = Optional.ofNullable(doc.getElementsByTagName("resultScore").item(0)).map(res -> res.getChildNodes().item(1).getTextContent());

                    return grade.map(g -> new Outcomes1Request(g, values.get(2), values.get(1), values.get(0)));
                });
        } catch (ParserConfigurationException|SAXException|IOException e) {
            return Optional.empty();
        }
    }

}
