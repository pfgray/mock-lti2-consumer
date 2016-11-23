app.directive('gradebooks',
['$http',
function($http) {
  return {
    templateUrl: 'assets/scripts/gradebooks/gradebooks.html',
    replace: true,
    scope: {
      page:"="
    },
    link: function(scope, element, attrs) {
        var PAGE_SIZE = 10;
        scope.loading = true;

        var limit = PAGE_SIZE;
        console.log('page: ')
        var offset = scope.page * PAGE_SIZE;

        $http.get('/api/gradebooks?limit=' + limit + "&offset=" + offset)
          .then(function(gradebooksQuery){

            console.log("total: ", gradebooksQuery.total);
            scope.hasNextPage = gradebooksQuery.total > (limit * offset) + limit;
            scope.hasPrevPage = !(scope.page == 0);

            scope.loading = false;
            console.log('got it:', gradebooksQuery);

            scope.gradebooks = gradebooksQuery.data.results;

          }, function(){
            scope.loading = false;
          });
    }
  }
}]);
