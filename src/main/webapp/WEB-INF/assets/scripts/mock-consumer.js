var app = angular.module('mock-consumer', ['ui.router', 'ui.bootstrap']);

app.config(function($stateProvider, $urlRouterProvider) {
    //
    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/");
    //
    // Now set up the states
    $stateProvider
    .state('manual', {
        url: "/",
        templateUrl: "assets/templates/partial-manual.html"
    })
    .state('tools', {
        url: "/tools",
        templateUrl: "assets/templates/partial-tools.html"
    })
    .state('gradebooks', {
        url: "/gradebooks?page",
        templateUrl: "assets/templates/partial-gradebooks.html",
        params: {
            page: "1"
        },
        controller: ['$scope', '$stateParams', function($scope, $stateParams){
            $scope.page = $stateParams.page
        }]
    })
    .state('gradebook', {
        url: "/gradebooks/:gradebookId",
        templateUrl: "assets/templates/partial-gradebook.html",
        controller: ['$scope', '$stateParams', function($scope, $stateParams){
            console.log('wut:', $scope, $stateParams)
            $scope.gradebookId = $stateParams.gradebookId
        }]
    });

}).run(function($rootScope, $state) {
    $rootScope.$state = $state;
});