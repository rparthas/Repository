var EventEmitter = require('events').EventEmitter,
util = require('util');
var eventEmitterClass = function() {
	console.log("The Class Constructor Example");
}
util.inherits(eventEmitterClass, EventEmitter);
eventEmitterClass.prototype.emitMethod = function() {
	console.log('before the emitevent');
	this.emit('emitevent');
	console.log('after the emitevent');
}
var evtEmitInstance = new eventEmitterClass();
evtEmitInstance.on('emitevent', function() {
	console.log('We have got the functionality of Event Emitter');
});
evtEmitInstance.emitMethod();
