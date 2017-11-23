//clase subasta
'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var auctionSchema = Schema({
    user: {
        type: Schema.ObjectId,
        ref: 'User',
        require: true
    },
    objectName: String,
    initialAmount: Number,
    created: {
        type:Date,
        default: Date.now
    },
    lastDate:{
        type: Date
    },
    finished:{
     type: Boolean,
     default : false   
    },
    currentBidUp: {
        type: Schema.ObjectId,
        ref: 'BidUp'
    },
    photosUrl: [
        {
        type:String
        }
    ],
    bidUpsList:[
        {
            type:Schema.ObjectId,
            ref:'BidUp'
        }
    ],
    followersList:[
        {
            type:Schema.ObjectId,
            ref:'User'
        }
    ]

});
var model = mongoose.model('Auction', auctionSchema);
module.exports = model;