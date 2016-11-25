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
        var MAX_PAGES_DISPLAYED = 5;
        scope.loading = true;

        scope.page = parseInt(scope.page);

        var limit = PAGE_SIZE;
        console.log('page: ');
        var offset = (scope.page - 1) * PAGE_SIZE;

        $http.get('/api/gradebooks?limit=' + limit + "&offset=" + offset)
          .then(function(resp){
            var gradebooksQuery = resp.data;

            console.log("total: ", gradebooksQuery.total);
            console.log("limit: ", scope.page, offset);
            scope.hasNextPage = gradebooksQuery.total > (scope.page * limit);
            scope.hasPrevPage = scope.page > 1;

            console.log(gradebooksQuery.total + '>' +  '(' + scope.page  + '*'  + limit + ')');

            // create an array of pages with
            var totalPages = Math.ceil(gradebooksQuery.total / PAGE_SIZE);
            // add from right(if possible), then add from left(if possible),
            scope.pages = getPageLinks([scope.page], totalPages, MAX_PAGES_DISPLAYED - 1);

            scope.loading = false;
            console.log('got it:', gradebooksQuery);

            scope.gradebooks = gradebooksQuery.results;

          }, function(){
            scope.loading = false;
          });
    }
  }
}]);

function getPageLinks(currentPages, totalPages, pagesToAdd){
  console.log('getPageLinks', currentPages, totalPages, pagesToAdd);

  var prepended = false;
  var appended = false;

  if(totalPages > currentPages[currentPages.length - 1] && pagesToAdd > 0){
    currentPages.push(currentPages[currentPages.length - 1] + 1);
    pagesToAdd--;
    appended = true;
  }

  if(currentPages[0] > 1 && pagesToAdd > 0) {
    currentPages.unshift(currentPages[0] - 1);
    pagesToAdd--;
    prepended = true;
  }

  if(!(appended || prepended) || pagesToAdd === 0) {
    return currentPages;
  } else {
    return getPageLinks(currentPages, totalPages, pagesToAdd);
  }
}
