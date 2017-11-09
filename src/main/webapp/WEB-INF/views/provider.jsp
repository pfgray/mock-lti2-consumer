<%--
    Document   : provider
    Created on : May 24, 2014, 10:58:04 PM
    Author     : pgray
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
        <base href="/">

        <title>MockLti</title>

        <script src="assets/lib/lodash.js/4.6.1/lodash.min.js"></script>
        <script src="assets/lib/jquery/2.1.1/jquery.min.js"></script>
        <script src="assets/lib/angular.js/1.5.2/angular.min.js"></script>
        <script src="assets/lib/angular-ui-router/0.2.18/angular-ui-router.min.js"></script>
        <script src="assets/lib/angular-ui-bootstrap/2.2.0/ui-bootstrap-tpls.min.js"></script>
        <script src="assets/lib/iframe-resizer/3.5.14/iframeResizer.js"></script>
        <script src="assets/scripts/provider.js"></script>
        <script src="assets/scripts/membershipsViewer/membershipsViewer.js"></script>

        <script>
            window.membershipUrl = "${membershipUrl}";
            window.key = "${key}";
            window.secret = "${secret}";
        </script>
    </head>
    <body ng-app="provider">
        <memberships-viewer></memberships-viewer>
    </body>
</html>
