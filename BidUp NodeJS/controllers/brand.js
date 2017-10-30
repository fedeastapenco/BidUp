var Brand = require('../models/brand');
//buscar por nombre
var getByName = function (req, res) {
    var brand = Brand.findOne({
        'name': req.params.name
    });
    brand.exec(function (err, brandDoc) {
        if (err)
            return console.log(err);
        res.json(brandDoc);
    });
}
var get = function(req, res){
    var brand = Brand.find();
    brand.exec(function(err,response){
        if(err)
            return console.log(err);
        res.json(response);
    });
}
module.exports = { getByName, get }