angular.module('app')
  .controller('LabController', [
    function() {
      var vm = this;
      vm.person = {
        name: 'Samuel Clemens',
        penName: 'Mark Twain'
      };
      vm.authors = [{
          name: 'Mark Twain',
          nation: 'American',
          dates: '1835-1910'
        },
        {
          name: 'A.A.Milne',
          nation: 'English',
          dates: '1882-1956'
        }
      ];
      vm.show = function() {
        alert(JSON.stringify(vm.person));
      }
      vm.display = function(author) {
        console.log(author);
      }
    }
  ]);
