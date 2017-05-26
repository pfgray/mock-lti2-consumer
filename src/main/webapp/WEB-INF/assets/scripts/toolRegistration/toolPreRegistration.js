app.directive('toolPreRegistration',
['$http', '$uibModal',
function($http, $uibModal) {
  return {
    templateUrl: 'assets/scripts/toolRegistration/toolPreRegistration.html',
    replace: true,
    scope: {
      toolUrl: '='
    },
    link: function(scope, element, attrs) {
        scope.options = [{
          label: ''
        }];

        scope.register = function(){

          // open modal at:

          var modalInstance = $uibModal.open({
            template: '<iframe src="/registerTool?toolurl=' + scope.toolUrl + '" />',
            controller: 'ModalInstanceCtrl',
            controllerAs: '$ctrl',
            size: 'lg',
            windowClass: 'registerModal',
            controller: ['$scope', function($scope) {
            }]
          });

          //  /registerTool?toolurl=

        }

    }
  }
}]);
