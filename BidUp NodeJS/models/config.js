'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var configSchema = Schema({
    "scheduleHour" : Number,
    "scheduleMinute" : Number
});
var model = mongoose.model('Config',configSchema);
module.exports = model;