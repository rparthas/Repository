describe('UserService', function() {

  var $rootScope, $httpBackend, $q, UserService, mockDeferred;

  beforeEach(function() {
    angular.mock.module('app');
    angular.mock.inject(function(_$rootScope_, _$q_, _UserService_, _$httpBackend_) {
      $rootScope = _$rootScope_;
      $q = _$q_;
      UserService = _UserService_;
      $httpBackend = _$httpBackend_;
    });
  });
  it('should be defined', function() {
    expect(UserService).toBeDefined();
  });
  it('getUsers should be defined', function() {
    expect(UserService.getUsers).toBeDefined();
  });
  it('getUsers should return a value', function() {
    var mockData = {
      "page": 1,
      "per_page": 3,
      "total": 12,
      "total_pages": 4,
      "data": [{
        "id": 1,
        "first_name": "george",
        "last_name": "bluth",
        "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"
      }, {
        "id": 2,
        "first_name": "lucille",
        "last_name": "bluth",
        "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"
      }, {
        "id": 3,
        "first_name": "oscar",
        "last_name": "bluth",
        "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/olegpogodaev/128.jpg"
      }]
    };
    $httpBackend.when('GET', 'http://reqres.in/api/users').respond(200, {
      data: mockData
    });
    var resolvedValue;
    var promise = UserService.getUsers();
    promise.then(function(value) {
      resolvedValue = value;
    })
    $httpBackend.flush();
    expect(resolvedValue).toEqual(mockData);
  });
});
