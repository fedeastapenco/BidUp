//TODO guardar errores
var Auction = require('../models/auction');
//crear subasta
var create = function (req, res) {
    var auction = new Auction(req.body);
    var promise = auction.save();
    promise.then(function (doc) {
        res.json(doc);
    });
    promise.catch(function (err) {
        res.status(400).end();
    });
}
//actualizar subasta
var update = function (req, res) {
    var auction = Auction.findByIdAndUpdate(req.params.id, { $set: req.body });
    auction.exec(function (err, response) {
        if (err)
            return console.log(err);
        res.json(response);
    });
}
//obtener por ID
var getById = function (req, res) {
    var auction = Auction.findById(req.params.id).populate('currentBidUp').populate('bidUpList').populate('followersList');
    
    auction.exec(function (err, response) {
        if (err)
            return console.log(err);
        res.json(response);
    });
}

var remove = function (req, res) {
    var auction = Auction.findByIdAndRemove(req.params.id);
    auction.exec(function (err, response) {
        if (err)
            return console.log(err);
        //response 204 que es ok pero no content
        res.status(204).end();
    });
}

var addPhoto = function(req, res){
    //viene el ID en req.params, agrego la url a el array
    var auction = Auction.findById(req.params.id);
    auction.photosUrl.push(req.body.urlPhoto);
    var promise = auction.save();
    promise.then(function(auctionDoc){
        res.json(auctionDoc);
    });
    promise.catch(function(err){
        res.status(400).end();
    });

}

var removePhoto = function (req, res) {
    var auction = Auction.findByIdAndUpdate(req.params.id, {$pull:{'photosUrl':{ id:req.body.urlPhoto}}});
    auction.exec(function(err,response){
        if(err)
            return console.log(err);
            res.json(response);
    });
}

var addBidUp = function(req,res){
    //viene el ID en req.params, agrego el id a el array
    var auction = Auction.findById(req.params.id);
    auction.bidUpsList.push(req.body.bidUpId);
    var promise = auction.save();
    promise.then(function(auctionDoc){
        res.json(auctionDoc);
    });
    promise.catch(function(err){
        res.status(400).end();
    });
}

var removeBidUp = function (req, res) {
    var auction = Auction.findByIdAndUpdate(req.params.id, {$pull:{'bidUpsList':{ id:req.body.bidUpId}}});
    auction.exec(function(err,response){
        if(err)
            return console.log(err);
            res.json(response);
    });
}

var addFollower = function(req,res){
    //viene el ID en req.params, agrego el id a el array
    var auction = Auction.findById(req.params.id);
    auction.followersList.push(req.body.followerId);
    var promise = auction.save();
    promise.then(function(auctionDoc){
        res.json(auctionDoc);
    });
    promise.catch(function(err){
        res.status(400).end();
    });
}

var removeFollower = function (req, res) {
    var auction = Auction.findByIdAndUpdate(req.params.id, {$pull:{'followersList':{ id:req.body.followerId}}});
    auction.exec(function(err,response){
        if(err)
            return console.log(err);
            res.json(response);
    });
}

var findByObjectName = function(req,res){
    var auction = Auction.find({objectName: new RegExp('^' + req.params.objectName,"i")})
    auction.exec(function(err,response){
        if(err)
            return console.log(err);
        res.json(response);
    });
}
module.exports = { create, update, getById, remove, addPhoto, addBidUp, addFollower, removePhoto, removeFollower, removeBidUp, findByObjectName}