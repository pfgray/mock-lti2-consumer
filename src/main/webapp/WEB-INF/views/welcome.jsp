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

        <title>Lti Consumer</title>
        <script src="assets/lib/lodash.js/4.6.1/lodash.min.js?version=${version}"></script>
        <script src="assets/lib/jquery/2.1.1/jquery.min.js?version=${version}"></script>
        <script src="assets/lib/angular.js/1.5.2/angular.min.js?version=${version}"></script>
        <script src="assets/lib/angular-ui-router/0.2.18/angular-ui-router.min.js?version=${version}"></script>
        <script src="assets/lib/angular-ui-bootstrap/2.2.0/ui-bootstrap-tpls.min.js?version=${version}"></script>
        <script src="assets/lib/iframe-resizer/3.5.14/iframeResizer.js?version=${version}"></script>
        <script src="assets/scripts/mock-consumer.js?version=${version}"></script>
        <script src="assets/scripts/gradebook/gradebook.js?version=${version}"></script>
        <script src="assets/scripts/gradebooks/gradebooks.js?version=${version}"></script>
        <script src="assets/scripts/ltiLaunches/ltiLaunches.js?version=${version}"></script>
        <script src="assets/scripts/sampleData/SampleUsers.js?version=${version}"></script>
        <script src="assets/scripts/sampleData/SampleCourses.js?version=${version}"></script>
        <script src="assets/scripts/sampleData/sampleToolsService.js?version=${version}"></script>
        <script src="assets/scripts/ToolController.js?version=${version}"></script>
        <script src="assets/scripts/services/LtiLaunchService.js?version=${version}"></script>
        <script src="assets/scripts/tools/AddedTool.js?version=${version}"></script>
        <script src="assets/scripts/tools/RegisteredTool.js?version=${version}"></script>

        <script>
            window.web_origin = "${origin}";
            window.version = "${version}";
        </script>

        <link rel="stylesheet" href="assets/styles/lib/bootstrap/3.2.0/css/bootstrap.min.css?version=${version}"/>
        <link rel="stylesheet" href="assets/styles/lib/font-awesome/4.1.0/css/font-awesome.min.css?version=${version}"/>
        <link rel="stylesheet" href="assets/styles/consumer.css?version=${version}"/>
    </head>
    <body ng-app="mock-consumer">
        <div class="container main">
            <div class="row">
                <h1>LTI Consumer</h1>
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
