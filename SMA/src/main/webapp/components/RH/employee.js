var app = angular.module('SMA');
app.controller('employee', ['$scope', '$rootScope', '$http', '$routeParams','$location',
    function ($scope, $rootScope, $http, $routeParams,$location) {

        $scope.errors = new Array();
        $scope.employees = new Array();

       if(!$routeParams.action || $routeParams.action!= 'modify'){
           $scope.employee = new Object();
           $scope.modify =false;
       }else{
           $scope.modify =true;
       }

        $scope.getEmployees = function () {
            $http.get('getAllEmployees', {}).success(function (data) {
                $scope.employees = data;
            }).error(function (errors) {
                $scope.errors.push(error.message);
            });
        };


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
        $scope.getEmployees();

        $scope.deleteEmployee = function (employee) {
            $http.get('deleteEmployee?id=' + employee.id, {}).success(function (data) {
                $scope.msg = data.message;
                $scope.getEmployees();
            }).error(function (errors) {
                $scope.errors.push(error.message);
            });
        };

        $scope.updateEmployee = function (employee) {
            $rootScope.employee = employee;
            $location.path('/addEmployee/modify');
        };

        $scope.addEmployee = function () {
            var errors = new Array();
            $rootScope.validate($scope.employee.department, 'Estatus', 4, '*', errors);
            $rootScope.validate($scope.employee.clave, 'Clave', 4, '*', errors);
            $rootScope.validate($scope.employee.name, 'Nombre', 4, '*', errors);
            $rootScope.validate($scope.employee.surname1, 'Appellido Paterno', 4, '*', errors);
            $rootScope.validate($scope.employee.surname2, 'Appellido Materno', 4, '*', errors);
            $rootScope.validate($scope.employee.alias, 'Alias', 4, '*', errors);
            $rootScope.validate($scope.employee.curp, 'Curp', 4, '*', errors);
            $rootScope.validate($scope.employee.birthDate, 'Fecha de Nacimiento', '*', '*', errors);
            $rootScope.validate($scope.employee.birthPlace, 'Lugar de Nacimiento', 4, '*', errors);

            $scope.errors = errors;
            if (errors.length <= 0) {
                $http.post('addEmployee', $scope.employee).success(function (data) {
                    $scope.msg = data.message;
                }).error(function (error) {
                    $scope.errors.push(error.message);
                });
            }
        };

        $scope.selectedDepartment = function (selected) {
            $scope.employee.department = selected.title;
        };

        $scope.selectedPermisionario = function (selected) {
            $scope.employee.permisionario = selected.title;
        };


    }]);
