angular.module('app').factory('UserService', ['$http', '$q', userService]);

function userService($http, $q){

    var service = {
        getUsers: getUsers
    };

    return service;

    function getUsers(){
        var deferred = $q.defer();

        $http.get('http://reqres.in/api/users').then(function(result){
            deferred.resolve(result.data.data);
        });

        return deferred.promise;
    }
}