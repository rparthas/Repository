'use strict';
var express = require('express'),
  app = express();
var http = require('http').Server(app);
app.use(function(req, res, next) {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
  res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
  res.setHeader('Access-Control-Allow-Credentials', true);
  next();
});
app.use(express.static('public'));
app.use(express.static('node_modules'));
var server = app.listen('2319', function() {
  var port = server.address().port;
  console.log('listening at port:' + port);
});

module.exports = app;
