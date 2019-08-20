app.directive('ltiLaunches',
['$http', 'LtiLaunchService', 'SampleUsers', 'SampleCourses', 'sampleToolsService', '$uibModal',
function($http, ltiLaunchService, SampleUsers, SampleCourses, sampleToolsService, $uibModal) {
  return {
    templateUrl: 'assets/scripts/ltiLaunches/lti-launches.html',
    replace: true,
    link: function(scope, element, attrs) {
      scope.launch = {};

      scope.signatureMethods = ['HMAC-SHA1', 'HMAC-SHA256'];

      scope.sampleTools = sampleToolsService.getTools();
      scope.sampleUsers = SampleUsers;
      scope.sampleContexts = SampleCourses;

      console.log('got tools:', scope.sampleTools);

      scope.launch.tool = scope.sampleTools.length > 0 ? scope.sampleTools[0] : {};
      scope.launch.user = SampleUsers[0];
      scope.launch.context = SampleCourses[0];

      scope.launch.signature_method = scope.signatureMethods[0];

      scope.launch.config = {};
      scope.launch.config.custom_params = '';

      scope.urlAndKey = function(tool){
        return tool.url + ' / ' + tool.key;
      }

      scope.getSignedParameters = function(){
        var launch = scope.launch;
        var params = _.pickBy(_.assign({}, launch.user, launch.context, launch.outcomes));

        function mergeParams(prefix) {
          return function(params, param){
            if(param !== ''){
              var i = param.indexOf('=');
              if(i === -1){
                // just include params
                params[prefix + param] = '';
                return params;
              } else {
                var key = param.substr(0, i);
                var value = param.substr(i + 1);
                params[prefix + key] = value;
                return params;
              }
            } else {
              return params;
            }
          }
        }

        var params = scope.launch.config.custom_params
          .split('\n')
          .reduce(mergeParams(''), params);

        if(scope.outcomesOnepOne) {
          params.lis_outcome_service_url = window.web_origin + "/outcomes/v1.1/gradebook";
        }

        if(scope.gradebookServices) {
          params.custom_lineitems_url = window.web_origin + "/api/ags/" + params.context_id + "/lineitems";
        }

        if(scope.membershipServices){
          params.custom_context_memberships_url = window.web_origin + "/contexts/" + params.context_id + "/memberships";
        }
        if(scope.contentItem){
          // todo: 
        }

        params.oauth_signature_method = scope.launch.signature_method;

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
          console.log('new window? ', scope.newWindow);

          sampleToolsService.addOrSetTool(scope.launch.tool);
          scope.sampleTools = sampleToolsService.getTools();

          ltiLaunchService.postLaunch({
            url:scope.launch.tool.url,
            target: scope.newWindow ? '_blank' : 'launch_frame',
            launchParameters: resp.data.launch
          });
          if(!scope.newWindow) {
            scope.launched = true;
            iFrameResize({
                log:true,
                checkOrigin:false,
                scrolling: 'auto'
            }, '#launch_frame');
          }
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
      };

      scope.unlaunch = function(){
        document.getElementById('launch_frame').src = '';
        scope.launched = false;
      }

      scope.outcomesOnepOne = true;
      scope.membershipServices = true;
      scope.contentItem = false;
      scope.gradebookServices = true;
      scope.newWindow = false;

      scope.configureTools = function(){
        //show the modal
        //show the modal
        var modalInstance = $uibModal.open({
          animation: true,
          templateUrl: 'configureLaunches.html',
          size: 'sm',
          controller: ['$scope', '$uibModalInstance', function($scope, $uibModalInstance){
            $scope.tools = scope.sampleTools;
            $scope.remove = function(tool) {
              sampleToolsService.removeTool(tool);
              $scope.tools = sampleToolsService.getTools();
              console.log('now showing: ', $scope.tools);
              scope.sampleTools = $scope.tools;
            };
            $scope.close = function() {
              $uibModalInstance.dismiss();
            };
          }]
        });
      }

    }
  };
}]);
