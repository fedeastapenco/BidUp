//TODO guardar errores
var Auction = require('../models/auction');
var User = require('../models/user');
var BidUp = require('../models/bidUp');
//crear subasta
var create = function (req, res) {
    var auction = new Auction(req.body);
    //busco con el token al usuario y lo agrego.
    var user = User.findOne({'authenticationToken' : req.body.authenticationToken});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            res.status(401).end();
        }
        
        auction.user = userDoc._id;
        var promise = auction.save();
        promise.then(function (doc) {
             res.json(doc);
        });
        promise.catch(function (err) {
             res.status(400).end();
        });
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
    var user = User.findOne({'authenticationToken' : req.query.authenticationToken});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            console.log("user not found");
            res.status(401).end();
        }
        var auction = Auction.findById(req.params.id).populate('user').populate('currentBidUp').populate('bidUpList').populate('followersList');
        auction.exec(function (err, response) {
            if (err)
                return console.log(err);
            res.json(response);
        });
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
    var user = User.findOne({'authenticationToken' : req.body.authenticationToken});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            console.log("user not found");
            res.status(404).end();
        }else{
            var auction = Auction.findById(req.params.auctionId);
            auction.exec(function(err, auctionDoc){
                var bidUp = new BidUp(req.body);
                var promise = bidUp.save();
                promise.then(function (bidUpDoc) {
                    auctionDoc.bidUpsList.push(bidUpDoc._id);
                    auctionDoc.currentBidUp = bidUpDoc._id;
                    auctionDoc.save(function(err,doc){
                        if(err){
                            console.log("error" + err);
                            res.status(500).end();
                        }else{
                            res.json(doc);
                        }
                    });
                });
                promise.catch(function (err) {
                    res.status(400).end();
                });
              
            });
        }
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
    var user = User.findOne({'authenticationToken' : req.body.authenticationToken});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            console.log("user not found");
            res.status(404).end();
        }else{
            var auction = Auction.findById(req.params.auctionId);
            auction.exec(function(err, auctionDoc){
                auctionDoc.followersList.push(userDoc._id);
                auctionDoc.save(function(err,doc){
                    if(err){
                        console.log("error" + err);
                        res.json({"result" : false});
                    }else{
                        res.json({"result" : true});
                    }
                });
            });
        }
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
    var user = User.findOne({'authenticationToken' : req.query.authenticationToken});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            console.log("user not found");
            res.status(404).end();
        }
        var auction = Auction.find({objectName: new RegExp('^' + req.params.objectName,"i")}).populate('user');
        auction.exec(function(err,response){
            if(err)
                return console.log(err);
        res.json({"list" : response});
        });
    }
    );
}
var getPublishedByUser = function(req,res){
     //busco con el token al usuario y lo agrego.
     var user = User.findOne({'authenticationToken' : req.query.authenticationToken});
     user.exec(function(err,userDoc){
         if(err)
             res.status(401).end();
         if(userDoc == null){
             console.log("user not found");
             res.status(404).end();
         }
         var auction = Auction.find({"user" : userDoc._id});
         auction.exec(function(err,auctionDoc){
            if(err)
                res.status(401).end();
            if(auctionDoc == null){
                res.status(404).end();
                console.log("auction not found");
            }
            res.json({"publishedList" : auctionDoc});
         });
    });
}
var getLastAuctions = function(req,res){
    var user = User.findOne({'authenticationToken' : req.query.authenticationToken});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            console.log("user not found");
            res.status(404).end();
        }
        var auction = Auction.find({}).sort({_id:-1}).limit(5).populate('user');
        auction.exec(function(err,auctionDoc){
           if(err)
               res.status(401).end();
           if(auctionDoc == null){
               res.status(404).end();
               console.log("auction not found");
           }
           res.json({"list" : auctionDoc});
        });
    });
}
module.exports = { create, update, getById, remove, addPhoto, addBidUp, addFollower, removePhoto, removeFollower, removeBidUp, findByObjectName, getPublishedByUser, getLastAuctions}