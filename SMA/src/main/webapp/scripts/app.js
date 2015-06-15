var app = angular.module('SMA', ['ngRoute']);
app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'views/login.html',
                controller: 'login'
            }).
            when('/login', {
                templateUrl: 'views/login.html',
                controller: 'login'
            }).
            when('/main',{
                templateUrl: 'views/main.html',
                controller: 'main'
            });
    }]);

app.controller('login',['$scope','$rootScope' ,function ($scope,$rootScope) {
    $rootScope.showMenu =false;
    $scope.login= function(){
       $rootScope.username= $scope.username;
    };
}]);
app.controller('main',['$scope','$rootScope' ,function ($scope,$rootScope) {
    $rootScope.showMenu =true;
}]);

