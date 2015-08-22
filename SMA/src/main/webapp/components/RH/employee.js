var app = angular.module('SMA');
app.controller('employee', ['$scope', '$rootScope', '$http',
    function ($scope, $rootScope, $http) {
        $scope.employee = new Object();
        $scope.errors= new Array();


        $scope.getPermisionarios = function () {
            $http.get('getPermisionarios', {}).success(function (data) {
                $scope.permisionarios = data;
            }).error(function (errors) {
                $scope.errors.push(error.message);
            });
        };

        $scope.getDepartments = function () {
            $http.get('getDepartments', {}).success(function (data) {
                $scope.departments = data;
            }).error(function (errors) {
                $scope.errors.push(error.message);
            });
        };

        $scope.getPermisionarios();
        $scope.getDepartments();

        $scope.addEmployee = function () {
            $http.post('addEmployee', $scope.employee).success(function (data) {
                $scope.msg = data.message;
            }).error(function (error) {
                $scope.errors.push(error.message);
            });
        };

        $scope.selectedDepartment = function (selected) {
            $scope.employee.department = selected.title;
        };

        $scope.selectedPermisionario = function (selected) {
            $scope.employee.operator.permisionario = selected.title;
        };


    }]);
