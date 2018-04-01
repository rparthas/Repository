angular.module('app').controller('UserDisplayController', ['UserService', userDisplayController]);

function userDisplayController(userService){
    var vm = this;
    vm.users = [];
    vm.executeSearch = executeSearch;

    function executeSearch(){
        userService.getUsers().then(getUsersSuccess);
    }

    function getUsersSuccess(result){
        vm.users = result;
    }
}