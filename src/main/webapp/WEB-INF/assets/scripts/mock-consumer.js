var app = angular.module('mock-consumer', ['ui.router']);

app.config(function($stateProvider, $urlRouterProvider) {
    //
    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/intro");
    //
    // Now set up the states
    $stateProvider
    .state('intro', {
        url: "/intro",
        templateUrl: "assets/templates/partial-intro.html"
    })
    .state('register', {
        url: "/register",
        templateUrl: "assets/templates/partial-register.html"
    });

});