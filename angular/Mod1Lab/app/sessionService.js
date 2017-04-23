angular.module('app').service('sessionService', ['$window', sessionService]);
function sessionService($window) {
  this.save = function(key, value) {
    $window.sessionStorage.setItem(key, value);
  }
  this.get = function(key) {
    return $window.sessionStorage.getItem(key);
  }
  this.clear = function() {
    $window.sessionStorage.clear();
  }
}
