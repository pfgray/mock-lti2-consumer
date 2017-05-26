<%--
    Document   : welcome
    Created on : Jul 17, 2014, 10:56:02 PM
    Author     : paul
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <body>
      <form id="autopost" method="POST" action="${url}">
        <c:forEach var="e" items="${params}">
           <input type="hidden" name="${e.key}" value="${e.value}"/>
        </c:forEach>
      </form>

      <script>
        document.getElementById('autopost').submit();
      </script>
    </body>
</html>
