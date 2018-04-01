var express = require('express');
var app = express();
app.use(express.static('public'));
app.use(function(req, res) {
  res.sendFile(__dirname + '/public/index.html');
});
app.listen(2319, function() {
  console.log('Our app is listening on port 2319!');
});
