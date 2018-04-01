describe('app module', function() {
  var app;
  beforeAll(function() {
    app = angular.module('app')
  });
  it('should be defined', function() {
    expect(app).toBeDefined();
  });
});
