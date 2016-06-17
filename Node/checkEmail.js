var checkEmail = function(value) {
   try {
      check(value).isEmail();
   } catch (e) {
      return e.message; //Invalid integer
   }
   return value;
};

module.exports.checkEmail = checkEmail;
