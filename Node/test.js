var checkData = require('./checkEmail');

console.log("In testvalidation -->"+checkData.checkEmail("piyas.de@gmail.com"));

console.log("In testvalidation -->"+checkData.checkEmail("piyas.de@gmailcom")); // Return error
