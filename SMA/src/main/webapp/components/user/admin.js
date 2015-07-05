var app = angular.module('SMA');
app.controller('user', ['$scope', '$rootScope', '$http',
    function ($scope, $rootScope, $http) {

        $scope.uname = '';
        $scope.pwd = '';
        $scope.empId = '';

        $scope.selection = {
            ids: {}
        };

        $scope.errors = new Array();

        $http.get('getRoles', {}).success(function (data) {
            $scope.roles = data;
            for (i = 0; i < data.length; i++) {
                $scope.selection.ids[data[i].roleId] = false;
            }
        }).error(function (errors) {
            $scope.errors.push(error.message);
        });

        $http.get('getUsers', {}).success(function (data) {
            $scope.users = data;
        }).error(function (errors) {
            $scope.errors.push(error.message);
        });

        $scope.deleteUser = function (username) {
            $scope.msg = '';
            if (!username) {
                $scope.errors.push('Por favor, seleccione Nombre de usuario');
            } else {
                $http.get('deleteUser?username=' + username, {}).success(function (data) {
                    $scope.msg = data.message;
                }).error(function (errors) {
                    $scope.errors.push(error.message);
                });
            }
        };

        $scope.updateUser = function () {
            var errors = new Array();
            var userObject = {
                username: $scope.uname,
                password: $scope.pwd,
                roleIDs: [],
                empId: null
            };
            userObject.roleIds = new Array();
            $rootScope.validate(userObject.password, 'Contrasena', 4, 8, errors);
            $scope.errors = errors;
            if (errors.length <= 0) {
                for (i = 0; i < $scope.roles.length; i++) {
                    if ($scope.selection.ids[$scope.roles[i].roleId]) {
                        userObject.roleIds.push($scope.roles[i].roleId);
                    }
                }
                    if (userObject.roleIds.length > 0) {
                        $http.post('updateUser', userObject).success(function (data) {
                            $scope.msg = data.message;
                        }).error(function (error) {
                            $scope.errors.push(error.message);
                        });
                    } else {
                        $scope.errors.push('Por favor seleccione una funcion');
                    }
            }
        };


        $scope.selecteduser = function (selected) {
            for (var i = 0; i < $scope.users.length; i++) {
                if ($scope.users[i].username == selected.title) {
                    $scope.uname = $scope.users[i].username;
                    $scope.pwd = $scope.users[i].password;
                    $scope.empId = $scope.users[i].empId;
                    var roleIds = $scope.users[i].roleIds;
                    for (i = 0; i < $scope.roles.length; i++) {
                        if (roleIds.indexOf($scope.roles[i].roleId) != -1) {
                            $scope.selection.ids[$scope.roles[i].roleId] = true;
                        }
                    }
                }
            }

        };


        $scope.addUser = function () {
            var errors = new Array();
            $rootScope.validate($scope.uname, 'Usuario', 4, 8, errors);
            $rootScope.validate($scope.pwd, 'Contrasena', 4, 8, errors);
            $scope.errors = errors;
            $scope.msg = '';
            var roles = new Array();
            if (errors.length <= 0) {
                for (i = 0; i < $scope.roles.length; i++) {
                    if ($scope.selection.ids[$scope.roles[i].roleId]) {
                        roles.push($scope.roles[i].roleId);
                    }
                }
                var user = new Object();
                user.username = $scope.uname;
                user.password = $scope.pwd;
                user.roleIds = roles;
                if ($scope.empId) {
                    user.empId = $scope.empId;
                }
                if (roles.length > 0) {
                    $http.post('addUser', user).success(function (data) {
                        $scope.msg = data.message;
                    }).error(function (error) {
                        $scope.errors.push(error.message);
                    });
                } else {
                    $scope.errors.push('Por favor seleccione una funcion');
                }
            }
        };
    }]);
