var schedule = require('node-schedule');
var Auction = require('../models/auction');

var checkAuctions = function(){
    console.log("checkAuctions");
    var rule = new schedule.RecurrenceRule();
    rule.hour = 23;
    rule.minute = 02;
    var j = schedule.scheduleJob(rule,function(){
        var today = new Date();
        console.log("ejecutando schedule");
        //si se corre a las 00 no agregar el dia, para hacer pruebas por ahora si
        var dd = today.getDate() + 1;
        var mm = today.getMonth();
        var yyyy = today.getFullYear();
        var dateFinal = new Date(yyyy,mm,dd);
        console.log("DateFinal: " + dateFinal);
        var auctions = Auction.find({"lastDate" : {"$lt":dateFinal}});
        auctions.exec(function(err,auctionList){
            console.log("entro");
            if(auctionList != null){
                console.log(auctionList)
               auctionList.forEach(element => {
                   var aux = Auction.findByIdAndUpdate(element._id, {"$set":{finished:true}});
                    aux.exec(function(err,response){
                        if(err){
                            console.log("error schedule: " + err);
                        }else{
                            console.log("schedule OK" + response._id);
                        }
                    })
               });
            }else{
                console.log("no hay elementos que terminen hoy");
            }
        });
    });
    
}

module.exports = {checkAuctions};