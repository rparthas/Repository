var http = require('http');
var ejs = require('ejs');
var csv = require('ya-csv');

var querystring = require('querystring');
var path = require('path');
var util = require('util');
var fs = require('fs');
var maxData = 2 * 1024 * 1024; //2mb


var mimeTypes = {
  '.js' : 'text/javascript',
  '.html': 'text/html',
  '.css' : 'text/css'
};


var cacheObj = {
  cachecachestore: {},
  maxSize: 26214400, //(bytes) 25mb
  maxAge: 5400 * 1000, //(ms) 1 and a half hours
  cleaninterval: 7200 * 1000,//(ms) two hours
  cleanetimestart: 0, //to be set dynamically
  clean: function (now) {
    if ((now - this.cleaninterval) > this.cleanetimestart) {
      console.log('cleaning data...');
      this.cleanetimestart = now;
      var that = this;
      if(this.cachestore){
        Object.keys(this.cachestore).forEach(function (file) {
          if (now > that.cachestore[file].timestamp + that.maxAge) {
            delete that.cachestore[file];
          }
        });
      }
    }
  }
};

http.createServer(function (request, response) {
  var lookup = path.basename(decodeURI(request.url)) || 'index.html',
    f = 'content/' + lookup;
    console.log(lookup);
    if (request.method === "POST") {
    var postData = '';
    request.on('data', function (chunk) {

      postData += chunk;
	console.log('postData.length-->>'+postData.length);
	if (postData.length > maxData) {
           postData = '';
           this.pause();
           response.writeHead(413); // Request Entity Too Large
           response.end('Too large');
        }

    }).on('end', function () {
      if (!postData) { response.end(); return; } //prevents empty post requests from crashing the server
      var postDataObject = querystring.parse(postData);
      var writeCSVFile = csv.createCsvFileWriter('users.csv', {'flags': 'a'});
      var data = [postDataObject.name,postDataObject.email,postDataObject.location,postDataObject.mobileno,postDataObject.notes];
      writeCSVFile.writeRecord(data);
      console.log('User Posted 1234:\n', JSON.stringify(util.inspect(postDataObject)));

      var readerCSV = csv.createCsvFileReader('users.csv');
      var data = [];
      readerCSV.on('data', function(rec) {
		data.push(rec);
	}).on('end', function() {
		console.log(data);
		ejs.renderFile('content/registeredusers.ejs',{ users : data},
			function(err, result) {
				// render on success
				//console.log("this is result part -->>"+result);
				if (!err) {
					response.end(result);
				}
				// render or error
				else {
					response.end('An error occurred');
					console.log(err);
				}
			});
	});

    });
    return;
  }
  if (request.method === "GET") {
   fs.exists(f, function (exists) { //path.exists for Node 0.6 and below
    if (exists) {
      var headers = {'Content-type': mimeTypes[path.extname(f)]};
      if (cacheObj.cachestore && cacheObj.cachestore[f]) {
        console.log('cache  deliver');
        response.writeHead(200, headers);
        response.end(cacheObj.cachestore[f].content);
        return;
      }

      var s = fs.createReadStream(f).once('open', function () {
        console.log('stream deliver');
        response.writeHead(200, headers);
        this.pipe(response);
      }).once('error', function (e) {
        console.log(e);
        response.writeHead(500);
        response.end('Internal Server Error...');
      });

      fs.stat(f, function (err, stats) {
        if (stats.size < cacheObj.maxSize) {
          var bufferOffset = 0;
          cacheObj.cachestore[f] = {content: new Buffer(stats.size),
                            timestamp: Date.now()};
          s.on('data', function (data) {
            data.copy(cacheObj.cachestore[f].content, bufferOffset);
            bufferOffset += data.length;
          });
        }
      });

      return;

    }
    response.writeHead(404); //no such file found!
    response.end('Page Not Found!');

  });
  }
  cacheObj.clean(Date.now());

}).listen(8080);
