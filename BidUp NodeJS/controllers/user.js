var User = require('../models/user');
var Card = require('../models/card');
var UserToken = require('../models/userToken');
var create = function (req, res) {
    var user = new User(req.body);
    var promise = user.save();
    promise.then(function (userDoc) {
        res.json(userDoc);
    });
    promise.catch(function (err) {
        res.status(400).end();
    });
}

var update = function (req, res) {
    var user = User.findByIdAndUpdate(req.params.id, { $set: req.body });
    user.exec(function (err, response) {
        if (err)
            return console.log(err);
        res.json(response);
    });
}

var getById = function (req, res) {
    var user = User.findById(req.params.id).populate('cardList');
    user.exec(function (err, response) {
        if (err)
            return console.log(err);
        res.json(response);
    });
}
var remove = function (req, res) {
    var user = User.findByIdAndRemove(req.params.id);
    user.exec(function (err, response) {
        if (err)
            return console.log(err);
        //response 204 que es ok pero no content
        res.status(204).end();
    });
}
var createCard = function (req, res){
    var user = User.findById(req.params.id);
    var card = new Card(req.body);
    var promise = card.save();
    promise.then(function(cardDoc){
        user.cardsList.push(cardDoc.id);
        var promiseUser = user.save();
        promiseUser.then(function(userDoc){
            res.json(userDoc);
        });
        promiseUser.catch(function(err){
            console.log(err);
            res.status(204).end();
        });
    });
    promise.catch(function(err){
        console.log(err);
        res.status(204).end();
    });
}

var updateRegistrationToken = function(req,res){
    var user = User.findOneAndUpdate({'authenticationToken' : req.body.authenticationToken},{$set:{fireBaseRegistrationToken:req.body.fireBaseRegistrationToken}});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            console.log("user not found");
            res.status(404).end();
        }else{
                res.status(200).end();
        }
    });
}
var login = function(req,res){
    var user = User.findOne({
        'email' : req.body.email,
        'password' : req.body.password
    });
    
    user.exec(function(err,response){
        if(err)
            return console.log(err);
        //en el nuevo login cambio el token del usuario
        var userToken = new UserToken();
        var promise = userToken.save();
        promise.then(function(doc){
            user.update({_id: response._id , $set : {authenticationToken: doc._id}}, function (err,userUpdated){
                if(err)
                    return console.log(err);
                response.authenticationToken = doc._id;
                res.json(response); 
            });
            
        });
        promise.catch(function(err){
            res.status(400).end();
        })
        
    });
}
module.exports = { create, update, getById, remove, createCard, updateRegistrationToken, login}