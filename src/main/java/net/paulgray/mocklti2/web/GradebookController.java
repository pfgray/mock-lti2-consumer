package net.paulgray.mocklti2.web;

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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

        log.info("Got Lti Outcomes message: \n" + IOUtils.toString(request.getInputStream()));

        /**
         * <?xml version = "1.0" encoding = "UTF-8"?>
         <imsx_POXEnvelopeRequest xmlns = "http://www.imsglobal.org/services/ltiv1p1/xsd/imsoms_v1p0">
         <imsx_POXHeader>
         <imsx_POXRequestHeaderInfo>
         <imsx_version>V1.0</imsx_version>
         <imsx_messageIdentifier>580eb427c8299</imsx_messageIdentifier>
         </imsx_POXRequestHeaderInfo>
         </imsx_POXHeader>
         <imsx_POXBody>
         <replaceResultRequest>
         <resultRecord>
         <sourcedGUID>
         <sourcedId>12345</sourcedId>
         </sourcedGUID>
         <result>
         <resultScore>
         <language>en-US</language>
         <textString>1</textString>
         </resultScore>
         </result>
         </resultRecord>
         </replaceResultRequest>
         </imsx_POXBody>
         </imsx_POXEnvelopeRequest>
         */

        return new ResponseEntity("", HttpStatus.ACCEPTED);
    }
}
