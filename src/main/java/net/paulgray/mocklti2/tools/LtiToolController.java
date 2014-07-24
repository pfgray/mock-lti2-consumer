/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author paul
 */
@Controller
public class LtiToolController {
    
    @Autowired
    LtiToolService ltiToolService;
    
    @RequestMapping("/api/tools")
    public ResponseEntity getTools(){
        return new ResponseEntity(ltiToolService.getAll(), HttpStatus.OK);
    }
    
    @RequestMapping("/api/tools/{toolId}")
    public ResponseEntity getToolForId(@PathVariable("toolId") String toolId){
        return new ResponseEntity(ltiToolService.getToolForId(toolId), HttpStatus.OK);
    }
    
}
