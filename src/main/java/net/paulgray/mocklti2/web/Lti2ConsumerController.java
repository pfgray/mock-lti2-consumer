/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.imsglobal.aspect.Lti;
import org.imsglobal.lti.launch.LtiOauthSigner;
import org.imsglobal.lti.launch.LtiSigner;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.imsglobal.lti2.LTI2Config;
import org.imsglobal.lti2.objects.consumer.ToolConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pgray
 */
@Controller
public class Lti2ConsumerController {

    Map<String, String> keys = new HashMap<>();
    {
        keys.put("fake", "fake");
        keys.put("key", "secret");
    }
    
    @RequestMapping(value = {"/", "/tools/**", "/gradebooks/**"})
    public String getWelcome(HttpServletRequest request, ModelMap model) {
        model.addAttribute("origin", HttpUtils.getOrigin(request).orElse(""));
        return "welcome";
    }

    // @Lti
    @RequestMapping(value = "/provider", method = {RequestMethod.POST, RequestMethod.GET})
    public String launch(
        HttpServletRequest request,
        LtiVerificationResult result,
        HttpServletResponse resp,
        ModelMap map
    ) throws Exception {
        if (false) {
            map.put("ltiError", result.getError().toString());
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            return "error";
        } else {
            // map.put("name", result.getLtiLaunchResult().getUser().getId());
            map.put("name", request.getParameter("user_id"));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> params = new HashMap<String, String>();
            for (String param : Collections.list(request.getParameterNames())) {
                params.put(param, request.getParameter(param));
            }
            map.put("params", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params));
            map.put("launch", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.getLtiLaunchResult()));

            map.put("membershipUrl", request.getParameter("custom_context_memberships_url"));
            map.put("key", request.getParameter("oauth_consumer_key"));
            map.put("secret", keys.get(request.getParameter("oauth_consumer_key")));
            return "provider";
        }
    }

    @RequestMapping(value = "/getMemberships", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<ObjectNode> launch(
        @RequestParam("membershipUrl") String membershipUrl,
        @RequestParam("key") String key,
        @RequestParam("secret") String secret
    ) throws Exception {

        LtiSigner signer = new LtiOauthSigner();

        HttpGet request = new HttpGet(membershipUrl);

        signer.sign(request, key, secret);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);


        ObjectMapper mapper = new ObjectMapper();
        // String body = IOUtils.toString();

        System.out.println("got response: " + response.getStatusLine().getStatusCode());

        return new ResponseEntity(mapper.readTree(response.getEntity().getContent()), HttpStatus.OK);
    }

}
