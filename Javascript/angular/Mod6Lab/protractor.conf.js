exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['protractor.specs.js'],
  capabilities: {
    browserName: 'chrome'
  }
}
