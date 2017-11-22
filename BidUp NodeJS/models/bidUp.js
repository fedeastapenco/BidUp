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
    card: {
        type: Schema.ObjectId,
        ref: 'Card',
        // require: true
    },
    amount: Number,
    created: Date

});
bidUpSchema.virtual('user.full').get(function(){
    return this.user._id + this.user.firstName; 
});
var model = mongoose.model('BidUp', bidUpSchema);
module.exports = model;