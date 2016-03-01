(function(){
  var app = angular.module('GLIFRP', [
    'ngRoute'
  ]);
  app.config(['$httpProvider','$routeProvider','$locationProvider',function($httpProvider,$routeProvider,$locationProvider) {

    $routeProvider
    .when('/', {templateUrl: 'views/main.html', controller: 'mainController'})
    .otherwise({redirectTo: '/'});

    $locationProvider.html5Mode(true);
  }]);
  
})();