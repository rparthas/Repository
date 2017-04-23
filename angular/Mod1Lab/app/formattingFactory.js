angular.module('app').factory('formattingFactory', function() {

  return {
    format: function(value) {
      if (value) {
        if (value.length % 2 == 0) {
          return value.toUpperCase();
        } else {
          return value.toLowerCase();
        }
        return value;
      }
    }
  }
});
