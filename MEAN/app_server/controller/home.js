module.exports.home=function(req, res, next) {
  res.render('index', { title: 'Home' });
};

module.exports.review=function(req, res, next) {
  res.render('index', { title: 'Add Review' });
};

module.exports.about=function(req, res, next) {
  res.render('index', { title: 'About' });
};

module.exports.location=function(req, res, next) {
  res.render('location-info', { title: 'Location' });
};
