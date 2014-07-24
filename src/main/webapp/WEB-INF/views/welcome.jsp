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
        <script src="http://cdnjs.cloudflare.com/ajax/libs/angular.js/1.2.20/angular.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.2.10/angular-ui-router.min.js"></script>
        <script src="assets/scripts/mock-consumer.js"></script>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="assets/styles/consumer.css"/>
    </head>
    <body ng-app="mock-consumer">
        <div class="container main">
            <div class="row">
                <h1>Mock Lti2 Consumer</h1>
            </div>
        </div>
        <div class="container main">
            <div class="row">
                <ul class="nav nav-tabs" role="tablist">
                    <li ng-class="{active: $state.includes('intro')}"><a ui-sref="intro">Introduction</a></li>
                    <li ng-class="{active: $state.includes('tools')}"><a ui-sref="tools">LTI Tools</a></li>
                </ul>
                <div ui-view></div>
            </div>
        </div>
    </body>
</html>
