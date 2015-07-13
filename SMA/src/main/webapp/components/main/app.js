var app = angular.module('SMA', ['ngRoute', 'angucomplete-alt']);
app.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {
        $routeProvider.
            when('/main', {
                templateUrl: 'components/main/main.html',
                controller: 'login'
            })
            .when('/login', {
                templateUrl: 'components/main/login.html',
                controller: 'login'
            })
            .when('/addUser', {
                templateUrl: 'components/user/addUser.html',
                controller: 'user'
            })
            .when('/updateUser', {
                templateUrl: 'components/user/updateUser.html',
                controller: 'user'
            })
            .when('/addEmployee', {
                templateUrl: 'components/RH/addEmployee.html',
                controller: 'employee'
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

    $rootScope.validate = function (element, field, minlength, maxlength, errors) {
        if (element == null) {
            errors.push(field + " no puede estar vacio ");
            return;
        }
        if (element.length > maxlength) {
            errors.push(field + " longitud no puede ser mayor que " + maxlength);
            return;
        }
        if (element.length < minlength) {
            errors.push(field + " longitud no puede ser menor que " + minlength);
            return;
        }
    };

}]);

app.controller('main', ['$scope', '$rootScope', function ($scope, $rootScope) {

}]);

