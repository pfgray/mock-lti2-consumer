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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <base href="/">

        <title>MockLti2Consumer</title>
        <script src="assets/lib/lodash.js/4.6.1/lodash.min.js"></script>
        <script src="assets/lib/jquery/2.1.1/jquery.min.js"></script>
        <script src="assets/lib/angular.js/1.5.2/angular.min.js"></script>
        <script src="assets/lib/angular-ui-router/0.2.18/angular-ui-router.min.js"></script>
        <script src="assets/lib/angular-ui-bootstrap/2.2.0/ui-bootstrap-tpls.min.js"></script>
        <script src="assets/scripts/mock-consumer.js"></script>
        <script src="assets/scripts/gradebook/gradebook.js"></script>
        <script src="assets/scripts/gradebooks/gradebooks.js"></script>
        <script src="assets/scripts/toolRegistration/toolPreRegistration.js"></script>
        <script src="assets/scripts/ltiLaunches/ltiLaunches.js"></script>
        <script src="assets/scripts/sampleData/SampleUsers.js"></script>
        <script src="assets/scripts/sampleData/SampleCourses.js"></script>
        <script src="assets/scripts/sampleData/sampleToolsService.js"></script>
        <script src="assets/scripts/ToolController.js"></script>
        <script src="assets/scripts/services/LtiLaunchService.js"></script>
        <script src="assets/scripts/tools/AddedTool.js"></script>
        <script src="assets/scripts/tools/RegisteredTool.js"></script>

        <script>
            window.web_origin = "${origin}";
        </script>

        <link rel="stylesheet" href="assets/styles/lib/bootstrap/3.2.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="assets/styles/lib/font-awesome/4.1.0/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="assets/styles/consumer.css"/>
    </head>
    <body ng-app="mock-consumer">
        <div class="container main">
            <div class="row">
                <h1>Mock LTI 2.0 Consumer</h1>
            </div>
        </div>
        <div class="container main">
            <div class="row">
                <ul class="nav nav-tabs" role="tablist">
                    <li ng-class="{active: $state.includes('manual')}"><a ui-sref="manual">Manual Launches</a></li>
                    <li ng-class="{active: $state.includes('tools')}"><a ui-sref="tools">LTI Tool Registration</a></li>
                    <li ng-class="{active: $state.includes('gradebooks')}"><a ui-sref="gradebooks">Gradebooks</a></li>
                </ul>
                <div ui-view></div>
            </div>
        </div>
    </body>
</html>
