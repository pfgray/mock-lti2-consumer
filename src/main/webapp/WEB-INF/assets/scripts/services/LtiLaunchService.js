app.service('LtiLaunchService', ['$http', function($http){

    var LtiLaunchService = {};

    LtiLaunchService.getLaunchForTool = function(tool){
        return $http.get('api/tools/' + tool.id + '/launch');
    }

    return LtiLaunchService;

}]);