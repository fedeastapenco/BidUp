//clase brand
'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
//TODO verificar los tipos de datos cuando tenga internet
var brandSchema = Schema({
    name: String,
    prefix: String
});
var model = mongoose.model('Brand', brandSchema);
module.exports = model;