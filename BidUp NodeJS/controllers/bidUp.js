//TODO guardar errores
var BidUp = require('../models/bidUp');
//crear puja
var create = function (req, res) {
    var bidUp = new BidUp(req.body);
    var promise = bidUp.save();
    promise.then(function (doc) {
        res.json(doc);
    });
    promise.catch(function (err) {
        res.status(400).end();
    });
}
//actualizar puja
var update = function (req, res) {
    var bidUp = BidUp.findByIdAndUpdate(req.params.id, { $set: req.body });
    bidUp.exec(function (err, response) {
        if (err){ 
            console.log(err)
            res.status(500).end();
        }else{
            
            res.json(response);
        }
    });
}
var getById = function (req, res) {
    var bidUp = BidUp.findById(req.params.id).populate('user').populate('auction').populate('card');
    bidUp.exec(function (err, response) {
        if (err){ 
            console.log(err)
            res.status(500).end();
        }else{
        res.json(response);
        }
    });
}
var remove = function (req, res) {
    var bidUp = Card.findByIdAndRemove(req.params.id);
    bidUp.exec(function (err, response) {
        if (err){ 
            console.log(err)
            res.status(500).end();
        }else{
        //response 204 que es ok pero no content
        res.status(204).end();
        }
    });
}
module.exports = { create, update, getById, remove }
