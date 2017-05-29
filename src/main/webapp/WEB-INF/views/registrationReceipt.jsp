<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <script>
      window.toolProxy = ${ proxyJson };
      document.addEventListener("DOMContentLoaded", function(event) {
        //do work
        var tool = document.getElementById("toolProxyDiv");
        // tool.innerHTML = JSON.stringify(window.toolProxy, null, 2);

        window.toolProxy.resourceHandlers.forEach(function(handler){
          var handlerDiv = document.createElement("div");
          handlerDiv.innerHTML = "<strong>" + handler.name + "</strong> with placements:";

          handler.messages.forEach(function(message){
            var messageDiv = document.createElement("div");

            // todo: what are the known message types, and what do they mean?

            messageDiv.innerHTML = "<strong>" + message.message_type + "</strong>" + "<span> @ " + message.message_type + "</span>";

            handlerDiv.appendChild(messageDiv);
            // todo: loop through message.enabled_capability and message.parameter to show admins
            // what this tool could access
          });
          tool.appendChild(handlerDiv);
        })
      });
    </script>
    <body>
      <strong>${ proxy.defaultUrl }</strong> would like to add:
      <div id="toolProxyDiv"/>
    </body>
</html>
