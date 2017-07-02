describe('UserDisplayController', function() {
  var $rootScope, $q, $controller, vm, UserService, mockDeferred;
  beforeEach(function() {
    angular.mock.module('app');
    angular.mock.inject(function(_$rootScope_, _$controller_, _$q_, _UserService_) {
      $rootScope = _$rootScope_;
      $controller = _$controller_;
      $q = _$q_;
      UserService = _UserService_;
    });
    mockDeferred = $q.defer();
    spyOn(UserService, 'getUsers').and.returnValue(mockDeferred.promise);
    vm = $controller('UserDisplayController', {
      userService: UserService
    });
  });
  it('should have vm defined', function() {
    expect(vm).toBeDefined();
  });

  it('should have vm.executeSearch defined', function() {
    expect(vm.executeSearch).toBeDefined();
  });
  describe('vm.execute search should call UserService.getUsers and return a result', function() {
    it('should call UserService.getUsers and set vm.users', function() {
      var mockUsers = ['user 1', 'user 2'];
      vm.executeSearch();
      mockDeferred.resolve(mockUsers);
      $rootScope.$apply();
      expect(UserService.getUsers).toHaveBeenCalled();
      expect(vm.users).toEqual(mockUsers);
    });
  });
});
