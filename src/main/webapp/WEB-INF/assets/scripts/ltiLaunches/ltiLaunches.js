app.directive('ltiLaunches', ['$http', 'LtiLaunchService', function($http, ltiLaunchService) {
  return {
    templateUrl: 'assets/scripts/ltiLaunches/lti-launches.html',
    link: function(scope, element, attrs) {
      scope.launch = {};

      scope.launch.tool = {};
      scope.launch.user = {};
      scope.launch.context = {};

      function postForm(url, form) {
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", "test.jsp");

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("name", "id");
        hiddenField.setAttribute("value", "bob");
        form.appendChild(hiddenField);
        document.body.appendChild(form);    // Not entirely sure if this is necessary
        form.submit();
      }

      scope.getSignedParameters = function(){
        console.log("###prepping launch....");
        var params = _.pickBy(_.assign({}, scope.launch.user, scope.launch.context));

        $http.post('/api/signedLaunch', {
          launchParameters: params,
          url: scope.launch.tool.url,
          method: 'POST',
          key: scope.launch.tool.key,
          secret: scope.launch.tool.secret
        }).then(function(resp){
          console.log("####got: ", resp);
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