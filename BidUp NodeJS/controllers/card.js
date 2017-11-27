var Card = require('../models/card');
var User = require('../models/user');
var create = function (req, res) {
   
    var user = User.findOne({'authenticationToken' : req.body.authenticationToken});
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            res.status(401).end();
        }else{
            var card = new Card(req.body);
            var numberString = card.number + "";
            card.lastFour = numberString.substr(numberString.length - 4);
            var promise = card.save();
            promise.then(function (cardDoc) {
                userDoc.cardsList.push(cardDoc._id);
                var promiseUser = userDoc.save();
                promiseUser.then(function(result){

                });
                promiseUser.catch(function(err){
                    console.log("Error " + err );
                });
                cardDoc.number = undefined;
                cardDoc.cvv = undefined;
                res.json(cardDoc);
            });
            promise.catch(function (err) {
                res.status(400).end();
            });
        }
    });
}

var getById = function (req, res) {
    var card = Card.findById(req.params.id).populate('card');
    card.exec(function (err, response) {
        if (err){ 
            console.log(err)
            res.status(500).end();
        }else{
        res.json(response);
        }
    });
}
var getByUser = function(req,res){
    //busco con el token al usuario y lo agrego.
    var user = User.findOne({'authenticationToken' : req.query.authenticationToken}).populate('cardsList');
    user.exec(function(err,userDoc){
        if(err)
            res.status(401).end();
        if(userDoc == null){
            console.log("user not found");
            res.status(404).end();
        }else{
            res.json({"cardList" : userDoc.cardsList});
        }
    });
}
var remove = function (req, res) {
    var card = Card.findByIdAndRemove(req.params.id);
    card.exec(function (err, response) {
        if (err){ 
            console.log(err)
            res.status(500).end();
        }else{
        //response 204 que es ok pero no content
        res.status(204).end();
        }
    });
}
module.exports = { create, getById, remove, getByUser }