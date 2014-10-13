app.factory('RegisteredTool', ['LtiLaunchService', function (ltiLaunchService) {

    var RegisteredTool = function(tool){
        tool.button = {
            label:"Launch Tool",
            icon:"fa-rocket",
            onClick:function(scope){
                console.log("getting launch for tool:", tool);
                //get the lti launch request & then redirect the user there.
                ltiLaunchService.getLaunchForTool(tool)
                .success(function(data){
                    var launchParameters = data.launchParameters;
                    console.log("got launch from api: ", data);
                    var ltiForm = $('<form action="' + data.url + '" method=' + data.method + ' target="_blank" ></form>');
                    for(var param in launchParameters){
                        ltiForm.append($("<input>").attr("type", "hidden").attr("name", param).val(launchParameters[param]));
                    }
                    ltiForm.appendTo('body').submit();
                })
            }
        };
        tool.liClass = 'list-group-item-success';
        return tool;
    };

    return RegisteredTool;

}]);