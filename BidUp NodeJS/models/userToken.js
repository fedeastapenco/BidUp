//clase usuario
'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = Schema({
    
});

var model = mongoose.model('UserToken', userSchema);
module.exports = model;