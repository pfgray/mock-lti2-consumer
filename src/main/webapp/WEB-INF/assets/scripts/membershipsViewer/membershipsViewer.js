app.directive('membershipsViewer',
['$http',
function($http) {
  return {
    templateUrl: 'assets/scripts/membershipsViewer/membershipsViewer.html',
    replace: true,
    scope: {},
    link: function(scope, element, attrs) {
        scope.loading = false;

        scope.fetchMembers = function(){
          console.log("fetching members");
          scope.loading = true;
          $http.get('/getMemberships?membershipUrl=' + window.membershipUrl + '&key=' + window.key + '&secret=' + window.secret)
            .then(function(memberships){
              scope.loading = false;
              scope.memberships = memberships;
            }, function(){
              scope.loading = false;
            });
        }

        scope.stringIt = function(obj) {
          return JSON.stringify(obj);
        }
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

