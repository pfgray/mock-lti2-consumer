var app = angular.module('mock-consumer', []);

app.controller('WelcomeController', ['$scope', function($scope){
    $scope.message = "welcome";
}]);

