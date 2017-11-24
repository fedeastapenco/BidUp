var Brand = require('../models/brand');
//buscar por nombre
var getByName = function (req, res) {
    var brand = Brand.findOne({
        'name': req.params.name
    });
    brand.exec(function (err, brandDoc) {
        if (err){ 
            console.log(err)
            res.status(500).end();
        }else{
        res.json(brandDoc);
        }
    });
}
var get = function(req, res){
    var brand = Brand.find();
    brand.exec(function(err,response){
        if (err){ 
            console.log(err)
            res.status(500).end();
        }else{
        res.json(response);
        }
    });
}
module.exports = { getByName, get }