var util=require('util');
var events= require('events');
var EventEmitter = events.EventEmitter;

var Person=function(name){
  this.name=name;
  this.speak = function(message) {
    this.emit('Speak',message);
  };
  this.on('Speak', function(message) {
    console.log(name+" speaks "+message);
  });
}

util.inherits(Person,EventEmitter);

var Ram = new Person('Ram');
Ram.speak("hi");
