//clase puja
'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var bidUpSchema = Schema({
    user: {
        type: Schema.ObjectId,
        ref: 'User',
        require: true
    },
    auction: {
        type: Schema.ObjectId,
        ref: 'Auction',
        require: true
    },
    card: {
        type: Schema.ObjectId,
        ref: 'Card',
        require: true
    },
    amount: Number,
    created: Date

});
var model = mongoose.model('BidUp', bidUpSchema);
module.exports = model;