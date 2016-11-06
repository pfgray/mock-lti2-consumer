package net.paulgray.mocklti2.web;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by nicole on 11/6/16.
 */
public class HttpUtils {

    public static Optional<String> getOrigin(HttpServletRequest req) {
        try {
            URL url = new URL(req.getRequestURL().toString());
            return Optional.of(url.getProtocol() + "//" + url.getHost() + getPort(req));
        } catch(MalformedURLException e) {
            return Optional.empty();
        }
    }

    private static String getPort(HttpServletRequest req) {
        final int port = req.getRemotePort();
        //don't include the port if http 80 or https 443
        if(req.isSecure() && port == 443 || !req.isSecure() && port == 80){
            return "";
        } else {
            return ":" + port;
        }

    }
}
