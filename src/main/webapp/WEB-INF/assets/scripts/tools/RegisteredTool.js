app.factory('RegisteredTool', ['LtiLaunchService', function (ltiLaunchService) {

    var RegisteredTool = function(tool){
        tool.button = {
            label:"Launch Tool",
            icon:"fa-rocket",
            onClick:function(scope){
                console.log("getting launch for tool:", tool);
                //get the lti launch request & then redirect the user there.
                ltiLaunchService.getLaunchForTool(tool)
                .success(ltiLaunchService.postLaunch);
            }
        };
        tool.liClass = 'list-group-item-success';
        tool.latestToolProxySubmission = JSON.parse(tool.latestToolProxySubmission);
        return tool;
    };

    return RegisteredTool;

}]);