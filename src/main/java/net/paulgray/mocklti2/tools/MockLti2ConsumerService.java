package net.paulgray.mocklti2.tools;

import org.imsglobal.aspect.LtiKeySecretService;

/**
 * Created by pgray on 8/30/14.
 */
public class MockLti2ConsumerService implements LtiKeySecretService {

    @Override
    public String getSecretForKey(String s) {
        return "reg_password";
    }
}
