var Config = require('../models/config');

var create = function(req, res){
    
    if(req.body.scheduleHour == undefined || req.body.scheduleMinute == undefined){
        console.log("Error, hour o minute no enviados");
        res.status(404).end();
    }else{
        var config = Config.findOne({});
        config.then(function(doc){
            console.log(doc);
            if(doc == undefined || doc.length == 0){
                var newConfig = new Config(req.body);
                var save = newConfig.save();
                save.then(function(docRes){
                    res.status(200).end();
                });
                save.catch(function(err){
                    res.status(500).end();
                });
            }
            doc.scheduleHour = req.body.scheduleHour;
            doc.scheduleMinute = req.body.scheduleMinute;
            var promise = doc.save();
            promise.then(function(response){
                res.status(200).end();
            });
            promise.catch(function(err){
                console.log("Error: " + err);
                res.status(500).end();
            });
        });
        config.catch(function(err){
            console.log("Error config: " + err);
            res.status(500).end();
        })
    }
}
module.exports = {create};