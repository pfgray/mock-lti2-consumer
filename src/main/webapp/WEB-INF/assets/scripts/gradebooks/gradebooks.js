app.directive('gradebooks',
['$http',
function($http) {
  return {
    templateUrl: 'assets/scripts/gradebooks/gradebooks.html',
    replace: true,
    scope: {
    },
    link: function(scope, element, attrs) {
        scope.loading = true;
        $http.get('/api/gradebooks')
          .then(function(gradebooksQuery){
            scope.loading = false;
            console.log('got it:', gradebooksQuery);

            scope.gradebooks = gradebooksQuery.data.results;

          }, function(){
            scope.loading = false;
          });
    }
  }
}]);
