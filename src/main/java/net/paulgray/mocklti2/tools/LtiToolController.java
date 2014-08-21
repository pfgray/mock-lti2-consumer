/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.tools;

import org.imsglobal.lti2.LTI2Config;
import org.imsglobal.lti2.objects.ToolConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity getConsumerProfile(HttpServletResponse response) {
        response.setHeader("Content-type","application/vnd.ims.lti.v2.ToolConsumerProfile+json");
        return new ResponseEntity(new ToolConsumer("guid", "tcp", config), HttpStatus.OK);
    }
    
}
