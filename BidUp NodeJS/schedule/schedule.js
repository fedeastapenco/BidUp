var schedule = require('node-schedule');
var Auction = require('../models/auction');
var notify = require('../notify/notifierManager');
var checkAuctions = function(){
    console.log("checkAuctions");
    var rule = new schedule.RecurrenceRule();
    rule.hour = 21;
    rule.minute = 26;
    var j = schedule.scheduleJob(rule,function(){
        var today = new Date();
        console.log("ejecutando schedule");
        //si se corre a las 00 no agregar el dia, para hacer pruebas por ahora si
        var dd = today.getDate() + 1;
        var mm = today.getMonth();
        var yyyy = today.getFullYear();
        var dateFinal = new Date(yyyy,mm,dd);
        var dateInicial = new Date(yyyy,mm,today.getDate());
        console.log("DateFinal: " + dateFinal);
        var auctions = Auction.find({"lastDate" : {"$lt":dateFinal, "$gte": dateInicial}});
        auctions.exec(function(err,auctionList){
            console.log("entro");
            if(auctionList != null){
                console.log(auctionList)
               auctionList.forEach(element => {
                   var aux = Auction.findByIdAndUpdate(element._id, {"$set":{finished:true}}).populate('followersList');
                    aux.exec(function(err,response){
                        if(err){
                            console.log("error schedule: " + err);
                        }else{
                            console.log("schedule OK" + response._id);
                            if(response.followersList.length > 0){
                                notify.messageNotification("Subasta finalizada","La subasta " +
                                 response.objectName +
                                  " ha finalizado",'proyectointegrador.bidup_TARGET_NOTIFICATION_AUCTION_DETAIL',
                                  {_id : response._id + ""},response.followersList);
                            }
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