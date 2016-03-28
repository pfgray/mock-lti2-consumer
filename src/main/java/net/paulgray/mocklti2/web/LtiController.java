package net.paulgray.mocklti2.web;

import org.imsglobal.lti.launch.LtiSigner;
import org.imsglobal.lti.launch.LtiSigningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by paul on 3/27/16.
 */
@Controller
public class LtiController {

    @Autowired
    LtiSigner ltiSigner;

    @RequestMapping(value = "/api/signedLaunch", method = RequestMethod.POST)
    public Map<String, String> getSignedLaunch(UnsignedLtiLaunchRequest request) throws LtiSigningException {
        return ltiSigner.signParameters(
                request.getLaunchParameters(),
                request.getKey(),
                request.getSecret(),
                request.getUrl(),
                request.getMethod()
        );
    }
}
