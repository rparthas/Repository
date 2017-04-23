angular.module('app').controller('sessionController', ['sessionService', 'sessionFactory', function(sessionService, sessionFactory) {
  var vm = this;
  vm.getFactorySession = function() {
    vm.model = {
      name: sessionFactory.get('name'),
      nickname: sessionFactory.get('nickname'),
      status: 'Retrieved by Factory on ' + new Date()
    };
  }
  vm.setFactorySession = function() {
    sessionFactory.save('name', vm.model.name);
    sessionFactory.save('nickname', vm.model.nickname);
    vm.getFactorySession();
  }
  vm.clearFactorySession = function() {
    sessionFactory.clear();
    vm.getFactorySession();
  }
  vm.getServiceSession = function() {
    vm.model = {
      name: sessionService.get('name'),
      nickname: sessionService.get('nickname'),
      status: 'Retrieved by service on ' + new Date()
    };
  }
  vm.setServiceSession = function() {
    sessionService.save('name', vm.model.name);
    sessionService.save('nickname', vm.model.nickname);
    vm.getServiceSession();
  }
  vm.clearServiceSession = function() {
    sessionService.clear();
    vm.getServiceSession();
  }
}]);
