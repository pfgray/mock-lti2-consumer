app.directive('gradebook',
['$http', '$uibModal',
function($http, $uibModal) {
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
            console.log('got the gradebook:', gradebook.data);

            scope.students =
              gradebook.data.columns
                .flatMap(col => col.cells)
                .map(cell => cell.studentId)
                .unique(id => id);

            scope.gradebook = gradebook.data;
          }, function(){
            scope.loading = false;
          });

        scope.getStudentSource = function(studentId, columnId){
            var column = scope.gradebook.columns.find(c => c.column.id === columnId);
            if(column) {
                var cell = column.cells.find(c => c.studentId === studentId);
                if(cell && cell.grade) {
                  return cell.source;
                }
            }
            return null;
        }

        scope.getStudentGradeForColumn = function(studentId, columnId){
            console.log('Getting Student grade for: ', studentId, columnId);
            console.log('in: ', scope.gradebook.columns);
            var column = scope.gradebook.columns.find(c => c.column.id === columnId);
            if(column) {
              console.log('found column: ', column, 'now looking in:', column.cells);
              var cell = column.cells.find(c => c.studentId === studentId);
              if(cell && cell.grade) {
                return cell.grade;
              }
            }
            return "";
        }

        scope.getStudentGradeStyle = function(studentId, columnId) {
          var grade = scope.getStudentGradeForColumn(studentId, columnId);
          if(grade == ""){
            return "";
          } else if(grade <= 59){
            return "cell-danger";
          } else if(grade <= 79) {
            return "cell-warning";
          } else {
            return "cell-success";
          }
        }

        scope.showSource = function(title, source){
            $uibModal.open({
              ariaLabelledBy: 'modal-title-top',
              ariaDescribedBy: 'modal-body-top',
              templateUrl: 'assets/scripts/gradebook/source.html',
              size: 'md',
              controller: ['$scope', '$uibModalInstance', function($scope, $uibModalInstance){
                $scope.title = title;
                $scope.source = source;
                $scope.close = function() {
                  $uibModalInstance.dismiss();
                }
              }]
            });
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

