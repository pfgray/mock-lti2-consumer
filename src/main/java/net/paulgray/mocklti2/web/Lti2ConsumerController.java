/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.paulgray.lti.launch.LtiOauth10aSigner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import net.paulgray.lti.launch.LtiSigner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/getMemberships", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<ObjectNode> launch(
        @RequestParam("membershipUrl") String membershipUrl,
        @RequestParam("key") String key,
        @RequestParam("secret") String secret
    ) throws Exception {

        LtiSigner signer = new LtiOauth10aSigner();

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
