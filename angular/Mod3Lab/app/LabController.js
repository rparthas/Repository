angular.module('app')
  .controller('LabController', [
    function() {
      var vm = this;
      vm.dt = new Date();
      vm.options = {
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        showWeeks: false,
        startingDay: 1
      };

      vm.alerts = [];
      vm.addDanger = function() {
        addAlert('danger', 'Danger, Will Robinson! Danger!');
      };
      vm.addWarning = function() {
        addAlert('warning', 'Warning! Warning! Alien approaching!');
      };
      vm.dismissAlert = function(index) {
        vm.alerts.splice(index, 1);
      };

      function addAlert(type, text) {
        vm.alerts.push({
          type: type,
          text: text
        });
      }
    }

  ]);
