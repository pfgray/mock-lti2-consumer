app.directive('gradebook',
['$http',
function($http) {
  return {
    templateUrl: 'assets/scripts/gradebook/gradebook.html',
    replace: true,
    scope: {
      gradebookId: '='
    },
    link: function(scope, element, attrs) {
        scope.loading = true;
        $http.get('/outcomes/gradebooks/' + scope.gradebookId)
          .then(function(gradebook){
            scope.loading = false;
            console.log('got it:', gradebook.data);

            scope.students =
              gradebook.data.columns
                .flatMap(col => col.cells)
                .map(cell => cell.resultSourcedId)
                .unique(id => id);

            scope.gradebook = gradebook.data;
          }, function(){
            scope.loading = false;
          });
    }
  }
}]);

Object.defineProperty(Array.prototype, 'flatMap', {
    enumerable: false,
    value: function(f) {
      var result = [];
      this.forEach(a => {
        var li = f(a);
        result = result.concat(li);
      });
      return result;
    }
});

Object.defineProperty(Array.prototype, 'unique', {
    enumerable: false,
    value: function(f) {
      var hashMap = this.reduce((map, a) => {
        map[f(a)] = a
        return map;
      }, {});
      return Object.keys(hashMap).map(key => hashMap[key]);
    }
});

