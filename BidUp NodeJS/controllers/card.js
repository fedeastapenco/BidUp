var Card = require('../models/card');

var create = function (req, res) {
    var card = new Card(req.body);
    var promise = card.save();
    promise.then(function (cardDoc) {
        res.json(cardDoc);
    });
    promise.catch(function (err) {
        res.status(400).end();
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
module.exports = { create, getById, remove }