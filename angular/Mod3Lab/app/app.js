angular.module('app', ['ui.bootstrap', 'ngAnimate']);
angular.module('app').animation('.alert-animation', [alertAnimation]);

function alertAnimation() {
  return {
    enter: function(element, done) {
      element.css({
          opacity: 0,
          position: "relative",
          top: "10px",
          left: "20px"
        })
        .animate({
          top: 0,
          left: 0,
          opacity: 1
        }, 1000, done);
    },
    leave: function(element, done) {
      element.css({
          top: 0,
          left: 0,
          opacity: 1,
          position: "relative"
        })
        .animate({
          top: "10px",
          left: "20px",
          opacity: 0
        }, 1000, done);
    }
  }
}
