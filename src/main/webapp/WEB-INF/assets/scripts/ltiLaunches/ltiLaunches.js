app.directive('ltiLaunches',
['$http', 'LtiLaunchService', 'SampleUsers', 'SampleCourses', 'sampleToolsService', '$uibModal',
function($http, ltiLaunchService, SampleUsers, SampleCourses, sampleToolsService, $uibModal) {
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
        var launch = scope.launch;
        var params = _.pickBy(_.assign({}, launch.user, launch.context, launch.outcomes));

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
            launchParameters: resp.data.launch
          })
        });
      };

      scope.showGradebook = function(gradebookId){
        console.log("Showing gradebook for:", gradebookId);
        var modalInstance = $uibModal.open({
          animation: true,
          templateUrl: 'gradebookModal.html',
          size: 'lg',
          controller: ['$scope', '$uibModalInstance', function($scope, $uibModalInstance){
            $scope.gradebookId = gradebookId;
            $scope.close = function() {
              $uibModalInstance.dismiss();
            }
          }]
        });
        //gradebookModal.html
      };

      scope.outcomesVersions = ["1.1", "2"];

      scope.outcomesVersion = "1.1";
      scope.launch.outcomes = {};
      scope.launch.outcomes.lis_outcome_service_url = window.web_context_url + "outcomes/v1.1/gradebook";
      scope.launch.outcomes.lis_result_sourcedid = "12345";

    }
  };
}]);
