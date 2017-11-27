//clase tarjeta
'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var cardSchema = Schema({
    number: {
        type: Number,
        select : false
    },
    cvv: {
     type : String,
     select : false   
    },
    expirationDate: Date,
    lastFour: {
        type: Number
    }
});
var model = mongoose.model('Card', cardSchema);
module.exports = model;