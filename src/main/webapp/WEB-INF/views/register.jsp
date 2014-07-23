<%-- 
    Document   : welcome
    Created on : Jul 17, 2014, 10:56:02 PM
    Author     : paul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MockLti2Consumer</title>
    </head>
    <body>
        <h1>Register your tool:</h1>
        <form>
            <label>
                Lti launch url:
                <input type="text" name="launch_url"/>
            </label>
            <label>
                Tool name:
                <input type="text" name="id"/>
            </label>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
