angular.module('app').directive('userDisplayDirective', [userDisplayDirective]);

function userDisplayDirective(){
    var directive = {
        templateUrl: 'app/user.display.template.html',
        restrict: 'E',
        scope: {
            users: '='
        }
    }

    return directive;
}