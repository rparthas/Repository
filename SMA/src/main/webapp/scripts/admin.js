var app = angular.module('SMA');
app.controller('user', [ '$scope', '$rootScope', function($scope, $rootScope) {

	$scope.selection = {
		ids : {
			"Admin" : true
		}
	};

	$scope.roles = [ {
		"name" : "Admin",
		"id" : "Admin"
	}, {
		"name" : "Fianza",
		"id" : "Fianza"
	} ];

	$scope.addUser = function() {
		var errors = new Array();
		$rootScope.validate($scope.uname, 'Usuario', 4, 8, errors);
		$rootScope.validate($scope.pwd, 'Contrasena', 4, 8, errors);
		$scope.errors = errors;
		if (errors.length <= 0) {
			$http.get('addUser', {}).success(function(data) {
				$scope.msg = data;
			}).error(function(errors) {
				$scope.errors = errors;
			});
		}
	};

} ]);
