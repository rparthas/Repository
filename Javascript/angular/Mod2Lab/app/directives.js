angular.module('app').directive('staticMessageDirective', function() {
  return {
    restrict: 'EA',
    template: '<h2>This is from a directive</h2>'
  };
});

angular.module('app').directive('h3Directive', function() {
  return {
    restrict: 'E',
    scope: {
      title: '@'
    },
    template: '<h3>{{title}}</h3>'
  };
});

angular.module('app').directive('personDirective', function() {
  return {
    restrict: 'E',
    scope: {
      person: '=',
      action: '&'
    },
    template: 'Name: <input type="text" ng-model="person.name" class="form-control" />' +
      'Pen Name: <input type="text" ng-model="person.penName" class="form-control" />' +
      '<input type="button" ng-click="action()" value="Show" class="btn btn-primary"/>' +
      '<pre>{{person | json}}</pre>',
    link: function(scope, element, attrs) {
      element.bind('mouseenter', function() {
        element.css('background-color', 'red')
      });

      element.bind('mouseleave', function() {
        element.css('background-color', 'silver')
      });
    }
  };
});


angular.module('app').directive('authorDirective', function() {
  return {
    restrict: 'E',
    replace: true,
    scope: {
      authors: '='
    },
    template: '<div class="col-sm-12" ng-repeat="author in authors"> \
             <div class="col-sm-3">{{author.name}}</div> \
             <div class="col-sm-3">{{author.nation}}</div> \
             <div class="col-sm-3">{{author.dates}}</div> \
             <div class="col-sm-3"><input type="button" class="btn btn-primary" value="Details" ng-click="vm.display(author)"/></div> \
             </div>',
    link: function(scope, element, attrs) {
      element.on('mouseenter', function() {
        element.css('background-color', 'silver')
      });

      element.on('mouseleave', function() {
        element.css('background-color', 'white')
      });
    }
  };
});
