/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.web;

import org.imsglobal.lti2.LTI2Config;
import org.imsglobal.lti2.objects.ToolConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pgray
 */
@Controller
public class Lti2ConsumerController {
    
    @RequestMapping(value = "/")
    public String getWelcome(HttpServletRequest request, ModelMap model) {
        model.addAttribute("contextPath", request.getContextPath());
        model.addAttribute("contextUrl", request.getRequestURL());
        model.addAttribute("contextUri", request.getRequestURI());
        return "welcome";
    }


}
