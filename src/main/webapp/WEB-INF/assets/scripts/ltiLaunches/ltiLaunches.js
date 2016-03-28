app.directive('ltiLaunches', function() {
  return {
    templateUrl: 'assets/scripts/ltiLaunches/lti-launches.html',
    link: function(scope, element, attrs) {
      scope.launch = {};

      scope.launch.tool = {};
      scope.launch.user = {};
      scope.launch.context = {};
    }
  };
});