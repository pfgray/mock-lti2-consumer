app.service('LtiLaunchService', ['$http', function($http){

    var LtiLaunchService = {};

    LtiLaunchService.getLaunchForTool = function(tool){
        return $http.get('api/tools/' + tool.id + '/launch');
    }

    LtiLaunchService.postLaunch = function(data){
        var launchParameters = data.launchParameters;
        var ltiForm = $('<form action="' + data.url + '" method=' + data.method + ' target="_blank" ></form>');
        for(var param in launchParameters){
            ltiForm.append($("<input>").attr("type", "hidden").attr("name", param).val(launchParameters[param]));
        }
        ltiForm.appendTo('body').submit();
    }

    return LtiLaunchService;

}]);