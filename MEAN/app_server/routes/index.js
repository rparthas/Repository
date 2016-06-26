var express = require('express');
var main=require('../controller/home');
var router = express.Router();

/* GET home page. */

var home=router.get('/',main.home );
var location=router.get('/location',main.location );
var about=router.get('/about',main.about );
var review=router.get('/location/review',main.review );

module.exports = router;
