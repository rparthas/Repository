var app = angular.module('SMA');
app.controller('user', [ '$scope', '$rootScope', '$http',
		function($scope, $rootScope, $http) {

			$scope.selection =  {
				ids:{}
			};

			$http.get('getRoles', {}).success(function(data) {
				$scope.roles = data;
				for (i = 0; i < data.length; i++) {
					$scope.selection.ids[data[i].roleId] = false;
				}
			}).error(function(errors) {
				$scope.errors = errors;
			});

			$scope.addUser = function() {
				var errors = new Array();
				$rootScope.validate($scope.uname, 'Usuario', 4, 8, errors);
				$rootScope.validate($scope.pwd, 'Contrasena', 4, 8, errors);
				$scope.errors = errors;
				if (errors.length <= 0) {
					for (i = 0; i < $scope.roles.length; i++) {
						if ($scope.selection.ids[$scope.roles[i].roleId]) {
							var user = new Object();
							user.username = $scope.uname;
							user.password = $scope.pwd;
							user.roleId = $scope.roles[i].roleId;
							if ($scope.empId) {
								user.empId = $scope.empId;
							}
							$http.post('addUser', user).success(function(data) {
								$scope.msg = data.success;
							}).error(function(errors) {
								$scope.errors.push(errors);
							});

						}
					}

				}
			};

		} ]);
