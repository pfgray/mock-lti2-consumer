/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.tools;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.imsglobal.lti2.LTI2Config;
import org.imsglobal.lti2.objects.consumer.ToolConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import org.imsglobal.lti2.objects.consumer.ServiceOffered;
import org.imsglobal.lti2.objects.consumer.ToolConsumer.LtiCapability;
import org.imsglobal.lti2.objects.provider.ToolProxy;

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
    
    @RequestMapping(value = "/api/tools", method = RequestMethod.GET)
    public ResponseEntity getTools(){
        return new ResponseEntity(ltiToolService.getAll(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/tools/{toolId}", method = RequestMethod.GET)
    public ResponseEntity getToolForId(@PathVariable("toolId") String toolId){
        return new ResponseEntity(ltiToolService.getToolForId(toolId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/tools", method = RequestMethod.POST)
    public ResponseEntity addTool(@RequestBody LtiTool tool){
        return new ResponseEntity(ltiToolService.addTool(tool), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/profile")
    public ResponseEntity getConsumerProfile(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-type","application/vnd.ims.lti.v2.ToolConsumerProfile+json");
        ToolConsumer me = new ToolConsumer("guid", "version", "tcp", config);
        me.addCapabilites(Arrays.asList(LtiCapability.BASICLTI_LAUNCH, LtiCapability.USER_ID, LtiCapability.USER_IMAGE, LtiCapability.COURSE_SECTION_ID, LtiCapability.MEMBERSHIP_ROLE));
        String endpoint = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/tool_proxy_registration";
        me.addServiceOffered(new ServiceOffered(endpoint, "tcp:ToolProxy.collection", "RestService", ToolProxy.CONTENT_TYPE, "POST"));
        return new ResponseEntity(me, HttpStatus.OK);
    }
    
}
