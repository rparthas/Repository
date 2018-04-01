describe('userDisplayDirective', function() {

  var $compile, $scope, element, users;

  beforeEach(function() {

    angular.mock.module('app');
    angular.mock.module('compiled-templates');
    angular.mock.inject(function(_$rootScope_, _$compile_) {
      $scope = _$rootScope_.$new();
      $compile = _$compile_;
    });
  });
  it('should compile an element', function() {
    users = [{
        first_name: 'Elvis',
        last_name: 'Presley',
        avatar: 'https://unsplash.it/200'
      },
      {
        first_name: 'Johnny',
        last_name: 'Cash',
        avatar: 'https://unsplash.it/300'
      },
      {
        first_name: 'Carl',
        last_name: 'Perkins',
        avatar: 'https://unsplash.it/400'
      }
    ];
    $scope.users = users;
    element = $compile('<user-display-directive users="users"></user-display-directive>')($scope);
    $scope.$digest();
    expect(element).toBeDefined();
    it('should have users on the scope', function() {
      expect(element.isolateScope().users).toBeDefined();
    });

    it('should have three users on the scope', function() {
      expect(element.isolateScope().users.length).toEqual(users.length);
    });

    it('the element should have the users in it\'s html', function() {
      expect(element.html()).toContain(users[0].first_name);
      expect(element.html()).toContain(users[1].first_name);
      expect(element.html()).toContain(users[2].first_name);
      expect(element.html()).toContain(users[0].last_name);
      expect(element.html()).toContain(users[1].last_name);
      expect(element.html()).toContain(users[2].last_name);
      expect(element.html()).toContain(users[0].avatar);
      expect(element.html()).toContain(users[1].avatar);
      expect(element.html()).toContain(users[2].avatar);
    });
  });
});
