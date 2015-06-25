var app = angular.module('SMA', ['ngRoute']);
app.config(['$routeProvider','$httpProvider',
    function ($routeProvider,$httpProvider) {
        $routeProvider.
            when('/main', {
                templateUrl: 'views/main.html',
                controller: 'login'
            }).
            when('/login', {
                templateUrl: 'views/login.html',
                controller: 'login'
            })
            .otherwise('/login');
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]);

app.controller('login', ['$scope', '$rootScope', '$http', '$location', function ($scope, $rootScope, $http, $location) {
    $rootScope.logout = function () {
        $rootScope.username = null;
        $rootScope.authenticated = false;
    }
    $scope.error = false;
    $scope.login = function () {
        var headers = $scope.credentials ? {
            authorization: "Basic "
            + btoa($scope.credentials.username + ":" + $scope.credentials.password)
        } : {};

        $http.get('login', {headers: headers}).success(function (data) {
            if (data.name) {
                $rootScope.authenticated = true;
                $rootScope.username = data.name;
                $location.path("/main");
            } else {
                $rootScope.authenticated = false;
                $scope.error = true;
            }
        }).error(function () {
            $rootScope.authenticated = false;
            $scope.error = true;
        });
    }
}]);

app.controller('main', ['$scope', '$rootScope', function ($scope, $rootScope) {

}]);

