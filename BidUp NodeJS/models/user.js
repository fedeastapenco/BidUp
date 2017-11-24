//clase usuario
'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = Schema({
    firstName: String,
    lastName: String,
    email: {
        type: String,
        unique: true,
        required : true
    },
    password: {
        type : String,
        select : false
    },
    ci: String,
    address: String,
    created: Date,
    fireBaseRegistrationToken: String,
    cardsList:[
        {
            type: Schema.ObjectId,
            ref: 'Card'
        }
    ],
    authenticationToken:{
        type:Schema.ObjectId,
        ref:"UserToken"
    }

});
var model = mongoose.model('User', userSchema);
module.exports = model;