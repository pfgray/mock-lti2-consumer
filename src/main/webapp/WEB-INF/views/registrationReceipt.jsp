<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <script>
      window.toolProxy = ${ proxy };
      document.addEventListener("DOMContentLoaded", function(event) {
        //do work
        var tool = document.getElementById("toolProxy")
        tool.innerHTML = JSON.stringify(window.toolProxy, null, 2);
      });
    </script>
    <body>
      Okay, we got receipt:
      <pre id="toolProxy">

      </pre>
    </body>
</html>
