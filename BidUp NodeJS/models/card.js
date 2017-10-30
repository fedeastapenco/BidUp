//clase tarjeta
'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var cardSchema = Schema({
    number: Number,
    brand: {
        type: Schema.ObjectId,
        ref: 'Brand',
        require: true
    },
    cvv: String,
    startDate: Date
});
var model = mongoose.model('Card', cardSchema);
module.exports = model;