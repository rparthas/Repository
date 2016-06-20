var express = require('express');
var main=require('../controller/main');
var router = express.Router();

/* GET home page. */

var main=router.get('/',main.home );

module.exports = router;
