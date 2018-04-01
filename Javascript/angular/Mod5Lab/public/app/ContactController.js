angular.module('app').controller('ContactController', [
  function() {
    var vm = this;
    vm.alert = {
      msg: ''
    };
    vm.addAlert = function() {
      vm.alert.msg = 'Mail Sent';
    };
    vm.closeAlert = function() {
      vm.alert.msg = '';
    }
  }
]);
