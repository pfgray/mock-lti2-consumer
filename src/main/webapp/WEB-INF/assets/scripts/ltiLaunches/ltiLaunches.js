app.directive('ltiLaunches',
['$http', 'LtiLaunchService', 'SampleUsers', 'SampleCourses', 'sampleToolsService',
function($http, ltiLaunchService, SampleUsers, SampleCourses, sampleToolsService) {
  return {
    templateUrl: 'assets/scripts/ltiLaunches/lti-launches.html',
    replace: true,
    link: function(scope, element, attrs) {
      scope.launch = {};

      scope.sampleTools = sampleToolsService.getTools();
      scope.sampleUsers = SampleUsers;
      scope.sampleContexts = SampleCourses;

      console.log('got tools:', scope.sampleTools);

      scope.launch.tool = scope.sampleTools.length > 0 ? scope.sampleTools[0] : {};
      scope.launch.user = SampleUsers[0];
      scope.launch.context = SampleCourses[0];

      scope.urlAndKey = function(tool){
        return tool.url + ' / ' + tool.key;
      }

      scope.getSignedParameters = function(){
        var params = _.pickBy(_.assign({}, scope.launch.user, scope.launch.context));

        params['lti_message_type'] = 'basic-lti-launch-request';
        params['lti_version'] = 'LTI-1p0';

        $http.post('/api/signedLaunch', {
          launchParameters: params,
          url: scope.launch.tool.url,
          method: 'POST',
          key: scope.launch.tool.key,
          secret: scope.launch.tool.secret
        }).then(function(resp){
          console.log("got signed request: ", resp);
          sampleToolsService.addOrSetTool(scope.launch.tool);
          ltiLaunchService.postLaunch({
            url:scope.launch.tool.url,
            method: 'POST',
            launchParameters: resp.data
          })
        });
      };
    }
  };
}]);
