/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.imsglobal.aspect.Lti;
import org.imsglobal.basiclti.LtiVerificationResult;
import org.imsglobal.lti2.LTI2Config;
import org.imsglobal.lti2.objects.consumer.ServiceOffered;
import org.imsglobal.lti2.objects.consumer.ToolConsumer;
import org.imsglobal.lti2.objects.consumer.ToolConsumer.LtiCapability;
import org.imsglobal.lti2.objects.provider.ToolProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author paul
 */
@Controller
public class LtiToolController {

    @Autowired
    LTI2Config config;
    
    @Autowired
    LtiToolService ltiToolService;
    
    private final static Logger logger = Logger.getLogger(LtiToolController.class.getName()); 
    
    @RequestMapping(value = "/api/tools", method = RequestMethod.GET)
    public ResponseEntity getTools(){
        return new ResponseEntity(ltiToolService.getAll(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/tools/{toolId}", method = RequestMethod.GET)
    public ResponseEntity getToolForId(@PathVariable("toolId") Integer toolId){
        return new ResponseEntity(ltiToolService.getToolForId(toolId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/tools", method = RequestMethod.POST)
    public ResponseEntity addTool(@RequestBody LtiTool tool){
        return new ResponseEntity(ltiToolService.addTool(tool), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/profile", method = RequestMethod.GET)
    public ResponseEntity getConsumerProfile(HttpServletRequest request, HttpServletResponse response, @RequestParam String toolId) {
        response.setHeader("Content-type","application/vnd.ims.lti.v2.ToolConsumerProfile+json");
        ToolConsumer me = new ToolConsumer("guid", "version", "tcp", config);
        me.addCapabilites(Arrays.asList(LtiCapability.BASICLTI_LAUNCH, LtiCapability.USER_ID, LtiCapability.USER_IMAGE, LtiCapability.COURSE_SECTION_ID, LtiCapability.MEMBERSHIP_ROLE));
        String endpoint = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/api/tool_proxy_registration?toolId=" + toolId;
        me.addServiceOffered(new ServiceOffered(endpoint, "tcp:ToolProxy.collection", "RestService", ToolProxy.CONTENT_TYPE, "POST"));
        return new ResponseEntity(me, HttpStatus.OK);
    }

    @Lti
    @RequestMapping(value = "/api/tool_proxy_registration", method = RequestMethod.POST)
    public ResponseEntity registerConsumerProfile(HttpServletRequest request, LtiVerificationResult result, @RequestBody ToolProxy toolProxy, @RequestParam Integer toolId) throws JsonProcessingException {
        if(!result.getSuccess()){
            return new ResponseEntity(result.getError() + result.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            LtiToolProxy ltiToolProxy = new LtiToolProxy();
            
            //TODO: refactor this whole block... there needs to be a better way!
            ObjectMapper mapper = new ObjectMapper();
            
            //find the base url for lti launch messages
            String defaultBaseUrl = "";
            String secureBaseUrl = "";
            if(toolProxy.getTool_profile().getBase_url_choice().isArray()){
                for(JsonNode url_choice : toolProxy.getTool_profile().getBase_url_choice()){
                    System.out.println("Looking at choice's selector: " + url_choice.get("selector"));
                    if(url_choice.get("selector") != null
                       && url_choice.get("selector").get("applies_to") != null
                       && url_choice.get("selector").get("applies_to").isArray()){
                        for(JsonNode url_application : url_choice.get("selector").get("applies_to")){
                            //Does 'MessageHandler' in this context refer to an *lti launch message* ?
                            if(url_application.asText().equals("MessageHandler")){
                                
                                defaultBaseUrl = url_choice.get("default_base_url").asText();
                                secureBaseUrl = url_choice.get("secure_base_url").asText();
                            }
                        }
                    }
                }
            }
            logger.info("Extracted base url: " + defaultBaseUrl);
            logger.info("Extracted secure base url: " + secureBaseUrl);


            //find the basic-lti-launch-request.
            //This part I'm unsure of. Why is there a "message" attribute on the tool_profile itself, as well as one each resource_handler definition?
            for(JsonNode resource_handler : toolProxy.getTool_profile().getResource_handler()){
                if(resource_handler.get("message") != null
                   && resource_handler.get("message").isArray()){
                    for(JsonNode message : resource_handler.get("message")){
                        if(message.get("message_type") != null
                           && message.get("message_type").asText().equals(ToolConsumer.LtiCapability.BASICLTI_LAUNCH)){
                            ltiToolProxy.setDefaultUrl(defaultBaseUrl + message.get("path").asText());
                            ltiToolProxy.setSecureUrl(secureBaseUrl + message.get("path").asText());
                        }
                    }
                }
            }

            ltiToolProxy.setKey(toolProxy.getTool_proxy_guid());
            ltiToolProxy.setSecret(toolProxy.getSecurity_contract().getShared_secret());
            
            //change the tool's state to "registered"
            LtiTool ltiTool = ltiToolService.getToolForId(toolId);
            ltiTool.getToolProxies().add(ltiToolProxy);
            ltiTool.setState(LtiTool.State.registered);
            ltiToolProxy.setTool(ltiTool);
            ltiToolService.updateTool(ltiTool);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }
}
